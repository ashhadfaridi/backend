package com.iastate.cloud.myapp.beans;

/**
 * Author @ Pawan Namagiri
 **/


public class Ec2Bean extends AuthTokenBean{

    public Ec2Bean(String token1, String token2, String username) {
        super(token1, token2, username);
    }


    private String imageId;

    private String instanceType;

    private String keyPairName;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getKeyPairName() {
        return keyPairName;
    }

    public void setKeyPairName(String keyPairName) {
        this.keyPairName = keyPairName;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    private String securityGroup;
}
