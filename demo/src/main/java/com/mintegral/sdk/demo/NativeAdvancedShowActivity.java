package com.mintegral.sdk.demo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.out.NativeAdvancedAdListener;
import com.mintegral.msdk.out.AutoPlayMode;
import com.mintegral.msdk.out.MTGNativeAdvancedHandler;
import com.mintegral.msdk.out.MTGMultiStateEnum;


import org.json.JSONObject;


public class NativeAdvancedShowActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "AdvancedNativeActivity";


    private RelativeLayout container;
    private Button preloadBtn;
    private Button showBtn;
    private Button releaseBtn;

    private String placementId = "202132";
    private String mUnitId= "258656";
    private int advancedNativeH = 640;
    private int advancedNativeW = 320;
    MTGNativeAdvancedHandler mtgNativeAdvancedHandler;
    private ViewGroup mAdvancedNativeView;

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_native_advanced_activity;
    }

    @Override
    public void initView() {
        container = findViewById(R.id.mintegral_demo_advanced_native_ac_container);
        preloadBtn = findViewById(R.id.mintegral_demo_advanced_native_ac_preload);
        showBtn = findViewById(R.id.mintegral_demo_advanced_native_ac_show);
        releaseBtn = findViewById(R.id.mintegral_demo_advanced_native_ac_release);
    }

    @Override
    public void initData() {
        try {


            mtgNativeAdvancedHandler = new MTGNativeAdvancedHandler(this,placementId, mUnitId);
            mtgNativeAdvancedHandler.setNativeViewSize(advancedNativeW,advancedNativeH);
            mtgNativeAdvancedHandler.setCloseButtonState(MTGMultiStateEnum.positive);
            mtgNativeAdvancedHandler.setPlayMuteState(MIntegralConstans.REWARD_VIDEO_PLAY_MUTE);
            mtgNativeAdvancedHandler.autoLoopPlay(AutoPlayMode.PLAY_WHEN_NETWORK_IS_AVAILABLE);
            String style = "{\"list\": [{\n" +
                    "\"target\": \"title\",\n" +
                    "\"values\": {\n" +
                    "\"paddingLeft\": 15,\"backgroundColor\": \"yellow\",\n" +
                    "                    \"fontSize\": 15,\n" +
                    "                    \"color\": \"red\"\n" +
                    "                  }\n" +
                    "              },{\n" +
                    "                  \"target\": \"mediaContent\",\n" +
                    "\n" +
                    "                  \"values\": {\n" +
                    "                      \"paddingLeft\": 60\n" +
                    "                  }\n" +
                    "              }]\n" +
                    "            }";
            JSONObject jsonObject = new JSONObject(style);
            mtgNativeAdvancedHandler.setViewElementStyle(jsonObject);
            mAdvancedNativeView = mtgNativeAdvancedHandler.getAdViewGroup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setListener() {
        mtgNativeAdvancedHandler.setAdListener(new NativeAdvancedAdListener() {

            @Override
            public void onLoadSuccessed() {
                Log.e(TAG, "====================onLoadSuccessed");
                Toast.makeText(NativeAdvancedShowActivity.this,"onLoadSuccessed:",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoadFailed(String msg) {
                Log.e(TAG, "====================onLoadFailed" + msg );
                Toast.makeText(NativeAdvancedShowActivity.this,"onLoadFailed:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLogImpression() {
                Log.e(TAG, "====================onLogImpression" );
                Toast.makeText(NativeAdvancedShowActivity.this,"onLogImpression:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClick() {
                Log.e(TAG, "====================onClick" );
                Toast.makeText(NativeAdvancedShowActivity.this,"onClick:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLeaveApp() {
                Log.e(TAG, "====================onLeaveApp" );
                Toast.makeText(NativeAdvancedShowActivity.this,"onLeaveApp:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void showFullScreen() {
                Log.e(TAG, "====================showFullScreen" );
                Toast.makeText(NativeAdvancedShowActivity.this,"showFullScreen:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void closeFullScreen() {
                Log.e(TAG, "====================closeFullScreen" );
                Toast.makeText(NativeAdvancedShowActivity.this,"closeFullScreen:" ,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClose() {

                Log.e(TAG, "====================onDismiss" );
                Toast.makeText(NativeAdvancedShowActivity.this,"onDismiss:" ,Toast.LENGTH_LONG).show();
            }
        });

        preloadBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
        releaseBtn.setOnClickListener(this);
    }

    private void show(){
        if (mAdvancedNativeView != null && mAdvancedNativeView.getParent() == null) {
            container.addView(mAdvancedNativeView);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mtgNativeAdvancedHandler != null){
            mtgNativeAdvancedHandler.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mtgNativeAdvancedHandler != null){
            mtgNativeAdvancedHandler.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if(mtgNativeAdvancedHandler != null){
            mtgNativeAdvancedHandler.release();
        }
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mintegral_demo_advanced_native_ac_preload:
                mtgNativeAdvancedHandler.load();
                break;
            case R.id.mintegral_demo_advanced_native_ac_show:
                show();
                break;
            case R.id.mintegral_demo_advanced_native_ac_release:
                mtgNativeAdvancedHandler.release();
                break;
        }
    }
}
