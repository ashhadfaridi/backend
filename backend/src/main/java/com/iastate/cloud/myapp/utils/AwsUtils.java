package com.iastate.cloud.myapp.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.iastate.cloud.myapp.beans.AuthTokenBean;
import com.iastate.cloud.myapp.encryption.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Author @ Pawan Namagiri
 **/


public class AwsUtils {





    public static AWSCredentials getAwsCredentialsObject(AuthTokenBean authTokenBean, String AesKey) {

        String accessKey = AES.decrypt(authTokenBean.getToken1(), AesKey);
        String secretKey = AES.decrypt(authTokenBean.getToken2(), AesKey);

        System.out.println(accessKey + " " + secretKey);

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        return credentials;

    }
}
