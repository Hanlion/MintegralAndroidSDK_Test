package com.mintegral.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.out.Campaign;
import com.mintegral.msdk.out.Frame;
import com.mintegral.msdk.out.MIntegralSDKFactory;
import com.mintegral.msdk.out.MtgNativeHandler;
import com.mintegral.msdk.out.NativeListener.NativeAdListener;
import com.mintegral.msdk.out.NativeListener.NativeTrackingListener;
import com.mintegral.msdk.widget.MTGAdChoice;
import com.mintegral.sdk.demo.util.ImageLoadTask;

public class NativeActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = NativeActivity.class.getName();
	private Button mBtNative;
	private Button mBtPreloadNative;
	private Spinner mSpinner;
	private static final String sMTGAdSouceUnitID = "146868";

	public int AD_NUM = 1;
	private String mCurrentUnitId = "";

	private MtgNativeHandler mNativeHandle;
	private Campaign mCampaign;
	private FrameLayout mFlAdHolder;
	private TextView mTvTitle;
	private ImageView mIvDisplayArea;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_native;
	}

	@Override
	public void initView() {
		mBtPreloadNative = (Button) findViewById(R.id.btPreNative);
		mBtNative = (Button) findViewById(R.id.btNative);
		mTvTitle = (TextView) findViewById(R.id.mintegral_demo_tv_title);
		mFlAdHolder = (FrameLayout) findViewById(R.id.fl_adplaceholder);
		mSpinner = (Spinner) findViewById(R.id.mintegral_demo_native_sp);
		mIvDisplayArea = (ImageView) findViewById(R.id.mintegral_demo_iv_display_area);

	}

	@Override
	public void initData() {
		mTvTitle.setText("NATIVE");
		List<String> adSourceList = new ArrayList<String>();
		adSourceList.add("MTG");
		ArrayAdapter<String> adSouceAdapter = new ArrayAdapter<String>(this, R.layout.mintegral_demo_native_sp_display,
				R.id.mintegral_demo_tv_sp_name, adSourceList);
		adSouceAdapter.setDropDownViewResource(R.layout.mintegral_demo_sp_dropdown_adsouce);
		mSpinner.setAdapter(adSouceAdapter);
	}

	@Override
	public void setListener() {
		mTvTitle.setOnClickListener(this);
		mBtPreloadNative.setOnClickListener(this);
		mBtNative.setOnClickListener(this);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				setUnitId(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private void setUnitId(int position) {
		switch (position) {
			default:
				mCurrentUnitId = sMTGAdSouceUnitID;
				break;
		}
	}

	private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd, NativeAppInstallAdView adView) {
		adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
		adView.setImageView(adView.findViewById(R.id.appinstall_image));
		adView.setBodyView(adView.findViewById(R.id.appinstall_body));
		adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
		adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
		adView.setPriceView(adView.findViewById(R.id.appinstall_price));
		adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
		adView.setStoreView(adView.findViewById(R.id.appinstall_store));

		((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
		((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
		((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
		((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
		((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
		((ImageView) adView.getIconView()).setImageDrawable(nativeAppInstallAd.getIcon().getDrawable());
		((RatingBar) adView.getStarRatingView()).setRating(nativeAppInstallAd.getStarRating().floatValue());

		List<NativeAd.Image> images = nativeAppInstallAd.getImages();

		if (images.size() > 0) {
			((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
		}

		// Assign native ad object to the native view.
		mNativeHandle.registerView(adView, mCampaign);
	}

	/**
	 * Populates a {@link NativeContentAdView} object with data from a given
	 * {@link NativeContentAd}.
	 *
	 * @param nativeContentAd
	 *            the object containing the ad's assets
	 * @param adView
	 *            the view to be populated
	 */
	private void populateContentAdView(NativeContentAd nativeContentAd, NativeContentAdView adView) {
		adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
		adView.setImageView(adView.findViewById(R.id.contentad_image));
		adView.setBodyView(adView.findViewById(R.id.contentad_body));
		adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
		adView.setLogoView(adView.findViewById(R.id.contentad_logo));
		adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

		((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
		((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
		((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
		((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

		List<NativeAd.Image> images = nativeContentAd.getImages();

		if (images != null && images.size() > 0) {
			((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
		}

		NativeAd.Image logoImage = nativeContentAd.getLogo();

		if (logoImage != null) {
			((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
		}

		// Assign native ad object to the native view.
		mNativeHandle.registerView(adView, mCampaign);
	}

	public void preloadNative() {

		MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
		Map<String, Object> preloadMap = new HashMap<String, Object>();
		preloadMap.put(MIntegralConstans.PROPERTIES_LAYOUT_TYPE, MIntegralConstans.LAYOUT_NATIVE);
		// preloadMap.put(MIntegralConstans.ID_FACE_BOOK_PLACEMENT,
		// "1611993839047594_1614040148842963");
		// preloadMap.put(MIntegralConstans.ID_MY_TARGET_AD_UNITID, "6590");
		preloadMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, mCurrentUnitId);
		preloadMap.put(MIntegralConstans.PLACEMENT_ID, "138780");

		preloadMap.put(MIntegralConstans.PROPERTIES_AD_NUM, AD_NUM);
		preloadMap.put(MIntegralConstans.PREIMAGE, true);
		// sdk.setAdMobClickListener(new AdMobClickListener() {
		//
		// @Override
		// public void onAdMobClickListener(Campaign campaign) {
		// Log.e("mintegral_demo", "admob is clicked");
		//
		// }
		// });
		sdk.preload(preloadMap);

	}

	public void loadNative() {
		//Map<String, Object> properties = MtgNativeHandler.getNativeProperties(mCurrentUnitId);
		Map<String, Object> properties = MtgNativeHandler.getNativeProperties("138780", mCurrentUnitId);
		// properties.put(MIntegralConstans.ID_FACE_BOOK_PLACEMENT,
		// "448464445332858_460157654163537");
		// properties.put(MIntegralConstans.ID_ADMOB_UNITID,
		// "ca-app-pub-3940256099942544/2247696110");
		// properties.put(MIntegralConstans.ID_MY_TARGET_AD_UNITID, "6590");
		// properties.put(MIntegralConstans.ADMOB_AD_TYPE,
		// MIntegralConstans.ADMOB_AD_TYPE_APP_INSTALL);
		// properties.put(MIntegralConstans.ADMOB_AD_TYPE,
		// MIntegralConstans.ADMOB_AD_TYPE_CONTENT);
		properties.put(MIntegralConstans.PROPERTIES_AD_NUM, AD_NUM);
		mNativeHandle = new MtgNativeHandler(properties, this);
		mNativeHandle.setAdListener(new NativeAdListener() {

			@Override
			public void onAdLoaded(List<Campaign> campaigns, int template) {
				Log.e(TAG, "onAdLoaded");
				if (campaigns != null && campaigns.size() > 0) {
					mIvDisplayArea.setVisibility(View.GONE);
					mCampaign = campaigns.get(0);
					for (Campaign campaign : campaigns) {
						Log.i(TAG, campaign.getAppName());
					}
//					if (mCampaign.getType() == MIntegralConstans.AD_TYPE_ADMOB) {
//						fillAdMobAdLayout();
//					} else if (mCampaign.getType() == MIntegralConstans.AD_TYPE_MYTARGET) {
//						fillMyTargetAdLayout();
//					} else {
						fillMTGAdLayout();
//					}
					preloadNative();
				}

			}

			private void fillMTGAdLayout() {
				final View view = LayoutInflater.from(NativeActivity.this)
						.inflate(R.layout.mintegral_demo_mul_big_ad_content, null);
				final ImageView iv = (ImageView) view.findViewById(R.id.mintegral_demo_iv_image);
				if (!TextUtils.isEmpty(mCampaign.getImageUrl())) {
					new ImageLoadTask(mCampaign.getImageUrl()) {

						@Override
						public void onRecived(Drawable result) {
							iv.setImageDrawable(result);
							mNativeHandle.registerView(view, mCampaign);
						}
					}.execute();
				}
				TextView tvAppName = (TextView) view.findViewById(R.id.mintegral_demo_bt_app_name);
				MTGAdChoice mtgAdChoice = view.findViewById(R.id.mintegral_demo_native_adchoice);
				int height = mCampaign.getAdchoiceSizeHeight();
				int width = mCampaign.getAdchoiceSizeWidth();
				ViewGroup.LayoutParams layoutParams = mtgAdChoice.getLayoutParams();
				layoutParams.width =width;
				layoutParams.height=height;
				mtgAdChoice.setLayoutParams(layoutParams);
				mtgAdChoice.setCampaign(mCampaign);
				tvAppName.setText(mCampaign.getAppName());
				mFlAdHolder.removeAllViews();
				mFlAdHolder.addView(view);

				// List<View> list = new ArrayList<View>();
				// list.add(iv);
				// list.add(view);
				// list.add(tvAppName);
				// mNativeHandle.registerView(tvAppName, list, mCampaign);
			}

//			private void fillFBAdLayout() {
//				final View view = LayoutInflater.from(NativeActivity.this)
//						.inflate(R.layout.mintegral_demo_mul_big_ad_content, null);
//				RelativeLayout rootView = (RelativeLayout) view.findViewById(R.id.mintegral_demo_rl_ad);
//				final ImageView iv = (ImageView) view.findViewById(R.id.mintegral_demo_iv_image);
//				if (!TextUtils.isEmpty(mCampaign.getImageUrl())) {
//					new ImageLoadTask(mCampaign.getImageUrl()) {
//
//						@Override
//						public void onRecived(Drawable result) {
//							iv.setImageDrawable(result);
//							mNativeHandle.registerView(view, mCampaign);
//						}
//					}.execute();
//				}
//				TextView tvAppName = (TextView) view.findViewById(R.id.movbista_demo_bt_app_name);
//				tvAppName.setText(mCampaign.getAppName());
//				com.facebook.ads.NativeAd ad = (com.facebook.ads.NativeAd) mCampaign.getNativead();
//				AdChoicesView choice = new AdChoicesView(NativeActivity.this, ad);
//				LayoutParams param_rlayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//				param_rlayout.leftMargin = 10;
//				param_rlayout.topMargin = 10;
//				rootView.addView(choice, param_rlayout);
//				mFlAdHolder.removeAllViews();
//				mFlAdHolder.addView(view);
//			}

//			private void fillMyTargetAdLayout() {
//				NativePromoAd ad = (NativePromoAd) mCampaign.getNativead();
//				mContentStreamAdView = NativeViewsFactory.getContentStreamView(ad, NativeActivity.this);
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//				mFlAdHolder.addView(mContentStreamAdView, params);
//				mContentStreamAdView.loadImages();
//				mNativeHandle.registerView(mContentStreamAdView, mCampaign);
//			}

//			private void fillAdMobAdLayout() {
//				if (MIntegralConstans.ADMOB_AD_TYPE_APP_INSTALL.equals(mCampaign.getSubType())) {
//					NativeAppInstallAd ad = (NativeAppInstallAd) mCampaign.getNativead();
//					Log.i(" ", "appinstall admob = " + ad.getHeadline().toString());
//					NativeAppInstallAdView adView = (NativeAppInstallAdView) getLayoutInflater()
//							.inflate(R.layout.ad_app_install, null);
//					populateAppInstallAdView(ad, adView);
//					mFlAdHolder.removeAllViews();
//					mFlAdHolder.addView(adView);
//				} else if (MIntegralConstans.ADMOB_AD_TYPE_CONTENT.equals(mCampaign.getSubType())) {
//					NativeContentAd ad = (NativeContentAd) mCampaign.getNativead();
//					Log.i(" ", "appcontent admob = " + ad.getHeadline().toString());
//					NativeContentAdView adView = (NativeContentAdView) getLayoutInflater().inflate(R.layout.ad_content,
//							null);
//					populateContentAdView(ad, adView);
//					mFlAdHolder.removeAllViews();
//					mFlAdHolder.addView(adView);
//				}
//			}

			@Override
			public void onAdLoadError(String message) {
				Log.e("", "onAdLoadError" + message);
			}

			@Override
			public void onAdClick(Campaign campaign) {
				Log.e("", "onAdClick");
			}

			@Override
			public void onAdFramesLoaded(final List<Frame> list) {

			}

			@Override
			public void onLoggingImpression(int adsourceType) {
				Log.e(TAG, "onLoggingImpression adsourceType:" + adsourceType);
			}

		});
		mNativeHandle.setTrackingListener(new NativeTrackingListener() {

			@Override
			public void onStartRedirection(Campaign campaign, String url) {
				Log.e("pro", "onStartRedirection---");
			}

			@Override
			public void onRedirectionFailed(Campaign campaign, String url) {
				// TODO Auto-generated method stub
				Log.e("pro", "onRedirectionFailed---");
			}

			@Override
			public void onFinishRedirection(Campaign campaign, String url) {
				Log.e("pro", "onFinishRedirection---"+url);
			}

			@Override
			public void onDownloadStart(Campaign campaign) {
				Log.e("pro", "start---");
			}

			@Override
			public void onDownloadFinish(Campaign campaign) {
				Log.e("pro", "finish---");

			}

			@Override
			public void onDownloadProgress(int progress) {
				Log.e("pro", "progress----" + progress);
			}

			@Override
			public boolean onInterceptDefaultLoadingDialog() {
				return false;
			}

			@Override
			public void onShowLoading(Campaign campaign) {

			}

			@Override
			public void onDismissLoading(Campaign campaign) {

			}
		});

		mNativeHandle.load();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.btPreNative:
				preloadNative();
				break;
			case R.id.btNative:
				loadNative();
				break;
			case R.id.mintegral_demo_tv_title:
				finish();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mNativeHandle != null) {
			mNativeHandle.release();
		}
	}

}