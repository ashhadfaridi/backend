package com.iastate.cloud.myapp.beans;

/**
 * Author @ Pawan Namagiri
 **/


public class AwsCredentialsBean {

    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    private String accessKey;

}
