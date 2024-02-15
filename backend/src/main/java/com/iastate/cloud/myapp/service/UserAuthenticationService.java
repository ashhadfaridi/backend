package com.iastate.cloud.myapp.service;

import com.iastate.cloud.myapp.aws.AuthenticationService;
import com.iastate.cloud.myapp.beans.AwsCredentialsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author @ Pawan Namagiri
 **/

@Service
public class UserAuthenticationService {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${AesKey}")
    private String AesKey;

    public String returnNameIfValidUser(AwsCredentialsBean awsCredentialsBean) {

        return authenticationService.AwsVerifyAndReturnUserName(awsCredentialsBean);
    }




}
