package com.mintegral.sdk.demo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintegral.msdk.out.MTGSplashHandler;
import com.mintegral.msdk.out.MTGSplashLoadListener;
import com.mintegral.msdk.out.MTGSplashShowListener;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "BSplashActivity";

    private Button preLoadBtn;
    private Button loadAndShowBtn;
    private Button showBtn;
    private RelativeLayout container;
    private int countDownTime = 10;
    private int loadTimeOut = 30;
    private String unitId= "209547";

    MTGSplashHandler mtgSplashHandler;

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_splash_activity;
    }

    @Override
    public void initView() {
        preLoadBtn = findViewById(R.id.mintegral_demo_splash_ac_preload);
        loadAndShowBtn = findViewById(R.id.mintegral_demo_splash_ac_load_show);
        showBtn = findViewById(R.id.mintegral_demo_splash_ac_show);
        container = findViewById(R.id.mintegral_demo_splash_ac_container);
    }

    @Override
    public void initData() {
        try {
            //mtgSplashHandler = new MTGSplashHandler(unitId);
            mtgSplashHandler = new MTGSplashHandler("173349", unitId);
            mtgSplashHandler.setLoadTimeOut(loadTimeOut);
            Button textView = new Button(this);
            textView.setText("logooooooooooo");
            mtgSplashHandler.setLogoView(textView, 100, 100);
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
                Toast.makeText(SplashActivity.this,"onLoadSuccessed:" + reqType,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadFailed(String msg, int reqType) {
                Log.e(TAG, "====================onLoadFailed" + msg + reqType);
                Toast.makeText(SplashActivity.this,"onLoadFailed:" + reqType,Toast.LENGTH_LONG).show();
            }
        });
        preLoadBtn.setOnClickListener(this);
        loadAndShowBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mintegral_demo_splash_ac_load_show:
                loadAndShow();
                break;
            case R.id.mintegral_demo_splash_ac_preload:
                mtgSplashHandler.preLoad();
                break;
            case R.id.mintegral_demo_splash_ac_show:
                if (mtgSplashHandler.isReady()) {
                    show();
                } else {
                    Log.e(TAG, "====================isready is false");
                }
                break;
        }
    }

    private void show(){
        Intent intent = new Intent(this,SplashShowActivity.class);
        intent.putExtra("type",1);
        startIt(intent);
    }
    private void loadAndShow(){
        Intent intent = new Intent(this,SplashShowActivity.class);
        intent.putExtra("type",2);
        startIt(intent);
    }

    private void startIt(Intent intent){
        intent.putExtra("unitId",unitId);
        intent.putExtra("countDownTime",countDownTime);
        intent.putExtra("loadTimeOut",loadTimeOut);
        startActivity(intent);
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
}
