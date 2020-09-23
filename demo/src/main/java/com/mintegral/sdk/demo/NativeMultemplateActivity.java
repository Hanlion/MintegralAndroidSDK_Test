package com.mintegral.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mintegral.msdk.out.NativeListener.Template;
import com.mintegral.sdk.demo.adapter.CommonAdapter;
import com.mintegral.sdk.demo.adapter.ViewHolder;
import com.mintegral.sdk.demo.util.ImageLoadTask;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

public class NativeMultemplateActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = NativeMultemplateActivity.class.getName();
	private Button mBtNative;
	private Button mBtnPreloadNative;
	private Spinner mSpinner;
	private static final String sMTGAdSouceUnitID = "146868";
	private String mCurrentUnitId = "";

	public int BIG_IMG_REQUEST_AD_NUM = 1;
	public int MULTIPLE_IMG_REQUEST_AD_NUM = 3;
	public int AD_NUM = 1;
	MtgNativeHandler mNativeHandler;
	Campaign mCampaign;
	FrameLayout mFlAdHolder;
	private TextView mTvTitle;
	private ImageView mIvDisplayArea;

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
		mNativeHandler.registerView(adView, mCampaign);
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
		mNativeHandler.registerView(adView, mCampaign);
	}

	public void preloadNative() {

		MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
		Map<String, Object> preloadMap = new HashMap<String, Object>();
		preloadMap.put(MIntegralConstans.PROPERTIES_LAYOUT_TYPE, MIntegralConstans.LAYOUT_NATIVE);
		preloadMap.put(MIntegralConstans.ID_FACE_BOOK_PLACEMENT, "1611993839047594_1614040148842963");
		preloadMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, mCurrentUnitId);
		preloadMap.put(MIntegralConstans.PLACEMENT_ID, "138780");

		List<Template> list = new ArrayList<Template>();
		// request 1 big image ad
		list.add(new Template(MIntegralConstans.TEMPLATE_BIG_IMG, BIG_IMG_REQUEST_AD_NUM));
		// request 3 multi-image ads
		list.add(new Template(MIntegralConstans.TEMPLATE_MULTIPLE_IMG, MULTIPLE_IMG_REQUEST_AD_NUM));
		//Large map and multi-map at the same time, priority to return to multi-map
		preloadMap.put(MIntegralConstans.NATIVE_INFO, MtgNativeHandler.getTemplateString(list));
		sdk.preload(preloadMap);

	}

	public void loadNative() {
		//Map<String, Object> properties = MtgNativeHandler.getNativeProperties(mCurrentUnitId);
		Map<String, Object> properties = MtgNativeHandler.getNativeProperties("138780", mCurrentUnitId);
//		properties.put(MIntegralConstans.ID_FACE_BOOK_PLACEMENT, "448464445332858_460157654163537");
//		properties.put(MIntegralConstans.ID_ADMOB_UNITID, "ca-app-pub-3940256099942544/2247696110");
		// properties.put(MIntegralConstans.ADMOB_AD_TYPE,
		// MIntegralConstans.ADMOB_AD_TYPE_APP_INSTALL);
		// properties.put(MIntegralConstans.ADMOB_AD_TYPE,
		// MIntegralConstans.ADMOB_AD_TYPE_CONTENT);
		if (mNativeHandler == null) {
			mNativeHandler = new MtgNativeHandler(properties, this);
		}
		
		// request 1 big image ad
		mNativeHandler.addTemplate(new Template(MIntegralConstans.TEMPLATE_BIG_IMG, BIG_IMG_REQUEST_AD_NUM));
		// request 3 multi-image ads 
		// Large map and multi-map at the same time, priority to return to multi-map
		mNativeHandler.addTemplate(new Template(MIntegralConstans.TEMPLATE_MULTIPLE_IMG, MULTIPLE_IMG_REQUEST_AD_NUM));

		mNativeHandler.setAdListener(new NativeAdListener() {

			// All the third-party advertising sources, FB, ADMOB, MYTARGET, etc. are all large template
			@Override
			public void onAdLoaded(List<Campaign> campaigns, int template) {
				if (campaigns != null && campaigns.size() > 0) {
					mIvDisplayArea.setVisibility(View.GONE);
					mCampaign = campaigns.get(0);
					for (Campaign campaign : campaigns) {
						Log.i(" ", campaign.getAppName());
					}
//					if (mCampaign.getType() == MIntegralConstans.AD_TYPE_ADMOB) {
//						fillAdMobAdLayout();
//					} else {
						fillMTGAdLayout(campaigns, template);
//					}

				}
				Log.e(TAG, "onAdLoaded");

			    // auto proload for next time
				preloadNative();
			}

			private void fillMTGAdLayout(List<Campaign> campaigns, int template) {
				switch (template) {
				case MIntegralConstans.TEMPLATE_BIG_IMG:
					loadMTGBigTemplateLayout();
					break;
				case MIntegralConstans.TEMPLATE_MULTIPLE_IMG:
					loadMTGMulTemplateLayout(campaigns, template);
					break;
				}
			}

			private void loadMTGMulTemplateLayout(List<Campaign> campaigns, int template) {
				// Multi-graph template processing
				if (template == MIntegralConstans.TEMPLATE_MULTIPLE_IMG) {
					View view = LayoutInflater.from(NativeMultemplateActivity.this)
							.inflate(R.layout.mintegral_demo_mul_template_ad_mul_content, null);
					GridView gVMulTemplate = (GridView) view.findViewById(R.id.mintegral_demo_gv_mul_template);
					CommonAdapter<Campaign> adapter = new CommonAdapter<Campaign>(campaigns,
							R.layout.mintegral_demo_mul_template_ad_content, NativeMultemplateActivity.this) {

						@Override
						public void convert(ViewHolder helper, Campaign item) {
							final ImageView iconView = helper.getView(R.id.mintegral_demo_iv_image);
							new ImageLoadTask(item.getIconUrl()) {

								@Override
								public void onRecived(Drawable result) {
									iconView.setImageDrawable(result);
								}
							}.execute();
							helper.setText(R.id.mintegral_demo_bt_app_name, item.getAppName());
							mNativeHandler.registerView(helper.getConvertView(), item);
						}
					};
					gVMulTemplate.setAdapter(adapter);
					mFlAdHolder.removeAllViews();
					mFlAdHolder.addView(view);
				}
			}

			private void loadMTGBigTemplateLayout() {
				View view = LayoutInflater.from(NativeMultemplateActivity.this)
						.inflate(R.layout.mintegral_demo_mul_big_ad_content, null);
				final ImageView iv = (ImageView) view.findViewById(R.id.mintegral_demo_iv_image);
				new ImageLoadTask(mCampaign.getImageUrl()) {

					@Override
					public void onRecived(Drawable result) {
						iv.setImageDrawable(result);
					}
				}.execute();
				TextView tvAppName = (TextView) view.findViewById(R.id.mintegral_demo_bt_app_name);
				tvAppName.setText(mCampaign.getAppName());
				mFlAdHolder.removeAllViews();
				mFlAdHolder.addView(view);
				mNativeHandler.registerView(view, mCampaign);
				// List<View> list = new ArrayList<View>();
				// list.add(iv);
				// list.add(view);
				// list.add(btn);
				// mNativeHandler.registerView(btn, list, campaign);
			}

//			private void fillFBAdLayout() {
//				final View view = LayoutInflater.from(NativeMultemplateActivity.this)
//						.inflate(R.layout.mintegral_demo_mul_big_ad_content, null);
//				RelativeLayout rootView = (RelativeLayout) view.findViewById(R.id.mintegral_demo_rl_ad);
//				final ImageView iv = (ImageView) view.findViewById(R.id.mintegral_demo_iv_image);
//				if (!TextUtils.isEmpty(mCampaign.getImageUrl())) {
//					new ImageLoadTask(mCampaign.getImageUrl()) {
//
//						@Override
//						public void onRecived(Drawable result) {
//							iv.setImageDrawable(result);
//							mNativeHandler.registerView(view, mCampaign);
//						}
//					}.execute();
//				}
//				TextView tvAppName = (TextView) view.findViewById(R.id.movbista_demo_bt_app_name);
//				tvAppName.setText(mCampaign.getAppName());
//				com.facebook.ads.NativeAd ad = (com.facebook.ads.NativeAd) mCampaign.getNativead();
//				AdChoicesView choice = new AdChoicesView(NativeMultemplateActivity.this, ad);
//				LayoutParams param_rlayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//				param_rlayout.leftMargin = 10;
//				param_rlayout.topMargin = 10;
//				rootView.addView(choice, param_rlayout);
//				mFlAdHolder.removeAllViews();
//				mFlAdHolder.addView(view);
//			}

//			private void fillMyTargetAdLayout() {
//				NativePromoAd ad = (NativePromoAd) mCampaign.getNativead();
//				contentStreamAdView = NativeViewsFactory.getContentStreamView(ad, NativeMultemplateActivity.this);
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//				mFlAdHolder.addView(contentStreamAdView, params);
//				contentStreamAdView.loadImages();
//				mNativeHandler.registerView(contentStreamAdView, mCampaign);
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
				Log.e("", "onLoggingImpression");
			}

		});
		mNativeHandler.setTrackingListener(new NativeTrackingListener() {

			@Override
			public void onStartRedirection(Campaign campaign, String url) {

			}

			@Override
			public void onRedirectionFailed(Campaign campaign, String url) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinishRedirection(Campaign campaign, String url) {
			}

			@Override
			public void onDownloadStart(Campaign campaign) {
				// TODO Auto-generated method stub
				Log.e("pro", "start---");
			}

			@Override
			public void onDownloadFinish(Campaign campaign) {
				// TODO Auto-generated method stub
				Log.e("pro", "finish---");

			}

			@Override
			public void onDownloadProgress(int progress) {
				// TODO Auto-generated method stub
				Log.e("pro", "progress----" + progress);
			}

			@Override
			public boolean onInterceptDefaultLoadingDialog() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowLoading(Campaign campaign) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDismissLoading(Campaign campaign) {
				// TODO Auto-generated method stub

			}
		});

		mNativeHandler.load();

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
		if (mNativeHandler != null) {
			mNativeHandler.release();
		}
	}

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_native;
	}

	@Override
	public void initView() {

		mBtnPreloadNative = (Button) findViewById(R.id.btPreNative);
		mBtNative = (Button) findViewById(R.id.btNative);
		mTvTitle = (TextView) findViewById(R.id.mintegral_demo_tv_title);
		mFlAdHolder = (FrameLayout) findViewById(R.id.fl_adplaceholder);
		mSpinner = (Spinner) findViewById(R.id.mintegral_demo_native_sp);
		mIvDisplayArea = (ImageView) findViewById(R.id.mintegral_demo_iv_display_area);
	}

	@Override
	public void initData() {
		mTvTitle.setText("NATIVE_MUL");
		List<String> adSourceList = new ArrayList<String>();
//		adSourceList.add("FB");
		adSourceList.add("MTG");
//		adSourceList.add("AdMob");
//		adSourceList.add("MyTarget");
		ArrayAdapter<String> adSouceAdapter = new ArrayAdapter<String>(this, R.layout.mintegral_demo_native_sp_display,
				R.id.mintegral_demo_tv_sp_name, adSourceList);
		adSouceAdapter.setDropDownViewResource(R.layout.mintegral_demo_sp_dropdown_adsouce);
		mSpinner.setAdapter(adSouceAdapter);

	}

	@Override
	public void setListener() {
		mTvTitle.setOnClickListener(this);
		mBtNative.setOnClickListener(this);
		mBtnPreloadNative.setOnClickListener(this);
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

}
