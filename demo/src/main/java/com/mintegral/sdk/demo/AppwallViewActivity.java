package com.mintegral.sdk.demo;

import java.util.List;
import java.util.Map;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.out.AppWallTrackingListener;
import com.mintegral.msdk.out.LoadListener;
import com.mintegral.msdk.out.MtgWallHandler;
import com.mintegral.msdk.out.MtgWallHandler.AppWallViewCampaignClickListener;
import com.mintegral.msdk.out.MtgWallHandler.AppWallViewLoadingEndListener;
import com.mintegral.msdk.out.MtgWallHandler.AppWallViewNoMoreDateListener;
import com.mintegral.msdk.out.MtgWallHandler.WallViewBackClickListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


public class AppwallViewActivity extends Activity implements OnClickListener {
	RelativeLayout rlRoot;
	private View wall;
	private MtgWallHandler controller;
	private List<View> listview;
	private int num = 0;
	public final String mAppWallUnitId = "146872";

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}

	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mintegral_demo_wall_handler_view);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
		btn = (Button) findViewById(R.id.btn);

//		ViewPager pager = (ViewPager) findViewById(R.id.demo_viewpager);

		TextView tv = new TextView(this);
		tv.setText("00000000000000000000000000000");
		TextView tv1 = new TextView(this);
		tv1.setText("11111111111111");
		//Map<String, Object> properties = MtgWallHandler.getWallProperties(mAppWallUnitId);
		Map<String, Object> properties = MtgWallHandler.getWallProperties("138784", mAppWallUnitId);

		properties.put(MIntegralConstans.WALL_VIEW_VIEWPAGER_NOSCROLL, true);
//		properties.put(MIntegralConstans.PROPERTIES_WALL_MAIN_BACKGROUND_ID,
//				R.color.mintegral_transparent);
//		properties.put(MIntegralConstans.PROPERTIES_WALL_TITLE_BACKGROUND_COLOR,
//				R.color.mintegral_transparent);

//		properties.put(MIntegralConstans.PROPERTIES_WALL_TAB_BACKGROUND_ID,
//				R.color.mintegral_transparent);

		properties.put(MIntegralConstans.PROPERTIES_WALL_CURRENT_TAB_ID, 2);//setting select tab（default is 0）
		properties.put(MIntegralConstans.APPWALL_VIEW_LOAD_RESULT_LISTENER, new LoadListener() {
			
			@Override
			public void onLoadSucceed() {
				Log.e("", "appwall_view load success");
				
			}
			
			@Override
			public void onLoadFaild(String errorMsg) {
				Log.e("", "appwall_view load failed");
				
			}
		});
		// properties.put(MIntegralConstans.PROPERTIES_WALL_TITLE_BACKGROUND_COLOR,
		// R.color.mintegral_green);

		controller = new MtgWallHandler(properties, this);
		wall = controller.getWallView(this, new AppWallTrackingListener() {

			@Override
			public void gotoGooglePlay() {
				Toast.makeText(AppwallViewActivity.this, "open GP", Toast.LENGTH_LONG).show();
			}
		});
		// controller.refreshUI(wall);
		controller.setWallViewBackClickListener(wall,
				new WallViewBackClickListener() {

					@Override
					public void onBackClick() {
						finish();
					}
				});

		controller.setAppWallViewCampaignClickListener(wall, new AppWallViewCampaignClickListener() {
			
			@Override
			public void onStartJump() {
				Log.e("", "mintegral appwallview StartJump");
			}
			
			@Override
			public void onEndJump() {
				Log.e("", "mintegral appwallview  endJump");
			}
		});
		controller.setAppWallViewNoMoreDateListener(wall, new AppWallViewNoMoreDateListener() {
			
			@Override
			public void onNoMoreData() {
				Log.e("", "mintegral appwallview  no more date");
				
			}
		});
		controller.setAppWallViewLoadingEnd(wall, new AppWallViewLoadingEndListener() {
			
			@Override
			public void onLoadEnd() {
				Log.e("", "mintegral loading end");
				
			}
		});
		LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		params1.addRule(RelativeLayout.BELOW, R.id.btn);
		if(wall != null){
			wall.setLayoutParams(params1);
		}

//		listview = new ArrayList<View>();
//		listview.add(tv);
//		listview.add(wall);
//		listview.add(tv1);
		 rlRoot.addView(wall);
		 controller.refreshUI(wall);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				controller.refreshUI(wall);
			}
		}); 
//		pager.addOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//
//			}
//		});
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return listview.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(listview.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(listview.get(position));

				num++;
				return listview.get(position);
			}

		};
//		pager.setAdapter(pagerAdapter);

	}

	@Override
	public void onClick(View v) {

	}

	protected void onDestroy() {
		super.onDestroy();
		if (controller != null && wall != null) {
			controller.releaseWallView(wall);
		}
	};

}
