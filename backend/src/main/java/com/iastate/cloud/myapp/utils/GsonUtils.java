package com.iastate.cloud.myapp.utils;

import com.google.gson.Gson;
import com.iastate.cloud.myapp.beans.AuthTokenBean;

/**
 * Author @ Pawan Namagiri
 **/


public class GsonUtils {

    public static String ObjectToJsonString(Object object) {
        Gson gson = new Gson();

        return gson.toJson(object);
    }


}
