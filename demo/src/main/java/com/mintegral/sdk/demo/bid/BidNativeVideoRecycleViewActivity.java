package com.mintegral.sdk.demo.bid;

import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.base.entity.CampaignEx;
import com.mintegral.msdk.mtgbid.out.BidListennning;
import com.mintegral.msdk.mtgbid.out.BidManager;
import com.mintegral.msdk.mtgbid.out.BidResponsed;
import com.mintegral.msdk.nativex.view.MTGMediaView;
import com.mintegral.msdk.out.Campaign;
import com.mintegral.msdk.out.Frame;
import com.mintegral.msdk.out.MtgBidNativeHandler;
import com.mintegral.msdk.out.MtgNativeHandler;
import com.mintegral.msdk.out.NativeListener;
import com.mintegral.msdk.out.OnMTGMediaViewListenerPlus;
import com.mintegral.msdk.widget.MTGAdChoice;
import com.mintegral.sdk.demo.BaseActivity;
import com.mintegral.sdk.demo.MainApplication;
import com.mintegral.sdk.demo.R;
import com.mintegral.sdk.demo.util.ImageLoadTask;
import com.mintegral.sdk.demo.view.StarLevelLayoutView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mintegral.msdk.MIntegralConstans.NATIVE_VIDEO_SUPPORT;


public class BidNativeVideoRecycleViewActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = BidNativeVideoRecycleViewActivity.class.getName();

    private Button mBtBidNative;
    private Button mBtShowVideo;
    private TextView mTvTitle;
    private ImageView mIvDisplayBg;
    private RecyclerView mRecycleView;
    private ProgressBar mProgressbar;
    private Spinner mSpinner;
    private String mCurrentUnitId = "146868";
    public static final int AD_NUM =30;
    private MtgBidNativeHandler mNativeHandle;
    private List<Campaign> mAllDatas = new ArrayList<>();
    private boolean videoSupport = true;//support native video
    private Set<MTGMediaView> mediaViewSet = new HashSet<MTGMediaView>();

    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSPARENT);
    }

    private MTGMediaView mediaView;

    @Override
    public int getResLayoutId() {
        return R.layout.mintegral_demo_atv_nativevideo_recycleview;
    }

    @Override
    public void initView() {
        mBtBidNative = (Button) findViewById(R.id.mintegral_native_bid);
        mBtBidNative.setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.mintegral_native_load)).setVisibility(View.GONE);
        mBtShowVideo = (Button) findViewById(R.id.mintegral_native_showvideo);
        mTvTitle = (TextView) findViewById(R.id.mintegral_demo_tv_title);
        mSpinner = (Spinner) findViewById(R.id.mintegral_demo_native_sp);
        mSpinner.setVisibility(View.GONE);
        mIvDisplayBg = (ImageView) findViewById(R.id.mintegral_display_bg);
        mProgressbar = (ProgressBar) findViewById(R.id.mintegral_progressBar);
        mRecycleView = (RecyclerView) findViewById(R.id.mintegral_recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void initData() {
        MainApplication.reportUser(33.33,-22.22);
        mTvTitle.setText("Bid_Native_Video_RecycleView");
    }

    @Override
    public void setListener() {
        mTvTitle.setOnClickListener(this);
        mBtShowVideo.setOnClickListener(this);
        mBtBidNative.setOnClickListener(this);
    }

    private List<Object> getRecycleViewDatas(List<Campaign> campaigns) {

        ArrayList<Object> datas = new ArrayList<>();

        if (campaigns == null) {
            return datas;
        }
        int size = campaigns.size();
        int total = size * 3 + 3;
        for (int i = 2; i <= total; i++) {

            if (i % 3 == 0) {
                Campaign campaign = null;
                if (i / 3 <= size) {
                    campaign = campaigns.get(i / 3 - 1);
                }

                if (campaign != null && campaign.getType() == MIntegralConstans.AD_TYPE_MTG) {
                    datas.add(campaign);
                }
            } else {
                datas.add(i - 1);
            }
        }
        return datas;
    }

    private void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void toastLoadSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "loadSuccess", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void toastLoadFailed(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "loadFailed:"+msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressbar.setVisibility(View.GONE);
            }
        });

    }

    public void loadNative() {
        showLoading();
        //Map<String, Object> properties = MtgNativeHandler.getNativeProperties(mCurrentUnitId);
        Map<String, Object> properties = MtgNativeHandler.getNativeProperties("138780", mCurrentUnitId);
        properties.put(MIntegralConstans.NATIVE_VIDEO_WIDTH, 720);
        properties.put(MIntegralConstans.NATIVE_VIDEO_HEIGHT, 480);
        properties.put(NATIVE_VIDEO_SUPPORT, videoSupport);
        properties.put(MIntegralConstans.PROPERTIES_AD_NUM, AD_NUM);
        if(mNativeHandle == null){
            mNativeHandle = new MtgBidNativeHandler(properties, this);
            mNativeHandle.setAdListener(new NativeListener.NativeAdListener() {

                @Override
                public void onAdLoaded(List<Campaign> campaigns, int template) {
                    Log.e(TAG, "onAdLoaded");
                    hideLoading();
                    toastLoadSuccess();
                    if (campaigns != null && campaigns.size() > 0) {
                        mAllDatas = campaigns;
                    }

                    //show campaign
                    if (mAllDatas == null || mAllDatas.size() == 0) {
                        Toast.makeText(getApplicationContext(), "no load data to show", Toast.LENGTH_LONG).show();
                        return;
                    }
//                Campaign campaign = mAllDatas.get(0);
                    List<Object> datas = getRecycleViewDatas(mAllDatas);
                    destoryMediaView();

                    mIvDisplayBg.setVisibility(View.GONE);
                    mRecycleView.setVisibility(View.VISIBLE);
                    MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(datas);
                    mRecycleView.setAdapter(adapter);
                }

                @Override
                public void onAdLoadError(String message) {
                    Log.e(TAG, "onAdLoadError" + message);
                    hideLoading();
                    toastLoadFailed(message);
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
            mNativeHandle.setTrackingListener(new NativeListener.NativeTrackingListener() {

                @Override
                public void onStartRedirection(Campaign campaign, String url) {
                }

                @Override
                public void onRedirectionFailed(Campaign campaign, String url) {
                }

                @Override
                public void onFinishRedirection(Campaign campaign, String url) {
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
        }
        mNativeHandle.bidLoad(token);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mintegral_native_bid:
                bidNative();
                break;
            case R.id.mintegral_native_showvideo:
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

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {

        public static final int VIEW_TYPE_VIDEO = 0;
        public static final int VIEW_TYPE_TXT = 1;

        private LayoutInflater mInflater;
        List<Object> mAllData = new ArrayList<>();

        public MyRecyclerViewAdapter(List<Object> datas) {

            this.mInflater = LayoutInflater.from(getApplicationContext());
            if (datas != null) {
                mAllData.addAll(datas);
            }
        }

        @Override
        public int getItemViewType(int position) {

            Object object = mAllData.get(position);
            if (object instanceof CampaignEx) {
                return VIEW_TYPE_VIDEO;
            } else if (object instanceof Integer) {
                return VIEW_TYPE_TXT;
            }
            return VIEW_TYPE_TXT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_VIDEO) {

                View inflate = mInflater.inflate(R.layout.mintegral_nativevideo_recycleview_mediaview_item, parent, false);
                VideoViewHolder videoViewHolder = new VideoViewHolder(inflate);
                return videoViewHolder;

            } else {

                return new TxtHolder(mInflater.inflate(R.layout.mintegral_nativevideo_recycleview_item_txt, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Object object = mAllData.get(position);
            if (holder.getItemViewType() == VIEW_TYPE_VIDEO) {
                if (object instanceof CampaignEx) {
                    Campaign campaign = (Campaign) object;
                    VideoViewHolder videoHolder = (VideoViewHolder) holder;

                    if (videoHolder.mMediaview != null) {
                        videoHolder.mMediaview.setOnMediaViewListener(new OnMTGMediaViewListenerPlus() {
                            @Override
                            public void onEnterFullscreen() {
                                Log.e(TAG, "onEnterFullscreen");
                            }

                            @Override
                            public void onExitFullscreen() {
                                Log.e(TAG, "onExitFullscreen");
                            }

                            @Override
                            public void onStartRedirection(Campaign campaign, String url) {
                                Log.e(TAG, "onStartRedirection");
                            }

                            @Override
                            public void onFinishRedirection(Campaign campaign, String url) {
                                Log.e(TAG, "onFinishRedirection");
                            }

                            @Override
                            public void onRedirectionFailed(Campaign campaign, String url) {
                                Log.e(TAG, "onRedirectionFailed");
                            }

                            @Override
                            public void onVideoAdClicked(Campaign campaign) {
                                Log.e(TAG, "onVideoAdClicked id:" + campaign.getId());
                            }

                            @Override
                            public void onVideoStart() {
                                Log.e(TAG, "onVideoStart");
                            }

                            @Override
                            public void onVideoComplete() {
                                Log.e(TAG, "onVideoComplete");
                            }
                        });
                    }
                    videoHolder.bindView(campaign);
                    if (videoHolder.mMediaview != null) {
                        videoHolder.mMediaview.setIsAllowFullScreen(true);
                    }
                }
            } else {
                TxtHolder txtHolder = (TxtHolder) holder;
                if (object instanceof Integer) {
                    int txt = (int) object;
                    txtHolder.setText(txt + "");
                }
            }
        }

        @Override
        public int getItemCount() {
            return mAllData == null ? 0 : mAllData.size();
        }

        private class VideoViewHolder extends RecyclerView.ViewHolder {

            private MTGMediaView mMediaview;
            private ImageView mIvIcon;
            private TextView mTvAppName;
            private TextView mTvAppDesc;
            private TextView mTvCta;
            private MTGAdChoice adChoice;
            private StarLevelLayoutView mStarLayout;

            public VideoViewHolder(View itemView) {
                super(itemView);
                mMediaview = (MTGMediaView) itemView.findViewById(R.id.mintegral_mediaview);
                if(mediaViewSet != null){
                    //9.10.0 add
                    mMediaview.setProgressVisibility(true);
                    mMediaview.setSoundIndicatorVisibility(true);
                    mMediaview.setVideoSoundOnOff(false);
                    mediaViewSet.add(mMediaview);
                }
                mIvIcon = (ImageView) itemView.findViewById(R.id.mintegral_feeds_icon);
                mTvAppName = (TextView) itemView.findViewById(R.id.mintegral_feeds_app_name);
                mTvCta = (TextView) itemView.findViewById(R.id.mintegral_feeds_tv_cta);
                mTvAppDesc = (TextView) itemView.findViewById(R.id.mintegral_feeds_app_desc);
                mStarLayout = (StarLevelLayoutView) itemView.findViewById(R.id.mintegral_feeds_star);
                adChoice = itemView.findViewById(R.id.mintegral_mediaview_adchoice);
            }

            public void bindView(Campaign campaign) {
                mMediaview.setNativeAd(campaign);
                if (!TextUtils.isEmpty(campaign.getIconUrl())) {
                    new ImageLoadTask(campaign.getIconUrl()) {

                        @Override
                        public void onRecived(Drawable result) {
                            mIvIcon.setImageDrawable(result);
                        }
                    }.execute();
                }
                mTvAppName.setText(campaign.getAppName() + "");
                mTvAppDesc.setText(campaign.getAppDesc() + "");
                mTvCta.setText(campaign.getAdCall());
                int rating = (int) campaign.getRating();
                mStarLayout.setRating(rating);
                adChoice.setCampaign(campaign);
                mNativeHandle.registerView(itemView, campaign);
            }
        }

        private class TxtHolder extends RecyclerView.ViewHolder {

            private TextView mTvDesc;

            public TxtHolder(View itemView) {
                super(itemView);
                mTvDesc = (TextView) itemView.findViewById(R.id.mintegral_tv_recycleview_desc);
            }

            public void setText(String txt) {
                mTvDesc.setText(txt);
            }
        }
    }

    private void destoryMediaView(){
        if(mediaViewSet != null && mediaViewSet.size() > 0){
            for(MTGMediaView mediaview : mediaViewSet){
                mediaview.destory();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNativeHandle != null) {
            mNativeHandle.bidRelease();
        }
        destoryMediaView();
    }

}
