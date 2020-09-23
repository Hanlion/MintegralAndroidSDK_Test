package com.mintegral.sdk.demo;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.interactiveads.out.InteractiveAdsListener;
import com.mintegral.msdk.interactiveads.out.MTGInteractiveHandler;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

import java.util.HashMap;

/**
 * Created by zhaopeng on 2018/7/3.
 */

public class InteractiveAdsActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "InteractiveAdsActivity";
    private Button mBtPreLoad;
    private Button mBtShow;
    private MTGInteractiveHandler mInterstitialHandler;
    private CommonTitleLayout mTitleLayout;
    private ProgressBar mProgressBar;
    private FrameLayout entranceViewLayout;


    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_atv_interactiveads;
    }

    @Override
    public void initView() {
        mBtPreLoad = (Button) findViewById(R.id.bt_preload);
        mBtShow = (Button) findViewById(R.id.bt_show);
        mProgressBar = (ProgressBar) findViewById(R.id.mintegral_demo_progress);
        mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
        entranceViewLayout = (FrameLayout) findViewById(R.id.entrance_view_layout);
    }

    @Override
    public void initData() {
        mTitleLayout.setTitleText(TAG);
        initHandler();
    }


    private void initHandler() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        // 设置广告位ID
        hashMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, "146878");
        hashMap.put(MIntegralConstans.PLACEMENT_ID, "138790");
        mInterstitialHandler = new MTGInteractiveHandler(this, hashMap);
        String placeholderUrl = "https://cdn-adn.rayjump.com/cdn-adn/offersync/18/04/20/10/22/5ad94efcddbb0.png";
        String placeHolderGifUrl = "https://img18.3lian.com/d/file/201710/14/2e593fb208dd49c36db8eb88ad68c1fd.gif";
        mInterstitialHandler.setEntranceView(entranceViewLayout, placeHolderGifUrl);
        mInterstitialHandler.setInteractiveAdsListener(new InteractiveAdsListener() {
            @Override
            public void onInteractivelLoadSuccess(int restype) {
                hideLoadding();
                Log.e(TAG, "onInteractivelLoadSuccess");
                Toast.makeText(getApplicationContext(), "onInteractivelLoadSuccess()", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterActiveMaterialLoadSuccess() {
                hideLoadding();
                Toast.makeText(getApplicationContext(), "onInterActiveMaterialloadSuccess()", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInteractiveLoadFail(String errorMsg) {
                hideLoadding();
                Log.e(TAG, "onInteractiveLoadFail");
                Toast.makeText(getApplicationContext(), "onInteractiveLoadFail()"+errorMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInteractiveShowSuccess() {
                hideLoadding();
                Log.e(TAG, "onInteractiveShowSuccess");
                Toast.makeText(getApplicationContext(), "onInteractiveShowSuccess()", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInteractiveShowFail(String errorMsg) {
                hideLoadding();
                Log.e(TAG, "onInteractiveShowFail " + errorMsg);
                Toast.makeText(getApplicationContext(), "onInteractiveShowFail()", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInteractiveClosed() {
                Log.e(TAG, "onInteractiveClosed");
            }

            @Override
            public void onInteractiveAdClick() {
                Log.e(TAG, "onInteractiveAdClick");
            }

            @Override
            public void onInteractivePlayingComplete(boolean isComplete) {
                Log.e(TAG, "onInteractivePlayingComplete isComplete = " + isComplete);
                Toast.makeText(getApplicationContext(), "onInteractivePlayingComplete isComplete = " + isComplete, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setListener() {
        mBtPreLoad.setOnClickListener(this);
        mBtShow.setOnClickListener(this);
    }

    private void showLoadding() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadding() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_preload:
                if (mInterstitialHandler != null) {
                    showLoadding();
                    mInterstitialHandler.load();
                }
                break;
            case R.id.bt_show:
                if (mInterstitialHandler != null) {
                    mInterstitialHandler.show();
                }
                break;
        }
    }


}
