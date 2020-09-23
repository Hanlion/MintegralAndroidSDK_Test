package com.mintegral.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.out.Campaign;
import com.mintegral.msdk.out.Frame;
import com.mintegral.msdk.out.MIntegralSDKFactory;
import com.mintegral.msdk.out.MtgNativeHandler;
import com.mintegral.msdk.out.NativeListener.NativeAdListener;
import com.mintegral.msdk.out.NativeListener.Template;
import com.mintegral.sdk.demo.util.ImageLoadTask;
import com.mintegral.sdk.demo.view.StarLevelLayoutView;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * native displayfeedsImage
 *
 */
public class FeedsImageActivity extends BaseActivity {
	private static final String TAG = FeedsImageActivity.class.getName();

	private static final String UNIT_ID = "146868";
	private LinearLayout mLl_Root;
	private ImageView mIvIcon;
	private ImageView mIvImage;
	private TextView mTvAppName;
	private TextView mTvAppDesc;
	private TextView mTvCta;
	private StarLevelLayoutView mStarLayout;
	private MtgNativeHandler nativeHandle;
	private ProgressBar mProgressBar;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_native_feeds_image;
	}

	@Override
	public void initView() {
		mIvIcon = (ImageView) findViewById(R.id.mintegral_feeds_icon);
		mIvImage = (ImageView) findViewById(R.id.mintegral_feeds_image);
		mTvAppName = (TextView) findViewById(R.id.mintegral_feeds_app_name);
		mTvCta = (TextView) findViewById(R.id.mintegral_feeds_tv_cta);
		mTvAppDesc = (TextView) findViewById(R.id.mintegral_feeds_app_desc);
		mLl_Root = (LinearLayout) findViewById(R.id.mintegral_feeds_ll_root);
		mStarLayout = (StarLevelLayoutView) findViewById(R.id.mintegral_feeds_star);
		mProgressBar = (ProgressBar) findViewById(R.id.mintegral_feeds_progress);
	}

	@Override
	public void initData() {
		showLoadding();
		loadNative();
	}

	@Override
	public void setListener() {

	}

	private void showLoadding() {
		mProgressBar.setVisibility(View.VISIBLE);
		mLl_Root.setVisibility(View.GONE);
	}

	private void hideLoadding() {
		mProgressBar.setVisibility(View.GONE);
		mLl_Root.setVisibility(View.VISIBLE);
	}

	public void preloadNative() {

		MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
		Map<String, Object> preloadMap = new HashMap<String, Object>();
		preloadMap.put(MIntegralConstans.PROPERTIES_LAYOUT_TYPE, MIntegralConstans.LAYOUT_NATIVE);
		preloadMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, UNIT_ID);
		preloadMap.put(MIntegralConstans.PLACEMENT_ID, "138780");

		List<Template> list = new ArrayList<Template>();
		list.add(new Template(MIntegralConstans.TEMPLATE_BIG_IMG, 1));
		preloadMap.put(MIntegralConstans.NATIVE_INFO, MtgNativeHandler.getTemplateString(list));
		sdk.preload(preloadMap);

	}

	public void loadNative() {
		//Map<String, Object> properties = MtgNativeHandler.getNativeProperties(UNIT_ID);
		Map<String, Object> properties = MtgNativeHandler.getNativeProperties("138780", UNIT_ID);
		nativeHandle = new MtgNativeHandler(properties, this);
		nativeHandle.addTemplate(new Template(MIntegralConstans.TEMPLATE_BIG_IMG, 1));
		nativeHandle.setAdListener(new NativeAdListener() {

			@Override
			public void onAdLoaded(List<Campaign> campaigns, int template) {
				hideLoadding();
				fillFeedsImageLayout(campaigns);
				preloadNative();
			}

			@Override
			public void onAdLoadError(String message) {
				Log.e(TAG, "onAdLoadError:" + message);
			}

			@Override
			public void onAdFramesLoaded(List<Frame> list) {

			}

			@Override
			public void onLoggingImpression(int adsourceType) {

			}

			@Override
			public void onAdClick(Campaign campaign) {
				Log.e(TAG, "onAdClick");
			}
		});
		nativeHandle.load();
	}

	protected void fillFeedsImageLayout(List<Campaign> campaigns) {
		if (campaigns != null && campaigns.size() > 0) {
			Campaign campaign = campaigns.get(0);
			if (!TextUtils.isEmpty(campaign.getIconUrl())) {
				new ImageLoadTask(campaign.getIconUrl()) {

					@Override
					public void onRecived(Drawable result) {
						mIvIcon.setImageDrawable(result);
					}
				}.execute();
			}
			if (!TextUtils.isEmpty(campaign.getImageUrl())) {
				new ImageLoadTask(campaign.getImageUrl()) {

					@Override
					public void onRecived(Drawable result) {
						mIvImage.setImageDrawable(result);
					}
				}.execute();
			}
			mTvAppName.setText(campaign.getAppName() + "");
			mTvAppDesc.setText(campaign.getAppDesc() + "");
			mTvCta.setText(campaign.getAdCall());
			int rating = (int) campaign.getRating();
			mStarLayout.setRating(rating);
			nativeHandle.registerView(mLl_Root, campaign);
		}
	}

}
