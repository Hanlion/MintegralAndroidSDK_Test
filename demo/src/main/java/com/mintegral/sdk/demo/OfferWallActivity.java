//package com.mintegral.sdk.demo;
//
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.mintegral.msdk.MIntegralConstans;
//import com.mintegral.msdk.out.MTGOfferWallHandler;
//import com.mintegral.msdk.out.MTGOfferWallRewardListener;
//import com.mintegral.msdk.out.OfferWallListener;
//import com.mintegral.msdk.out.OfferWallRewardInfo;
//import com.mintegral.sdk.demo.view.CommonTitleLayout;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * offerWall
// *
// * @author simon
// *
// */
//public class OfferWallActivity extends BaseActivity implements OnClickListener {
//
//	public static final String TAG = "OfferWallActivity";
//	private Button mBtLoad;
//	private Button mBtShow;
//	private Button mBtQuery;
//	private MTGOfferWallHandler mOfferWallHandler;
//	private CommonTitleLayout mTitleLayout;
//	private ProgressBar mProgressBar;
//	private String mUnitId = "146877";
//	private String mUserId= "123";
//
//	@Override
//	public int getResLayoutId() {
//		return R.layout.mintegral_demo_atv_offerwall;
//	}
//
//	@Override
//	public void initView() {
//		mBtLoad = (Button) findViewById(R.id.bt_load);
//		mBtShow = (Button) findViewById(R.id.bt_show);
//		mBtQuery = (Button) findViewById(R.id.bt_query_reward);
//		mProgressBar = (ProgressBar) findViewById(R.id.mintegral_demo_progress);
//		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
//	}
//
//	@Override
//	public void initData() {
//		mTitleLayout.setTitleText("OfferWall");
//		initHandler();
//	}
//
//	@Override
//	public void setListener() {
//		mBtLoad.setOnClickListener(this);
//		mBtShow.setOnClickListener(this);
//		mBtQuery.setOnClickListener(this);
//	}
//
//	private void initHandler() {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		// Set placementId Must.... Not yet.
//		hashMap.put(MIntegralConstans.PLACEMENT_ID, "138789");
//		// Set adID Must
//		hashMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, mUnitId);
//		// Set userId Must
//		hashMap.put(MIntegralConstans.OFFER_WALL_USER_ID, mUserId);
//		// Set offerWall title backgroud color
//		hashMap.put(MIntegralConstans.OFFER_WALL_TITLE_BACKGROUD_COLOR, R.color.mintegral_demo_white);
//		// Set offerWall title text
//		hashMap.put(MIntegralConstans.OFFER_WALL_TITLE_TEXT, "MIntegralOfferWall");
//		// Set offerWall title font color
//		hashMap.put(MIntegralConstans.OFFER_WALL_TITLE_FONT_COLOR, R.color.mintegral_demo_green);
//		// Set offerWall title font size
//		hashMap.put(MIntegralConstans.OFFER_WALL_TITLE_FONT_SIZE, 50);
//		// Set offerWall title font typeface
//		hashMap.put(MIntegralConstans.OFFER_WALL_TITLE_FONT_TYPEFACE, MIntegralConstans.TITLE_TYPEFACE_DEFAULT_BOLD);
//		// Set offerWall reward b_video did not see whether to open the dialog
//		// box，default false
//		hashMap.put(MIntegralConstans.OFFER_WALL_REWARD_OPEN_WARN, true);
//		// Set offerWall reward b_video Dialog box to close the text description
//		hashMap.put(MIntegralConstans.OFFER_WALL_REWARD_VIDEO_STOP_TEXT, "Stop");
//		// Set offerWall reward b_video Dialog box to cancel the text description
//		hashMap.put(MIntegralConstans.OFFER_WALL_REWARD_VIDEO_RESUME_TEXT, "Resume");
//		// Set offerWall reward b_video Dialog prompt info
//		hashMap.put(MIntegralConstans.OFFER_WALL_REWARD_VIDEO_WARN_TEXT,
//				"No rewards earned yet! Complete b_video to earn rewards ?");
//		// instantiation MTGOfferWallHandler Object
//		mOfferWallHandler = new MTGOfferWallHandler(this, hashMap);
//		mOfferWallHandler.setOfferWallListener(new OfferWallListener() {
//
//			@Override
//			public void onOfferWallShowFail(String errorMsg) {
//				Log.e(TAG, "onOfferWallShowFail:" + errorMsg);
//			}
//
//			@Override
//			public void onOfferWallOpen() {
//				Log.e(TAG, "onOfferWallOpen");
//			}
//
//			@Override
//			public void onOfferWallLoadSuccess() {
//				Log.e(TAG, "onOfferWallLoadSuccess");
//				Toast.makeText(getApplicationContext(), "onOfferWallLoadSuccess", Toast.LENGTH_LONG).show();
//				hideLoadding();
//			}
//
//			@Override
//			public void onOfferWallLoadFail(String errorMsg) {
//				Log.e(TAG, "onOfferWallLoadFail:" + errorMsg);
//				Toast.makeText(getApplicationContext(), "onOfferWallLoadFail:" + errorMsg, Toast.LENGTH_LONG).show();
//			}
//
//			/**
//			 * when the b_video see 80% callback reward
//			 */
//			@Override
//			public void onOfferWallCreditsEarned(String rewardName, int rewardAmount) {
//				Log.e(TAG, "onOfferWallCreditsEarned rewardName:" + rewardName + " rewardAmount:" + rewardAmount);
//				Toast.makeText(getApplicationContext(), "rewardInfo:" + rewardName + rewardAmount, Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void onOfferWallClose() {
//				Log.e(TAG, "onOfferWallClose");
//			}
//
//			@Override
//			public void onOfferWallAdClick() {
//				Log.e(TAG, "onOfferWallAdClick");
//			}
//		});
//	}
//
//	private void showLoadding() {
//		mProgressBar.setVisibility(View.VISIBLE);
//	}
//
//	private void hideLoadding() {
//		mProgressBar.setVisibility(View.GONE);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt_load:
//
//			if (mOfferWallHandler != null) {
//				showLoadding();
//				mOfferWallHandler.load();
//			}
//			break;
//		case R.id.bt_show:
//
//			if (mOfferWallHandler != null) {
//				mOfferWallHandler.show();
//			}
//			break;
//		case R.id.bt_query_reward:
//			if (mOfferWallHandler != null) {
//				// query rewardInfo
//				mOfferWallHandler.queryOfferWallRewards(new MTGOfferWallRewardListener() {
//
//					/**
//					 * call back rewardInfo
//					 */
//					@Override
//					public void responseRewardInfo(List<OfferWallRewardInfo> offerWallRewardInfos) {
//						if (offerWallRewardInfos != null && offerWallRewardInfos.size() > 0) {
//							for (OfferWallRewardInfo rewardInfo : offerWallRewardInfos) {
//								Toast.makeText(OfferWallActivity.this,
//										rewardInfo.getRewardName() + " " + rewardInfo.getRewardAmount(), Toast.LENGTH_LONG).show();
//							}
//						}
//
//					}
//				});
//			}
//			break;
//		}
//	}
//
//}
