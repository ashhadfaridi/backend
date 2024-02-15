package com.iastate.cloud.myapp.beans;

/**
 * Author @ Pawan Namagiri
 **/


public class AuthTokenBean {

    public String token1;

    public String token2;

    public String username;

    public AuthTokenBean(String token1,String token2,  String username) {
        this.token1 = token1;
        this.token2 = token2;
        this.username = username;
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
