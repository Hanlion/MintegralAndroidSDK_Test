package com.mintegral.sdk.demo;

import com.mintegral.msdk.out.BannerAdListener;
import com.mintegral.msdk.out.BannerSize;
import com.mintegral.msdk.out.MTGBannerView;
import android.util.Log;
import android.widget.Toast;

/**
 * Native display Banner style
 * 
 * @author
 *
 */
public class BannerActivity extends BaseActivity {

	private static final String TAG = BannerActivity.class.getName();
	public static final String UNIT_ID = "146879";
	private MTGBannerView mtgBannerView;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_native_banner_activity;
	}

	@Override
	public void initData() {
		loadNative();
	}

	@Override
	public void setListener() {

	}


	@Override
	public void initView() {
		mtgBannerView = findViewById(R.id.mintegral_banner_view);
	}

	private void loadNative() {
		//mtgBannerView.init(new BannerSize(BannerSize.DEV_SET_TYPE,1294,720),UNIT_ID);
		mtgBannerView.init(new BannerSize(BannerSize.DEV_SET_TYPE,1294,720), "138791", UNIT_ID);

		mtgBannerView.setAllowShowCloseBtn(true);
		mtgBannerView.setRefreshTime(10);
		mtgBannerView.setBannerAdListener(new BannerAdListener() {
			@Override
			public void onLoadFailed(String msg) {
				showToast("on load failed"+msg);
				Log.e(TAG, "on load failed"+msg);

			}

			@Override
			public void onLoadSuccessed() {

				showToast("on load successd");
				Log.e(TAG, "on load successed");
			}



			@Override
			public void onClick() {
				showToast("onAdClick");
				Log.e(TAG, "onAdClick");
			}

			@Override
			public void onLeaveApp() {
				showToast("leave app");
				Log.e(TAG, "leave app");
			}

			@Override
			public void showFullScreen() {
				showToast("showFullScreen");
				Log.e(TAG, "showFullScreen");
			}

			@Override
			public void closeFullScreen() {
				showToast("closeFullScreen");
				Log.e(TAG, "closeFullScreen");
			}

			@Override
			public void onLogImpression() {
				showToast("onLogImpression");
				Log.e(TAG, "onLogImpression");
			}

			@Override
			public void onCloseBanner() {
				showToast("onCloseBanner");
				Log.e(TAG, "onCloseBanner");
			}
		});

		mtgBannerView.load();


	}



	private void showToast(String msg){
		Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mtgBannerView!= null){
			mtgBannerView.release();
		}
	}
}
