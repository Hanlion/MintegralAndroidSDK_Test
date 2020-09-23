package com.mintegral.sdk.demo.bid;

import java.util.List;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.out.Campaign;
import com.mintegral.msdk.out.Frame;
import com.mintegral.msdk.out.MtgBidNativeHandler;
import com.mintegral.msdk.out.MtgNativeHandler;
import com.mintegral.msdk.out.NativeListener.NativeAdListener;
import com.mintegral.msdk.out.NativeListener.NativeTrackingListener;
import com.mintegral.msdk.widget.MTGAdChoice;
import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.R;
import com.mintegral.sdk.demo.util.ImageLoadTask;

public class BidNativeActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = BidNativeActivity.class.getName();
    private Button mBtNative;
    private Button mBtBidNative;
    private Spinner mSpinner;

    public int AD_NUM = 1;
    private String mCurrentUnitId = "146868";

    private MtgBidNativeHandler mNativeHandle;
    private Campaign mCampaign;
    private FrameLayout mFlAdHolder;
    private TextView mTvTitle;
    private ImageView mIvDisplayArea;

    private String token = "";

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_atv_native;
    }

    @Override
    public void initView() {
        mBtBidNative = (Button) findViewById(R.id.bt_bid_native);
        ((Button) findViewById(R.id.btPreNative)).setVisibility(View.GONE);
        mBtNative = (Button) findViewById(R.id.btNative);
        mTvTitle = (TextView) findViewById(R.id.mintegral_demo_tv_title);
        mFlAdHolder = (FrameLayout) findViewById(R.id.fl_adplaceholder);
        mSpinner = (Spinner) findViewById(R.id.mintegral_demo_native_sp);
        mSpinner.setVisibility(View.GONE);
        mIvDisplayArea = (ImageView) findViewById(R.id.mintegral_demo_iv_display_area);
        mBtBidNative.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        mTvTitle.setText("Bid_Native");
    }

    @Override
    public void setListener() {
        mTvTitle.setOnClickListener(this);
        mBtBidNative.setOnClickListener(this);
        mBtNative.setOnClickListener(this);
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


    public void loadNative() {
        //Map<String, Object> properties = MtgNativeHandler.getNativeProperties(mCurrentUnitId);
        Map<String, Object> properties = MtgNativeHandler.getNativeProperties("138780", mCurrentUnitId);
        properties.put(MIntegralConstans.PROPERTIES_AD_NUM, AD_NUM);
        mNativeHandle = new MtgBidNativeHandler(properties, this);
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
                    fillMTGAdLayout();
                }
            }

            private void fillMTGAdLayout() {
                final View view = LayoutInflater.from(BidNativeActivity.this)
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
            }

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

        mNativeHandle.bidLoad(token);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_bid_native:
                bidNative();
                break;
            case R.id.btNative:
                loadNative();
                break;
            case R.id.mintegral_demo_tv_title:
                finish();
                break;
        }
    }

    private void bidNative(){
        //BidManager manager = new BidManager(mCurrentUnitId);
        BidManager manager = new BidManager("138780", mCurrentUnitId);
        manager.setBidListener(new BidListennning() {
            @Override
            public void onFailed(String msg) {
                Toast.makeText(getApplicationContext(),"bid failed:"+msg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccessed(BidResponsed bidResponsed) {
                Toast.makeText(getApplicationContext(),"bid successed:"+bidResponsed.getPrice(),Toast.LENGTH_LONG).show();
                token = bidResponsed.getBidToken();
            }
        });
        manager.bid();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeHandle != null) {
            mNativeHandle.bidRelease();
        }
    }

}