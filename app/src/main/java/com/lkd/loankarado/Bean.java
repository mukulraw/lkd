package com.lkd.loankarado;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Bean extends Application {
    private static Context context;

    public String baseurl = "http://ec2-15-206-41-150.ap-south-1.compute.amazonaws.com/";


    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();



        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

    }
}
