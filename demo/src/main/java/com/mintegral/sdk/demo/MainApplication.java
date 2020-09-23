package com.mintegral.sdk.demo;

import android.app.Application;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.MIntegralUser;
import com.mintegral.msdk.out.MIntegralSDKFactory;
import com.mintegral.msdk.out.SDKInitStatusListener;

import java.util.HashMap;
import java.util.Map;

public class MainApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("demo", "application oncreate");

        String appId = "118690";
        String appKey = "7c22942b749fe6a6e361b675e96b3ee9";
        MIntegralSDKManager.getInstance().initialize(getApplicationContext(), appKey, appId, true, null, new MIntegralSDKManager.MIntegralSDKInitializeListener() {
            @Override
            public void onInitializeSuccess(String appKey, String appID) {
                Log.e("SDKInitStatus", "onInitSuccess");
            }

            @Override
            public void onInitializeFailure(String message) {
                Log.e("SDKInitStatus", "onInitFail");
            }
        });

        //update user
        reportUser(111.0121, -15.001);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics dm = this.getBaseContext().getResources().getDisplayMetrics();
        this.getBaseContext().getResources().updateConfiguration(newConfig, dm);
    }

    /**
     * report current user info,this can Raise Revenue
     */
    public static void reportUser(double lat, double lng) {
        MIntegralUser mIntegralUser = new MIntegralUser();
        // 1(pay),0(no pay)，if unkonw you can not set
        mIntegralUser.setPay(1);
        // 1male,2fmale(int类型),if unkonw you can not set
        mIntegralUser.setGender(2);
        // set current user age,if unkonw you can not set
        mIntegralUser.setAge(28);
        //Custom parameters
        mIntegralUser.setCustom("Custom parameters");
        //set user longitude,if unkonw you can not set
        mIntegralUser.setLng(lng);
        //set user latitude,if unkonw you can not set
        mIntegralUser.setLat(lat);
        MIntegralSDKManager.getInstance().getMIntegralSDK().reportUser(mIntegralUser);
    }


}
