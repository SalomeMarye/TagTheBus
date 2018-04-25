package com.salome.tagthebus;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by 54088 on 20/04/2018.
 */

public class BarcelonaRequest {
    private OkHttpClient my_client;
    private static BarcelonaRequest INSTANCE = null;

    private BarcelonaRequest (){
        my_client = new OkHttpClient();
    }

    public static BarcelonaRequest getINSTANCE (){
        if (INSTANCE == null){
            INSTANCE = new BarcelonaRequest();
        }
        return INSTANCE;
    }

    public void SendRequest(Request request, Callback callback){
        my_client.newCall(request).enqueue(callback);
    }
}
