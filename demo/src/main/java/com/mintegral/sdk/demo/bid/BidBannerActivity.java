package com.mintegral.sdk.demo.bid;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mintegral.msdk.mtgbid.out.BannerBidRequestParams;
import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.out.BannerAdListener;
import com.mintegral.msdk.out.BannerSize;
import com.mintegral.msdk.out.MTGBannerView;
import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;

/**
 * Native display Banner style
 * 
 * @author
 *
 */
public class BidBannerActivity extends BaseActivity {

	private static final String TAG = BidBannerActivity.class.getName();
	public static final String UNIT_ID = "146879";
	private ProgressBar mProgressBar;
	private MTGBannerView mtgBannerView;
	private Button bidBtn,loadBtn;
	private BidManager manager;
	private String bidToken;
	private int bannerH = 720;
	private int bannerW = 1294;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_native_banner_activity;
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListener() {

	}

	@Override
	public void initView() {
		mtgBannerView = findViewById(R.id.mintegral_banner_view);
		bidBtn = findViewById(R.id.bt_bid_banner);
		bidBtn.setVisibility(View.VISIBLE);
		bidBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bid();
			}
		});
		loadBtn = findViewById(R.id.bt_load_banner);
		loadBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(bidToken)) {
					loadBanner(bidToken);
				}else{
					showToast("token is null ");
				}
			}
		});
	}

	private void bid(){
		//manager = new BidManager(new BannerBidRequestParams(UNIT_ID,bannerW,bannerH));
		manager = new BidManager(new BannerBidRequestParams("138791", UNIT_ID, bannerW, bannerH));
		manager.setBidListener(new BidListennning() {
			@Override
			public void onFailed(String msg) {
				Toast.makeText(getApplicationContext(),"bid failed:"+msg,Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccessed(BidResponsed bidResponsed) {
				Toast.makeText(getApplicationContext(),"bid successed:"+bidResponsed.getPrice(),Toast.LENGTH_LONG).show();
				bidToken = bidResponsed.getBidToken();
			}
		});
		manager.bid();
	}

	private void loadBanner(String token) {
		mtgBannerView.init(new BannerSize(BannerSize.DEV_SET_TYPE, bannerW, bannerH), "138791", UNIT_ID);
		//mtgBannerView.setAllowShowCloseBtn(false);
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

			}
			@Override
			public void onCloseBanner() {
				showToast("onCloseBanner");
				Log.e(TAG, "onCloseBanner");
			}
		});

		mtgBannerView.loadFromBid(token);

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
