package com.mintegral.sdk.demo.bid;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.out.MTGBidRewardVideoHandler;
import com.mintegral.msdk.out.RewardVideoListener;
import com.mintegral.msdk.videocommon.download.NetStateOnReceive;
import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

public class BidRewardActivity extends BaseActivity implements OnClickListener {
	private final static String TAG = "BidRewardActivity";
	private Button bt_bid;
	private Button bt_load;
	private Button bt_show;
	private Button bt_mute;
	private Button bt_unmute;
	private CommonTitleLayout mTitleLayout;
	private MTGBidRewardVideoHandler mMTGRewardVideoHandler;
	private ProgressBar mProgressBar;
	private NetStateOnReceive mNetStateOnReceive;
	private Dialog mDialog;
	private String mRewardUnitId = "146874";
	private String mRewardId = "12817";
	private String mUserId = "123";
	private String bidToken = "";
	private BidManager manager;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_reward;
	}

	@Override
	public void initView() {
		bt_bid = (Button) findViewById(R.id.bt_bid);
		bt_bid.setVisibility(View.VISIBLE);
		bt_load = (Button) findViewById(R.id.bt_load);
		bt_show = (Button) findViewById(R.id.bt_show);
		bt_mute = (Button) findViewById(R.id.bt_mute);
		bt_unmute = (Button) findViewById(R.id.bt_unmute);
		mProgressBar = (ProgressBar) findViewById(R.id.mintegral_demo_progress);
		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
	}

	@Override
	public void initData() {
		initHandler();

	}

	@Override
	public void setListener() {
		mTitleLayout.setTitleText("Head Bid RewardVideo");
		bt_bid.setOnClickListener(this);
		bt_load.setOnClickListener(this);
		bt_show.setOnClickListener(this);
		bt_mute.setOnClickListener(this);
		bt_unmute.setOnClickListener(this);
	}

	private void showLoadding() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void hideLoadding() {
		mProgressBar.setVisibility(View.GONE);
	}

	private void initHandler() {
		try {
			// Declare network status for downloading video
			if (mNetStateOnReceive == null) {
				mNetStateOnReceive = new NetStateOnReceive();
				IntentFilter filter = new IntentFilter();
				filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
				registerReceiver(mNetStateOnReceive, filter);
			}


			//mMTGRewardVideoHandler = new MTGBidRewardVideoHandler(this, mRewardUnitId);
			mMTGRewardVideoHandler = new MTGBidRewardVideoHandler(this, "138786", mRewardUnitId);
			mMTGRewardVideoHandler.setRewardVideoListener(new RewardVideoListener() {

				@Override
				public void onLoadSuccess(String placementId, String unitId) {
					Log.e(TAG, "onLoadSuccess:"+Thread.currentThread());
					Toast.makeText(getApplicationContext(), "onLoadSuccess()", Toast.LENGTH_LONG).show();

				}

				@Override
				public void onVideoLoadSuccess(String placementId, String unitId) {
					Log.e(TAG, "onVideoLoadSuccess:"+Thread.currentThread());
					hideLoadding();
					Toast.makeText(getApplicationContext(), "onVideoLoadSuccess()", Toast.LENGTH_LONG).show();

				}

				@Override
				public void onVideoLoadFail(String errorMsg) {
					Log.e(TAG, "onVideoLoadFail errorMsg:"+errorMsg);
					hideLoadding();
					Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
				}

				@Override
				public void onShowFail(String errorMsg) {
					Log.e(TAG, "onShowFail=" + errorMsg);
					Toast.makeText(getApplicationContext(), "errorMsg:" + errorMsg, Toast.LENGTH_LONG).show();
				}

				@Override
				public void onAdShow() {
					Log.e(TAG, "onAdShow");
					Toast.makeText(getApplicationContext(), "onAdShow", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onAdClose(boolean isCompleteView, String RewardName, float RewardAmout) {
					Log.e(TAG, "onAdClose rewardinfo :" + "RewardName:" + RewardName + "RewardAmout:" + RewardAmout+" isCompleteView："+isCompleteView);
					if(isCompleteView){
						Toast.makeText(getApplicationContext(),"onADClose:"+isCompleteView+",rName:"+RewardName +"，RewardAmout:"+RewardAmout,Toast.LENGTH_LONG).show();
						showDialog(RewardName, RewardAmout);
					}else{
						Toast.makeText(getApplicationContext(),"onADClose:"+isCompleteView+",rName:"+RewardName +"，RewardAmout:"+RewardAmout,Toast.LENGTH_LONG).show();
					}
				}

				@Override
				public void onVideoAdClicked(String placementId, String unitId) {
					Log.e(TAG, "onVideoAdClicked");
					Toast.makeText(getApplicationContext(), "onVideoAdClicked", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onVideoComplete(String placementId, String unitId) {
					Log.e(TAG, "onVideoComplete");
					Toast.makeText(getApplicationContext(), "onVideoComplete", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onEndcardShow(String placementId, String unitId) {
					Log.e(TAG, "onEndcardShow");
					Toast.makeText(getApplicationContext(), "onEndcardShow", Toast.LENGTH_LONG).show();
				}

			});
			//manager = new BidManager(mRewardUnitId);
			manager = new BidManager("138786", mRewardUnitId, "0");
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
			// set this before bid
			manager.setRewardPlus(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_bid:
				if (manager != null) {
					manager.bid();
				}
				break;
			case R.id.bt_load:
				showLoadding();
				mMTGRewardVideoHandler.loadFromBid(bidToken);
				break;
			case R.id.bt_show:
				if (mMTGRewardVideoHandler.isBidReady()) {
					mMTGRewardVideoHandler.showFromBid(mRewardId, mUserId);
				}
				break;
			case R.id.bt_mute:
				if (mMTGRewardVideoHandler != null) {
					Toast.makeText(getApplicationContext(),"bt_mute",Toast.LENGTH_LONG).show();
					mMTGRewardVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_MUTE);
				}
				break;
			case R.id.bt_unmute:
				if (mMTGRewardVideoHandler != null) {
					Toast.makeText(getApplicationContext(),"bt_unmute",Toast.LENGTH_LONG).show();
					mMTGRewardVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_NOT_MUTE);
				}
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (mDialog != null) {
				mDialog = null;
			}
			if (mNetStateOnReceive != null) {
				unregisterReceiver(mNetStateOnReceive);
			}
			if(mMTGRewardVideoHandler != null){
				mMTGRewardVideoHandler.setRewardVideoListener(null);
			}
			if(manager != null){
				manager.setBidListener(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showDialog(String RewardName, float RewardAmout) {
		try {
			mDialog = new Dialog(BidRewardActivity.this);
			View view = View.inflate(BidRewardActivity.this, R.layout.dialog_reward, null);
			TextView tvRewardName = (TextView) view.findViewById(R.id.tv_rewardName);
			TextView tvRewardAmout = (TextView) view.findViewById(R.id.tv_RewardAmout);
			tvRewardName.setText(RewardName + "");
			tvRewardAmout.setText(RewardAmout + "");
			mDialog.setContentView(view);
			mDialog.show();
		}catch (Throwable t){
			t.printStackTrace();
		}
	}





}
