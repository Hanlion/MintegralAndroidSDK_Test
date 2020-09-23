package com.mintegral.sdk.demo;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mintegral.sdk.demo.adapter.CommonAdapter;
import com.mintegral.sdk.demo.adapter.ViewHolder;
import com.mintegral.sdk.demo.bean.AdStyleInfoBean;
import com.mintegral.sdk.demo.bid.BidChoiceActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

	private static final String TAG = HomeActivity.class.getName();

	private static final int POSITION_NATIVE = 0;
	private static final int POSITION_APPWALL = 1;
	private static final int POSITION_REWARDVIDEO = 2;
	private static final int POSITION_INTERSTITIAL = 3;
	private static final int POSITION_INTERSTITIAL_VIDEO = 4;
	private static final int POSITION_INTERACTIVE_ADS = 5;
	private static final int POSITION_BID = 6;
	private static final int POSITION_SPLASH = 7;
	private static final int POSITION_BANNER = 8;
	private static final int POSITION_ADVANCED_NATIVE = 9;

	private ListView mLvAdList;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_home;
	}

	@Override
	public void initView() {
		mLvAdList = (ListView) findViewById(R.id.mintegral_demo_lv_ad_list);
	}

	@Override
	public void initData() {
		List<AdStyleInfoBean> adStyleLists = new ArrayList<AdStyleInfoBean>();
		AdStyleInfoBean bidStyle = new AdStyleInfoBean("Header bidding", R.drawable.mintegral_demo_ad_hb,
				R.drawable.mintegral_demo_icon_hb);
		AdStyleInfoBean nativeStyle = new AdStyleInfoBean("Native", R.drawable.mintegral_demo_ad_native,
				R.drawable.mintegral_demo_icon_native);
		AdStyleInfoBean appWallStyle = new AdStyleInfoBean("APPWALL", R.drawable.mintegral_demo_ad_appwall,
				R.drawable.mintegral_demo_icon_appwall);
		AdStyleInfoBean rewardStyle = new AdStyleInfoBean("RewardVideo", R.drawable.mintegral_demo_ad_reward,
				R.drawable.mintegral_demo_icon_reward);
		AdStyleInfoBean intersititialStyle = new AdStyleInfoBean("Interstitial", R.drawable.mintegral_demo_ad_interstitial,
				R.drawable.mintegral_demo_icon_intertitial);
		AdStyleInfoBean intersititialVideoStyle = new AdStyleInfoBean("InterstitialVideo", R.drawable.mintegral_demo_ad_interstitial_video,
				R.drawable.mintegral_demo_icon_interstitial_video);
		AdStyleInfoBean interactivieadsStyle = new AdStyleInfoBean("InteractiveAds", R.drawable.mintegral_demo_ad_interstitial_ad,
				R.drawable.mintegral_demo_icon_interstitial_ad);
		AdStyleInfoBean bannerStyle = new AdStyleInfoBean("Banner", R.drawable.mintegral_demo_ad_banner,
				R.drawable.mintegral_demo_icon_splash);
		AdStyleInfoBean splashStyle = new AdStyleInfoBean("Splash", R.drawable.mintegral_demo_ad_splash,
				R.drawable.mintegral_demo_icon_splash);
		AdStyleInfoBean advancedNativeStyle = new AdStyleInfoBean("AdvancedNative", R.drawable.mintegral_demo_ad_native_advanced,
				R.drawable.mintegral_demo_icon_native_advanced);

		adStyleLists.add(nativeStyle);
		adStyleLists.add(appWallStyle);
		adStyleLists.add(rewardStyle);
		adStyleLists.add(intersititialStyle);
		adStyleLists.add(intersititialVideoStyle);
		adStyleLists.add(interactivieadsStyle);
		adStyleLists.add(bidStyle);
		adStyleLists.add(splashStyle);
		adStyleLists.add(bannerStyle);
		adStyleLists.add(advancedNativeStyle);
		CommonAdapter<AdStyleInfoBean> adapter = new CommonAdapter<AdStyleInfoBean>(adStyleLists,
				R.layout.mintegral_demo_item_ad_style, this) {

			@Override
			public void convert(ViewHolder helper, AdStyleInfoBean item) {
				helper.setText(R.id.mintegral_demo_item_tv_ad_name, item.getAdStyleName());
				helper.setImageResource(R.id.mintegral_demo_item_iv_big, item.getAdImageResId());
				helper.setImageResource(R.id.mintegral_demo_item_iv_icon, item.getAdIconResId());
			}
		};
		mLvAdList.setAdapter(adapter);
	}

	@Override
	public void setListener() {
		mLvAdList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
					case POSITION_BID:
						intent = new Intent(HomeActivity.this, BidChoiceActivity.class);
						startActivity(intent);
						break;
					case POSITION_NATIVE:
						Intent recevi = new Intent();
						recevi.setAction("alphab_net_debug_action");
						sendBroadcast(recevi);
						intent = new Intent(HomeActivity.this, NativeChoiceActivity.class);
						startActivity(intent);
						break;
					case POSITION_APPWALL:
						intent = new Intent(HomeActivity.this, AppwallActivity.class);
						startActivity(intent);
						break;
					case POSITION_REWARDVIDEO:
						intent = new Intent(HomeActivity.this, RewardActivity.class);
						startActivity(intent);
						break;
					case POSITION_INTERSTITIAL:
						intent = new Intent(HomeActivity.this, InterstitialActivity.class);
						startActivity(intent);
						break;
					case POSITION_INTERSTITIAL_VIDEO:
						intent = new Intent(HomeActivity.this, InterstitialVideoActivity.class);
						startActivity(intent);
						break;
					case POSITION_INTERACTIVE_ADS:
						intent = new Intent(HomeActivity.this, InteractiveAdsActivity.class);
						startActivity(intent);
						break;
					case POSITION_SPLASH:
						intent = new Intent(HomeActivity.this, SplashActivity.class);
						startActivity(intent);
						break;
					case POSITION_BANNER:
						intent = new Intent(HomeActivity.this, BannerActivity.class);
						startActivity(intent);
						break;
					case POSITION_ADVANCED_NATIVE:
						intent = new Intent(HomeActivity.this, NativeAdvancedShowActivity.class);
						startActivity(intent);
						break;
					default:

						break;
				}
			}
		});
	}


}
