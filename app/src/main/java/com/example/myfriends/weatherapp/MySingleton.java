package com.example.myfriends.weatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by myfriends on 3/18/2018.
 */

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context myContext;

    private MySingleton(Context context){
        myContext=context;
        requestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new MySingleton(context);
        }
        return mInstance;
    }
    public void addToRequestQue(Request request){
        requestQueue.add(request);
    }
}
