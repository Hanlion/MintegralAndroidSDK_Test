package com.mintegral.sdk.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintegral.msdk.out.MTGSplashHandler;
import com.mintegral.msdk.out.MTGSplashLoadListener;
import com.mintegral.msdk.out.MTGSplashShowListener;
import com.mintegral.sdk.demo.bid.BidSplashActivity;

public class SplashShowActivity extends BaseActivity{
    private static String TAG = "BSplashActivity";


    private RelativeLayout container;
    private int countDownTime = 5;
    private int loadTimeOut = 30;
    private String mUnitId;
    private int type;
    private String token;

    private int TYPE_SHOW = 1;
    private int TYPE_LOAD_AND_SHOW = 2;
    MTGSplashHandler mtgSplashHandler;

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_splash_show_activity;
    }

    @Override
    public void initView() {
        container = findViewById(R.id.mintegral_demo_splash_ac_container);
    }

    @Override
    public void initData() {
        try {
            Intent intent = getIntent();
            mUnitId = intent.getStringExtra("unitId");
            countDownTime = intent.getIntExtra("countDownTime",5);
            loadTimeOut = intent.getIntExtra("loadTimeOut",30);
            type = intent.getIntExtra("type",0);
            token = intent.getStringExtra("token");
            //mtgSplashHandler = new MTGSplashHandler(mUnitId);
            mtgSplashHandler = new MTGSplashHandler("173349", mUnitId);
            mtgSplashHandler.setLoadTimeOut(loadTimeOut);
            ImageView logo = new ImageView(this);
            logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
            logo.setImageResource(R.drawable.mintegral_logo_green);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(320,
                    150);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(logo,params);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(relativeParams);
            relativeLayout.setBackgroundResource(R.color.mintegral_white);
            mtgSplashHandler.setLogoView(relativeLayout, 400, 250);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setListener() {
        mtgSplashHandler.setSplashLoadListener(new MTGSplashLoadListener() {
            @Override
            public void onLoadSuccessed(int reqType) {
                Log.e(TAG, "====================onLoadSuccessed" + reqType);
                Toast.makeText(SplashShowActivity.this,"onLoadSuccessed:" + reqType,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadFailed(String msg, int reqType) {
                Log.e(TAG, "====================onLoadFailed" + msg + reqType);
                Toast.makeText(SplashShowActivity.this,"onLoadFailed:" + reqType,Toast.LENGTH_LONG).show();
            }
        });
        mtgSplashHandler.setSplashShowListener(new MTGSplashShowListener() {
            @Override
            public void onShowSuccessed() {
                Log.e(TAG, "====================onShowSuccessed");
            }

            @Override
            public void onShowFailed(String msg) {
                Log.e(TAG, "====================onShowFailed" + msg);
            }

            @Override
            public void onAdClicked() {
                Log.e(TAG, "====================onAdClicked");
            }

            @Override
            public void onDismiss(int type) {
                Log.e(TAG, "====================onDismiss" + type);
                finish();
            }

            @Override
            public void onAdTick(long millisUntilFinished) {
                Log.e(TAG, "====================onAdTick" + millisUntilFinished);
            }
        });
        show();
    }

    private void show(){
        if(TextUtils.isEmpty(token)){
            if(type == TYPE_SHOW){
                mtgSplashHandler.show(container);
            }else if(type == TYPE_LOAD_AND_SHOW){
                mtgSplashHandler.loadAndShow(container);
            }else {
                Toast.makeText(this,"the type is unknow",Toast.LENGTH_LONG).show();
            }
        }else {
            if(type == TYPE_SHOW){
                mtgSplashHandler.show(container,token);
            }else if(type == TYPE_LOAD_AND_SHOW){
                mtgSplashHandler.loadAndShowByToken(token,container);
            }else {
                Toast.makeText(this,"the type is unknow",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mtgSplashHandler != null){
            mtgSplashHandler.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mtgSplashHandler != null){
            mtgSplashHandler.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if(mtgSplashHandler != null){
            mtgSplashHandler.onDestroy();
        }
        super.onDestroy();
    }

    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
        return dstbmp;
    }
}
