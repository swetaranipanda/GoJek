package golife.com.gojektest;


import android.app.Application;
import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.Date;

import golife.com.gojektest.utils.GsonUtils;


/**
 * Created by Swetarani Panda on 26/04/2017.
 */
public class BaseApplication extends Application {

    public static Context context;
    private static final String TAG = "BaseApplication";


    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplication.context = getApplicationContext();
        gsonBuilder();

    }

    public static void gsonBuilder() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, GsonUtils.customDateJsonSerializer);
        gsonBuilder.registerTypeAdapter(Date.class, GsonUtils.customDateJsonDeserializer);
        GsonUtils.gson = gsonBuilder.create();
    }

    }