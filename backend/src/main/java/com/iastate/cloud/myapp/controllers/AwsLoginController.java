package com.iastate.cloud.myapp.controllers;

import com.iastate.cloud.myapp.beans.AuthTokenBean;
import com.iastate.cloud.myapp.encryption.AES;
import com.iastate.cloud.myapp.service.UserAuthenticationService;
import com.iastate.cloud.myapp.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.iastate.cloud.myapp.beans.AwsCredentialsBean;
import org.springframework.web.bind.annotation.*;

/**
 * Author @ Pawan Namagiri
 **/

@CrossOrigin("*")
@RestController
@RequestMapping("/home")
public class AwsLoginController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Value("${AesKey}")
    private String AesKey;

    @Autowired
    private AES aes;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<String> getCredentials(@RequestBody AwsCredentialsBean credentials) {

        String authResponse = userAuthenticationService.returnNameIfValidUser(credentials);

        if(authResponse.equals("USER NOT FOUND")) return new ResponseEntity<>("Bad Credentials", HttpStatus.UNAUTHORIZED);

        AuthTokenBean authTokenBean = new AuthTokenBean(
                AES.encrypt(credentials.getAccessKey(),AesKey),
                AES.encrypt(credentials.getSecretKey(),AesKey) ,
                authResponse);


        return new ResponseEntity<>(GsonUtils.ObjectToJsonString(authTokenBean), HttpStatus.OK);
    }

}
