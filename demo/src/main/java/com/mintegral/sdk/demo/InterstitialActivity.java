package com.mintegral.sdk.demo;

import java.util.HashMap;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.out.InterstitialListener;
import com.mintegral.msdk.out.MTGInterstitialHandler;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * InterstitialActivity
 * 
 * @author simon
 * 
 */
public class InterstitialActivity extends BaseActivity implements OnClickListener {

	public static final String TAG = "InterstitialActivity";
	private Button mBtPreLoad;
	private Button mBtShow;
	private MTGInterstitialHandler mInterstitialHandler;
	private CommonTitleLayout mTitleLayout;
	private ProgressBar mProgressBar;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_interstitial;
	}

	@Override
	public void initView() {
		mBtPreLoad = (Button) findViewById(R.id.bt_preload);
		mBtShow = (Button) findViewById(R.id.bt_show);
		mProgressBar = (ProgressBar) findViewById(R.id.mintegral_demo_progress);
		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
	}

	@Override
	public void initData() {
		mTitleLayout.setTitleText("Intertitial");
		initHandler();
	}

	private void showLoadding() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void hideLoadding() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void setListener() {
		mBtPreLoad.setOnClickListener(this);
		mBtShow.setOnClickListener(this);
	}

	private void initHandler() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		// 设置广告位ID
		hashMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, "146871");
		hashMap.put(MIntegralConstans.PLACEMENT_ID, "138783");
		mInterstitialHandler = new MTGInterstitialHandler(this, hashMap);
		mInterstitialHandler.setInterstitialListener(new InterstitialListener() {
			@Override
			public void onInterstitialShowSuccess() {
				Log.e(TAG, "onInterstitialShowSuccess");
			}

			@Override
			public void onInterstitialShowFail(String errorMsg) {
				Log.e(TAG, "onInterstitialShowFail errorMsg:" + errorMsg);
			}

			@Override
			public void onInterstitialLoadSuccess() {
				Log.e(TAG, "onInterstitialLoadSuccess");
				hideLoadding();
				Toast.makeText(getApplicationContext(), "onInterstitialLoadSuccess", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onInterstitialLoadFail(String errorMsg) {
				Log.e(TAG, "onInterstitialLoadFail errorMsg:" + errorMsg);
			}

			@Override
			public void onInterstitialClosed() {
				Log.e(TAG, "onInterstitialClosed");
			}

			@Override
			public void onInterstitialAdClick() {
				Log.e(TAG, "onInterstitialAdClick");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_preload:

			if (mInterstitialHandler != null) {
				showLoadding();
				mInterstitialHandler.preload();
			}
			break;
		case R.id.bt_show:

			if (mInterstitialHandler != null) {
				mInterstitialHandler.show();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mInterstitialHandler != null){
			mInterstitialHandler.setInterstitialListener(null);
		}
	}
}
