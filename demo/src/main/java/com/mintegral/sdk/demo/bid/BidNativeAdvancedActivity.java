package com.mintegral.sdk.demo.bid;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mintegral.msdk.mtgbid.out.AdvancedNativeBidRequestParams;

import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.out.NativeAdvancedAdListener;

import com.mintegral.msdk.out.MTGNativeAdvancedHandler;

import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;

/**
 * Native display AdvancedNative style
 * 
 * @author
 *
 */
public class BidNativeAdvancedActivity extends BaseActivity {

	private static final String TAG = BidNativeAdvancedActivity.class.getName();
	public static final String UNIT_ID = "259532";
	public static final String PLACEMENT_ID = "202132";
	private ViewGroup mtgAdView;
	private Button bidBtn,loadBtn;
	private RelativeLayout mintegralContainerRl;
	private BidManager manager;
	private String bidToken;
	private int advancedNativeH = 640;
	private int advancedNativeW = 320;
	private MTGNativeAdvancedHandler mtgNativeAdvancedHandler;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_native_advanced_bid_activity;
	}

	@Override
	public void initData() {
		mtgNativeAdvancedHandler = new MTGNativeAdvancedHandler(this,PLACEMENT_ID,UNIT_ID);
		mtgNativeAdvancedHandler.setNativeViewSize(advancedNativeH,advancedNativeW);
		mtgAdView = mtgNativeAdvancedHandler.getAdViewGroup();
	}

	@Override
	public void setListener() {
		if (mtgNativeAdvancedHandler != null) {
			mtgNativeAdvancedHandler.setAdListener(new NativeAdvancedAdListener() {
				@Override
				public void onLoadFailed(String msg) {
					Log.e(TAG,"=======onLoadFailed");
				}

				@Override
				public void onLoadSuccessed() {
					Log.e(TAG,"=======onLoadSuccessed");
					if(mtgAdView != null && mtgAdView.getParent() == null){
						mintegralContainerRl.addView(mtgAdView);
					}
				}

				@Override
				public void onLogImpression() {
					Log.e(TAG,"=======onLogImpression");
				}

				@Override
				public void onClick() {
					Log.e(TAG,"=======onClick");
				}

				@Override
				public void onLeaveApp() {
					Log.e(TAG,"=======onLeaveApp");
				}

				@Override
				public void showFullScreen() {
					Log.e(TAG,"=======showFullScreen");
				}

				@Override
				public void closeFullScreen() {
					Log.e(TAG,"=======closeFullScreen");
				}

				@Override
				public void onClose() {
					Log.e(TAG,"=======onClose");
				}
			});
		}
	}

	@Override
	public void initView() {
		mintegralContainerRl = findViewById(R.id.mintegral_container_rl);
		bidBtn = findViewById(R.id.bt_bid_advancedNative);
		bidBtn.setVisibility(View.VISIBLE);
		bidBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bid();
			}
		});
		loadBtn = findViewById(R.id.bt_load_advancedNative);
		loadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(bidToken)) {
					load(bidToken);
				}else{
					showToast("token is null ");
				}
			}
		});
	}

	private void bid(){
		manager = new BidManager(new AdvancedNativeBidRequestParams(PLACEMENT_ID, UNIT_ID, advancedNativeW, advancedNativeH));
		manager.setBidListener(new BidListennning() {
			@Override
			public void onFailed(String msg) {
				Toast.makeText(getApplicationContext(),"bid failed:"+msg,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccessed(BidResponsed bidResponsed) {
				Toast.makeText(getApplicationContext(),"bid successed:"+bidResponsed.getPrice(),Toast.LENGTH_SHORT).show();
				bidToken = bidResponsed.getBidToken();
			}
		});
		manager.bid();
	}

	private void load(String token) {
		if(mtgNativeAdvancedHandler != null){
			mtgNativeAdvancedHandler.loadByToken(token);
		}


	}


	@Override
	protected void onResume() {
		super.onResume();
		mtgNativeAdvancedHandler.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mtgNativeAdvancedHandler.onPause();
	}

	private void showToast(String msg){
		Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mtgNativeAdvancedHandler != null){
			mtgNativeAdvancedHandler.release();
		}
	}
}
