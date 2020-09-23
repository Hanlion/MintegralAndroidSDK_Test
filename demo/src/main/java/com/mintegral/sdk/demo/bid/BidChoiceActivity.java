package com.mintegral.sdk.demo.bid;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;
import com.mintegral.sdk.demo.adapter.CommonAdapter;
import com.mintegral.sdk.demo.adapter.ViewHolder;
import com.mintegral.sdk.demo.bean.AdStyleInfoBean;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

import java.util.ArrayList;
import java.util.List;

public class BidChoiceActivity extends BaseActivity {
	private static final int POSITION_NATIVE = 0;
	private static final int POSITION_NATIVE_VIDEO = 1;
	private static final int POSITION_IV = 2;
	private static final int POSITION_RV = 3;
	private static final int POSITION_BANNER = 4;
	private static final int POSITION_SPLASH = 5;
	private static final int POSITION_ADVANCED_NATIVE = 6;
	private GridView mGvNative;
	private CommonTitleLayout mTitleLayout;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_native_choic;
	}

	@Override
	public void initView() {
		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
		mGvNative = (GridView) findViewById(R.id.mintegral_demo_gv_native_list);
	}

	@Override
	public void initData() {
		mTitleLayout.setTitleText("Header Bid List");
		List<AdStyleInfoBean> nativeSelectlist = new ArrayList<AdStyleInfoBean>();
		AdStyleInfoBean nativeStyle = new AdStyleInfoBean("Native", R.drawable.mintegral_demo_hb_img_native, 0);
		AdStyleInfoBean nativeVideoStyle = new AdStyleInfoBean("Native_Video", R.drawable.mintegral_demo_hb_img_nv, 0);
		AdStyleInfoBean ivStyle = new AdStyleInfoBean("interstitial video", R.drawable.mintegral_demo_hb_img_iv, 0);
		AdStyleInfoBean rvStyle = new AdStyleInfoBean("reward video", R.drawable.mintegral_demo_hb_img_rv, 0);
		AdStyleInfoBean bannerStyle = new AdStyleInfoBean("banner", R.drawable.mintegral_demo_simple_banner, 0);
		AdStyleInfoBean splashStyle = new AdStyleInfoBean("splash", R.drawable.mintegral_demo_hb_splash, 0);
		AdStyleInfoBean advancedNativeStyle = new AdStyleInfoBean("advancedNative", R.drawable.mintegral_demo_hb_native_advanced, 0);
		nativeSelectlist.add(nativeStyle);
		nativeSelectlist.add(nativeVideoStyle);
		nativeSelectlist.add(ivStyle);
		nativeSelectlist.add(rvStyle);
		nativeSelectlist.add(bannerStyle);
		nativeSelectlist.add(splashStyle);
		nativeSelectlist.add(advancedNativeStyle);
		CommonAdapter<AdStyleInfoBean> adapter = new CommonAdapter<AdStyleInfoBean>(nativeSelectlist,
				R.layout.mintegral_demo_item_natives, this) {

			@Override
			public void convert(ViewHolder helper, AdStyleInfoBean item) {
				helper.setImageResource(R.id.mintegral_demo_iv_name, item.getAdImageResId());
				helper.setText(R.id.mintegral_demo_tv_name, item.getAdStyleName());
			}
		};
		mGvNative.setAdapter(adapter);
	}

	@Override
	public void setListener() {
		mGvNative.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
					case POSITION_NATIVE:
						intent = new Intent(BidChoiceActivity.this, BidNativeActivity.class);
						startActivity(intent);
						break;
					case POSITION_IV:
						intent = new Intent(BidChoiceActivity.this, BidInterstitialVideoActivity.class);
						startActivity(intent);
						break;
					case POSITION_RV:
						intent = new Intent(BidChoiceActivity.this, BidRewardActivity.class);
						startActivity(intent);
						break;
					case POSITION_NATIVE_VIDEO:
						intent = new Intent(BidChoiceActivity.this, BidNativeVideoRecycleViewActivity.class);
						startActivity(intent);
						break;
					case POSITION_BANNER:
						intent = new Intent(BidChoiceActivity.this, BidBannerActivity.class);
						startActivity(intent);
						break;
					case POSITION_SPLASH:
						intent = new Intent(BidChoiceActivity.this, BidSplashActivity.class);
						startActivity(intent);
						break;
					case POSITION_ADVANCED_NATIVE:
						intent = new Intent(BidChoiceActivity.this, BidNativeAdvancedActivity.class);
						startActivity(intent);
						break;
				}
			}
		});
	}
}
