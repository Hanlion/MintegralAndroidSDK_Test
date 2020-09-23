package com.mintegral.sdk.demo;

import java.util.ArrayList;
import java.util.List;

import com.mintegral.sdk.demo.adapter.CommonAdapter;
import com.mintegral.sdk.demo.adapter.ViewHolder;
import com.mintegral.sdk.demo.bean.AdStyleInfoBean;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class NativeSimpleActivity extends BaseActivity {
	private GridView mGvSimpleList;
	private CommonTitleLayout mTitleLayout;
	private static final int POSITION_FEEDS = 0;
	private static final int POSITION_FULL_SCREEN = 1;
	private static final int POSITION_INTERSTITIAL = 2;
	public int BIG_IMG_REQUEST_AD_NUM = 1;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_native_simple;
	}

	@Override
	public void initView() {
		mGvSimpleList = (GridView) findViewById(R.id.mintegral_demo_gv_simple_list);
		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_common_demo_title_layout);
	}

	@Override
	public void initData() {
		mTitleLayout.setTitleText("NativeSimple");
		List<AdStyleInfoBean> nativeStyleList = new ArrayList<AdStyleInfoBean>();
		AdStyleInfoBean feedsStyle = new AdStyleInfoBean("FEEDS", R.drawable.mintegral_demo_simple_feeds, 0);
		AdStyleInfoBean fullScreenStyle = new AdStyleInfoBean("FULL SCREEN",
				R.drawable.mintegral_demo_simple_fill_screen, 0);
		AdStyleInfoBean intertitialStyle = new AdStyleInfoBean("INTERTITIAL",
				R.drawable.mintegral_demo_simple_intersitial, 0);

		nativeStyleList.add(feedsStyle);
		nativeStyleList.add(fullScreenStyle);
		nativeStyleList.add(intertitialStyle);
		CommonAdapter<AdStyleInfoBean> adapter = new CommonAdapter<AdStyleInfoBean>(nativeStyleList,
				R.layout.mintegral_demo_item_natives, this) {
			@Override
			public void convert(ViewHolder helper, AdStyleInfoBean item) {
				helper.setImageResource(R.id.mintegral_demo_iv_name, item.getAdImageResId());
				helper.setText(R.id.mintegral_demo_tv_name, item.getAdStyleName());
			}
		};
		mGvSimpleList.setAdapter(adapter);

	}

	@Override
	public void setListener() {
		mGvSimpleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
				case POSITION_FEEDS:
					intent = new Intent(NativeSimpleActivity.this, FeedsImageActivity.class);
					startActivity(intent);
					break;
				case POSITION_FULL_SCREEN:
					intent = new Intent(NativeSimpleActivity.this, FullScreenActivity.class);
					startActivity(intent);
					break;
				case POSITION_INTERSTITIAL:
					intent = new Intent(NativeSimpleActivity.this, NativeInterstitialActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}

}
