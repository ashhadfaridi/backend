package com.iastate.cloud.myapp.aws;

import com.iastate.cloud.myapp.beans.AwsCredentialsBean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

/**
 * Author @ Pawan Namagiri
 **/

@Service
public class AuthenticationService {

    public String AwsVerifyAndReturnUserName(AwsCredentialsBean credentials) {


        String username;

        String accessKey = credentials.getAccessKey();
        String secretKey = credentials.getSecretKey();
        try {


            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                    credentials.getAccessKey(),
                    credentials.getSecretKey());


            StsClient stsClient = StsClient.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                    .region(Region.US_EAST_1)
                    .build();

            GetCallerIdentityResponse response = stsClient.getCallerIdentity();
            System.out.println("The user id is" + response.userId());
            System.out.println("The ARN value is" + response);

            String arn = response.arn();

            username = arn.split("/")[1];

            System.out.println("Welcome " + username);
        } catch (Exception e){
            return "USER NOT FOUND";

        }

        return username;
    }
}
