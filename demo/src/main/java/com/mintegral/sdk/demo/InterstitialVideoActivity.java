package com.mintegral.sdk.demo;

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
import com.mintegral.msdk.interstitialvideo.out.InterstitialVideoListener;
import com.mintegral.msdk.interstitialvideo.out.MTGInterstitialVideoHandler;
import com.mintegral.msdk.videocommon.download.NetStateOnReceive;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

public class InterstitialVideoActivity extends BaseActivity implements OnClickListener {
	private final static String TAG = "InterVideoActivity";
	private Button bt_load;
	private Button bt_show;
	private Button bt_mute;
	private Button bt_unmute;
	private CommonTitleLayout mTitleLayout;
	private MTGInterstitialVideoHandler mMtgInterstitalVideoHandler;
	private ProgressBar mProgressBar;
	private NetStateOnReceive mNetStateOnReceive;
	private Dialog mDialog;
	private String mRewardUnitId = "146869";
	private String mRewardId = "12817";
	private String mUserId = "123";

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_reward;
	}

	@Override
	public void initView() {
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
		mTitleLayout.setTitleText("InterstitialVideo");
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

			//mMtgInterstitalVideoHandler = new MTGInterstitialVideoHandler(this, mRewardUnitId);
			mMtgInterstitalVideoHandler = new MTGInterstitialVideoHandler(this, "138781", mRewardUnitId);
			mMtgInterstitalVideoHandler.setInterstitialVideoListener(new InterstitialVideoListener() {

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
				public void onAdClose(boolean isCompleteView) {
					Log.e(TAG, "onAdClose rewardinfo :" +  "isCompleteView："+isCompleteView);
					Toast.makeText(getApplicationContext(),"onADClose:"+isCompleteView,Toast.LENGTH_LONG).show();
				}

				@Override
				public void onVideoAdClicked(String placementId, String unitId) {
					Log.e(TAG, "onVideoAdClicked");
					Toast.makeText(getApplicationContext(), "onVideoAdClicked", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onVideoComplete(String placementId, String unitId) {
					Log.e(TAG,"onVideoComplete");
					Toast.makeText(getApplicationContext(),"onVideoComplete",Toast.LENGTH_LONG).show();
				}

				/**
				 * If you called {@link MTGInterstitialVideoHandler#setIVRewardEnable(int, int)}
				 * will callback this method.
				 * You can decide whether to give users a reward based on the return value.
				 *
				 * @param isComplete complete status.
				 *                   This parameter indicates whether the video or playable has finished playing.
				 *
				 * @param rewardAlertStatus interstitialVideo reward  alert  window status.
				 *                          This parameter is used to indicate the status of the alert dialog.
				 *                          If the dialog is not shown, it will return {@link MIntegralConstans#IVREWARDALERT_STATUS_NOTSHOWN}
				 *                          If the user clicks the dialog's continue button, it will return {@link MIntegralConstans#IVREWARDALERT_STATUS_CLICKCONTINUE}
				 *                          If the user clicks the dialog's cancel button, it will return {@link MIntegralConstans#IVREWARDALERT_STATUS_CLICKCANCEL}
				 *
				 */
				@Override
				public void onAdCloseWithIVReward(boolean isComplete, int rewardAlertStatus) {
					Log.e(TAG, "onAdCloseWithIVReward");
					Log.e(TAG, isComplete ? "Video playback/playable is complete." : "Video playback/playable is not complete.");

					if (rewardAlertStatus == MIntegralConstans.IVREWARDALERT_STATUS_NOTSHOWN) {
						Log.e(TAG,"The dialog is not show.");
					}

					if (rewardAlertStatus == MIntegralConstans.IVREWARDALERT_STATUS_CLICKCONTINUE) {
						Log.e(TAG,"The dialog's continue button clicked.");
					}

					if (rewardAlertStatus == MIntegralConstans.IVREWARDALERT_STATUS_CLICKCANCEL) {
						Log.e(TAG,"The dialog's cancel button clicked.");
					}
				}

				@Override
				public void onEndcardShow(String placementId, String unitId) {
					Log.e(TAG,"onEndcardShow");
					Toast.makeText(getApplicationContext(),"onEndcardShow",Toast.LENGTH_LONG).show();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_load:
				showLoadding();
				if (mMtgInterstitalVideoHandler != null) {

					/**
					 * Optional.
					 *
					 * NOTE : Please make sure that you call before {@link MTGInterstitialVideoHandler#load()} method.
					 *
					 * Set the reward mode for interstitial video.
					 *
					 * Call this method when you want the IV to have a reward function.
					 * After calling this method, When the video or playable is playing,
					 * or the user clicked close button,
					 * a {@link android.app.AlertDialog} will appear at the set time,
					 * reminding the user to watch or play will get a reward.
					 *
					 * when IV closed will be called {@link InterstitialVideoListener#onAdCloseWithIVReward(boolean, int)},
					 * you can decide whether to give the reward based on that callback.
					 *
					 * @param rewardType Set the reward alert show time.
					 *                 {@link MIntegralConstans#IVREWARD_TYPE_CLOSEMODE} used when the user closes the video or playable.
					 *                 {@link MIntegralConstans#IVREWARD_TYPE_PLAYMODE} used to get rewards after playing the video for a few seconds.
					 *
					 * @param value Set the value of the interstitial video reward mode type.
					 *              In PLAYMODE, this value indicates a {@link android.app.AlertDialog} will appear after a few seconds of playback.
					 *              In CLOSEMODE, this value indicates when the user click the close button before the set time, {@link android.app.AlertDialog} will appear.
					 *
					 *              You can set {@link Integer} or {@link Double},
					 *              Integer values represent seconds and Double values represent percentages.
					 *              the Integer value range is 0-100, Double value range is 0.0-1.0.
					 *              The default value is 5s OR 80%(0.8).
					 */
					mMtgInterstitalVideoHandler.setIVRewardEnable(MIntegralConstans.IVREWARD_TYPE_CLOSEMODE, 30);

					/**
					 * Optional.
					 *
					 * Call this method when you wanna custom the reward alert dialog display text.
					 *
					 * @param confirmTitle title text for reward dialog.
					 * @param confirmContent content text for reward dialog.
					 * @param cancelText cancel button text for reward dialog.
					 * @param confirmText confirm button text for reward dialog.
					 */
					mMtgInterstitalVideoHandler.setAlertDialogText("Continue?", "If you continue, you can have reward when ad close.", "Continue", "Cancel");

					mMtgInterstitalVideoHandler.load();
				}
				break;
			case R.id.bt_show:
				if (mMtgInterstitalVideoHandler != null && mMtgInterstitalVideoHandler.isReady()) {
					mMtgInterstitalVideoHandler.show();
				}
				break;
			case R.id.bt_mute:
				if (mMtgInterstitalVideoHandler != null) {
					Toast.makeText(getApplicationContext(),"bt_mute",Toast.LENGTH_LONG).show();
					mMtgInterstitalVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_MUTE);
				}
				break;
			case R.id.bt_unmute:
				if (mMtgInterstitalVideoHandler != null) {
					Toast.makeText(getApplicationContext(),"bt_unmute",Toast.LENGTH_LONG).show();
					mMtgInterstitalVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_NOT_MUTE);
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
			if(mMtgInterstitalVideoHandler != null){
				mMtgInterstitalVideoHandler.setInterstitialVideoListener(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showDialog(String RewardName, float RewardAmout) {
		try {
			mDialog = new Dialog(InterstitialVideoActivity.this);
			View view = View.inflate(InterstitialVideoActivity.this, R.layout.dialog_reward, null);
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
