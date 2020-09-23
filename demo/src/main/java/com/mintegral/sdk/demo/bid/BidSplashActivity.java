package com.mintegral.sdk.demo.bid;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.mtgbid.out.SplashBidRequestParams;
import com.mintegral.msdk.out.MTGSplashHandler;
import com.mintegral.msdk.out.MTGSplashLoadListener;
import com.mintegral.msdk.out.MTGSplashShowListener;
import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;
import com.mintegral.sdk.demo.SplashActivity;
import com.mintegral.sdk.demo.SplashShowActivity;

public class BidSplashActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "BidSplashActivity";

    private Button preLoadBtn;
    private Button loadAndShowBtn;
    private Button showBtn;
    private Button bidBtn;
    private RelativeLayout container;
    private int countDownTime = 5;
    private int loadTimeOut = 30;
    private String unitId = "209547";
    private BidManager bidManager;

    MTGSplashHandler mtgSplashHandler;
    private int adHeight=1400;
    private int adWidth=700;
    private String token;

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_splash_bid_activity;
    }

    @Override
    public void initView() {
        bidBtn = findViewById(R.id.mintegral_demo_splash_ac_bid);
        preLoadBtn = findViewById(R.id.mintegral_demo_splash_ac_preload);
        loadAndShowBtn = findViewById(R.id.mintegral_demo_splash_ac_load_show);
        showBtn = findViewById(R.id.mintegral_demo_splash_ac_show);
        container = findViewById(R.id.mintegral_demo_splash_ac_container);
    }

    @Override
    public void initData() {
        try {
            //bidManager = new BidManager(new SplashBidRequestParams(unitId,true,2,30,30));
            bidManager = new BidManager(new SplashBidRequestParams("173349", unitId, true,2,30,30));
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
        bidManager.setBidListener(new BidListennning() {
            @Override
            public void onFailed(String msg) {
                Toast.makeText(BidSplashActivity.this,"bid failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccessed(BidResponsed bidResponsed) {
                token = bidResponsed.getBidToken();
                Toast.makeText(BidSplashActivity.this,"bid success :"+token,Toast.LENGTH_LONG).show();
            }
        });
        mtgSplashHandler.setSplashLoadListener(new MTGSplashLoadListener() {
            @Override
            public void onLoadSuccessed(int reqType) {
                Log.e(TAG, "====================onLoadSuccessed" + reqType);
                Toast.makeText(BidSplashActivity.this,"onLoadSuccessed:" + reqType,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadFailed(String msg, int reqType) {
                Log.e(TAG, "====================onLoadFailed" + msg + reqType);
                Toast.makeText(BidSplashActivity.this,"onLoadFailed:" + reqType,Toast.LENGTH_LONG).show();
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
            }

            @Override
            public void onAdTick(long millisUntilFinished) {
                Log.e(TAG, "====================onAdTick" + millisUntilFinished);
            }
        });
        bidBtn.setOnClickListener(this);
        preLoadBtn.setOnClickListener(this);
        loadAndShowBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mintegral_demo_splash_ac_bid:
                bidManager.bid();
                break;
            case R.id.mintegral_demo_splash_ac_load_show:

                if(!TextUtils.isEmpty(token)){
                    loadAndShow();
                }else {
                    Toast.makeText(BidSplashActivity.this,"Token is empty",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mintegral_demo_splash_ac_preload:
                if (!TextUtils.isEmpty(token)) {
                    preload();
                }else {
                    Toast.makeText(BidSplashActivity.this,"Token is empty",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mintegral_demo_splash_ac_show:
                if (!TextUtils.isEmpty(token)) {
                    if (mtgSplashHandler.isReady(token)) {
                        show();
                    } else {
                        Log.e(TAG, "====================isready is false");

                        Toast.makeText(BidSplashActivity.this,"campain is not ready",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(BidSplashActivity.this,"Token is empty",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void preload(){
        mtgSplashHandler.preLoadByToken(token);
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
        intent.putExtra("token",token);
        startActivity(intent);
    }

}
