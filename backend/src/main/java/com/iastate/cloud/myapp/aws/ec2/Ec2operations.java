package com.iastate.cloud.myapp.aws.ec2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.iastate.cloud.myapp.beans.AuthTokenBean;
import com.iastate.cloud.myapp.beans.Ec2Bean;
import com.iastate.cloud.myapp.utils.AwsUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author @ Pawan Namagiri
 **/

@Service
public class Ec2operations {

    @Value("${AesKey}")
    private String AesKey;



    
    public String createEc2Instance(Ec2Bean ec2Bean){

        AWSCredentials credentials = AwsUtils.getAwsCredentialsObject(ec2Bean, AesKey);

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();


        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withImageId(ec2Bean.getImageId())
                .withInstanceType(ec2Bean.getInstanceType())
                .withKeyName(ec2Bean.getKeyPairName())
                .withMinCount(1)
                .withMaxCount(1)
                .withSecurityGroups(ec2Bean.getSecurityGroup());

        String yourInstanceId = ec2Client.runInstances(runInstancesRequest)
                .getReservation().getInstances().get(0).getInstanceId();


        if(yourInstanceId != null) return yourInstanceId;
        else return "Failed";
    }

    public List<String> listAllInstances(Ec2Bean ec2Bean) {

        List<String> listOfInstances = new ArrayList<>();

        AWSCredentials credentials = AwsUtils.getAwsCredentialsObject(ec2Bean, AesKey);

        AmazonEC2 ec2 = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        boolean done = false;
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while (!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for (Reservation reservation : response.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    System.out.printf(
                            "Found instance with id %s, " +
                                    "AMI %s, " +
                                    "type %s, " +
                                    "state %s " +
                                    "and monitoring state %s",
                            instance.getInstanceId(),
                            instance.getImageId(),
                            instance.getInstanceType(),
                            instance.getState().getName(),
                            instance.getMonitoring().getState());

                    listOfInstances.add(instance.getInstanceId());
                }
            }

            request.setNextToken(response.getNextToken());

            if (response.getNextToken() == null) {
                done = true;
            }
        }

        return listOfInstances;
    }


}
