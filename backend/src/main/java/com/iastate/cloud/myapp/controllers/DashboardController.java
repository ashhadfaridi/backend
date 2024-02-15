package com.iastate.cloud.myapp.controllers;

import com.iastate.cloud.myapp.aws.s3.S3Operations;
import com.iastate.cloud.myapp.beans.AuthTokenBean;
import com.iastate.cloud.myapp.beans.AwsCredentialsBean;
import com.iastate.cloud.myapp.beans.Ec2Bean;
import com.iastate.cloud.myapp.beans.PutObjectBean;
import com.iastate.cloud.myapp.encryption.AES;
import com.iastate.cloud.myapp.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.iastate.cloud.myapp.aws.ec2.Ec2operations;
/**
 * Author @ Pawan Namagiri
 **/

@CrossOrigin("*")
@RestController
@RequestMapping("/home")
public class DashboardController {

    @Autowired
    private S3Operations s3Operations;
    
    @Autowired
    private Ec2operations ec2Operations;

    @Value("${AesKey}")
    private String AesKey;


//    @PostMapping(path = "/s3/getBucketList")
//    public ResponseEntity<List<String>> getBucketList(@RequestBody AuthTokenBean authTokenBean) {
//
//
//        System.out.println(AesKey);
//
//        return new ResponseEntity<>(s3Operations.getListOfBuckets(authTokenBean), HttpStatus.OK);
//
//    }
    @PostMapping(path = "/s3/getBucketList")
    public ResponseEntity<String> getBucketList(@RequestBody AuthTokenBean authTokenBean) {


        System.out.println(AesKey);
        List<String> response = s3Operations.getListOfBuckets(authTokenBean);
        String html = "<select id='bucket-selection'>";
        
        for(int i=0;i<response.size();i++){
            html+="<option>"+response.get(i)+"</option>";
        }
        html+="</select>";

        return new ResponseEntity<>(html, HttpStatus.OK);

    }
    @CrossOrigin
    @PostMapping("/test")
    public String test(){

        String html = "<h1>Hello World</h1>" +
                "<select><option>One</option><option>Two</option><select>";

        return html;

    }

    @CrossOrigin
    @PostMapping(path ="s3/putObject", consumes="multipart/form-data")
    public ResponseEntity<String> s3PutObject(@ModelAttribute PutObjectBean putObjectBean){

    	
    	File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+putObjectBean.getFile().getOriginalFilename());
        System.out.println("file is : " + putObjectBean.getFile().getOriginalFilename());
        System.out.println("file is : " + putObjectBean.getToken1() +" " +putObjectBean.getToken2());
        
        
        try {
			putObjectBean.getFile().transferTo(convFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        s3Operations.uploadToBucket(putObjectBean.getBucketName(), convFile, putObjectBean);
       

        return new ResponseEntity<String>(putObjectBean.getFile().getOriginalFilename(), HttpStatus.OK);
        
    }
    @PostMapping(path="ec2/createInstance")
    public ResponseEntity<String> createEc2Instance(@RequestBody Ec2Bean ec2Bean){

        String response = ec2Operations.createEc2Instance(ec2Bean);

        System.out.println("EC2 Response: "+ response);

        if(response.equals("Failed")){
            return new ResponseEntity<>("Creation Failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Created", HttpStatus.OK);
    }


    @PostMapping(path="ec2/listInstances")
    public ResponseEntity<String> listAllInstances(@RequestBody Ec2Bean ec2Bean){
        
        List<String> listOfInstances = ec2Operations.listAllInstances(ec2Bean);
        
        String html = "<ul>";
        
        for(int i=0; i < listOfInstances.size(); i++){
            html+= "<li>"+listOfInstances.get(i)+"</li>";
        }
        html+= "</ul>";

        return new ResponseEntity<>(html, HttpStatus.OK);

    }
}
