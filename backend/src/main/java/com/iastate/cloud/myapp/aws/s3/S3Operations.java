package com.iastate.cloud.myapp.aws.s3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.iastate.cloud.myapp.beans.AuthTokenBean;
import com.iastate.cloud.myapp.beans.PutObjectBean;
import com.iastate.cloud.myapp.encryption.AES;
import com.iastate.cloud.myapp.utils.AwsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Author @ Pawan Namagiri
 **/

@Service
public class S3Operations {


    @Autowired
    AES aes;

    @Value("${AesKey}")
    private String AesKey;

    public List<String> getListOfBuckets(AuthTokenBean authTokenBean) {


        AWSCredentials credentials = AwsUtils.getAwsCredentialsObject(authTokenBean, AesKey);

        List<String> listOfBuckets = new ArrayList<>();

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        List<Bucket> buckets = s3client.listBuckets();
        for(Bucket bucket : buckets) {
            listOfBuckets.add(bucket.getName());
        }

        return listOfBuckets;
    }

    public String uploadToBucket(String bucketName, File file, PutObjectBean putObjectBean) {

        AWSCredentials credentials = AwsUtils.getAwsCredentialsObject(putObjectBean, AesKey);

        List<String> listOfBuckets = new ArrayList<>();

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();


        PutObjectResult result= s3client.putObject(
                bucketName,
                file.getName(),
                file
        );

        if(result != null) {
            return "success";
        } else {
            return "failed";
        }
    }





}
