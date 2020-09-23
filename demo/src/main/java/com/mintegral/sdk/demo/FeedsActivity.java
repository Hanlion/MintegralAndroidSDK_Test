//package com.mintegral.sdk.demo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.mintegral.msdk.MIntegralConstans;
//import com.mintegral.msdk.out.MTGVideoFeedsHandler;
//import com.mintegral.msdk.out.VideoFeedsListener;
//import com.mintegral.msdk.videocommon.download.NetStateOnReceive;
//import com.mintegral.msdk.videofeeds.vfplayer.VideoFeedsAdView;
//import com.mintegral.sdk.demo.util.DimenUtils;
//import com.mintegral.sdk.demo.view.CommonTitleLayout;
//
//import android.annotation.SuppressLint;
//import android.content.IntentFilter;
//import android.graphics.PixelFormat;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class FeedsActivity extends BaseActivity implements OnClickListener {
//
//	public static final String TAG = "FeedsActivity";
//	private Button btn_load;
//	private MTGVideoFeedsHandler mFeedsHandler;
//	private ListView listview;
//	private NetStateOnReceive netStateOnReceive;
//	private VideoFeedsAdView mAdView;
//	private AdViewAdapter mAdapter;
//	private CommonTitleLayout mTitleLayout;
//	private ImageView mIvDisaplyArea;
//	private String mFeedsVideoUnitId ="146876";
//	private int mAdNum = 5;
//
//	@Override
//	@SuppressLint("ResourceAsColor")
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
//	}
//
//	@Override
//	public int getResLayoutId() {
//		return R.layout.mintegral_demo_atv_feeds;
//	}
//
//	@Override
//	public void initView() {
//		btn_load = (Button) findViewById(R.id.feeds_load);
//		listview = (ListView) findViewById(R.id.listview);
//		mIvDisaplyArea = (ImageView) findViewById(R.id.mintegral_display_area);
//		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
//
//	}
//
//	@Override
//	public void initData() {
//		mTitleLayout.setTitleText("FeedsVideo");
//		initHandler();
//		initReceiver();
//
//	}
//
//	@Override
//	public void setListener() {
//		btn_load.setOnClickListener(this);
//	}
//
//	private void initHandler() {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put(MIntegralConstans.PROPERTIES_UNIT_ID, mFeedsVideoUnitId);
//		hashMap.put(MIntegralConstans.PLACEMENT_ID, "138788");
//		hashMap.put(MIntegralConstans.PROPERTIES_AD_NUM, mAdNum);
//		mFeedsHandler = new MTGVideoFeedsHandler(getApplicationContext(), hashMap);
//		mFeedsHandler.setVideoFeedsListener(new VideoFeedsListener() {
//
//			@Override
//			public void onVideoLoadSuccess() {
//				mIvDisaplyArea.setVisibility(View.GONE);
//				Log.e(TAG, "feeds  load success");
//
//				if (mAdView != null && mAdapter != null) {
//					Log.i(TAG, "adView ready create No more to create");
//					return;
//				}
//
//				if (mFeedsHandler != null) {
//					mAdView = mFeedsHandler.show();
//					if (mAdView != null) {
//						List<Object> datas = getDatas(mAdView);
//						mAdapter = new AdViewAdapter(datas);
//						listview.setAdapter(mAdapter);
//						listview.setVisibility(View.VISIBLE);
//					}
//				}
//			}
//
//			@Override
//			public void onVideoLoadFail(String errorStr) {
//				Log.e(TAG, "onVideoLoadFail:" + errorStr);
//
//			}
//
//			@Override
//			public void onShowFail(String errorMsg) {
//				Log.e(TAG, "onShowFail:" + errorMsg);
//			}
//
//			@Override
//			public void onAdClicked() {
//				Log.e(TAG, "onAdClicked");
//			}
//
//			@Override
//			public void onAdShowSuccess(int cacheNum) {
//				Log.e(TAG, "onAdShowSuccess cacheNum:" + cacheNum);
//			}
//
//		});
//	}
//
//	private List<Object> getDatas(VideoFeedsAdView adView) {
//		List<Object> datas = new ArrayList<Object>();
//
//		datas.add("txt");
//		datas.add("txt");
//		if (adView != null) {
//			datas.add(adView);
//		}
//		datas.add("txt");
//		datas.add("txt");
//		datas.add("txt");
//		datas.add("txt");
//		datas.add("txt");
//		return datas;
//	}
//
//	public class AdViewAdapter extends BaseAdapter {
//
//		public static final int VIEW_TYPE_ADVIEW = 0;
//		public static final int VIEW_TYPE_TXT = 1;
//
//		public static final int VIEW_TYPE_COUNT = 2;
//
//		List<Object> mAllDatas = new ArrayList<Object>();
//		private LayoutInflater mInflater;
//
//		public AdViewAdapter(List<Object> datas) {
//			this.mInflater = LayoutInflater.from(getApplicationContext());
//			if (datas != null && datas.size() > 0) {
//				mAllDatas.addAll(datas);
//			}
//		}
//
//		public void updateDatasAndNotify(List<Object> datas) {
//
//			if (datas != null && datas.size() > 0) {
//				mAllDatas.clear();
//				mAllDatas.addAll(datas);
//				notifyDataSetChanged();
//			}
//		}
//
//		@Override
//		public int getCount() {
//			return mAllDatas == null ? 0 : mAllDatas.size();
//		}
//
//		@Override
//		public Object getItem(int positon) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int positon) {
//			return 0;
//		}
//
//		@Override
//		public int getItemViewType(int position) {
//
//			Object object = mAllDatas.get(position);
//			if (object instanceof VideoFeedsAdView) {
//				return VIEW_TYPE_ADVIEW;
//			} else if (object instanceof String) {
//				return VIEW_TYPE_TXT;
//			}
//			return VIEW_TYPE_TXT;
//		}
//
//		@Override
//		public int getViewTypeCount() {
//			return VIEW_TYPE_COUNT;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//			int viewType = getItemViewType(position);
//			VideoViewHolder videoViewHolder = null;
//			TxtHolder txtHolder = null;
//			if (convertView == null) {
//				if (viewType == VIEW_TYPE_ADVIEW) {
//
//					convertView = mInflater.inflate(R.layout.mintegral_demo_lv_adview_video, null);
//					videoViewHolder = new VideoViewHolder();
//					videoViewHolder.mPlaycontainer = (LinearLayout) convertView.findViewById(R.id.ll_root);
//
//					Object object = mAllDatas.get(position);
//					if (object instanceof VideoFeedsAdView) {
//						VideoFeedsAdView adView = (VideoFeedsAdView) object;
//						videoViewHolder.mPlaycontainer.addView(adView);
//					}
//					convertView.setTag(videoViewHolder);
//				} else {
//
//					convertView = mInflater.inflate(R.layout.mintegral_demo_lv_adview_txt, null);
//					txtHolder = new TxtHolder();
//					txtHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
//
//					convertView.setTag(txtHolder);
//				}
//			} else {
//				if (viewType == VIEW_TYPE_ADVIEW) {
//					videoViewHolder = (VideoViewHolder) convertView.getTag();
//				} else {
//					txtHolder = (TxtHolder) convertView.getTag();
//				}
//			}
//
//			if (viewType == VIEW_TYPE_TXT) {
//				Object object = mAllDatas.get(position);
//				if (object instanceof String) {
//
//					String txt = (String) object;
//					txtHolder.tv_desc.setText(txt);
//					LinearLayout.LayoutParams layoutParams = (LayoutParams) txtHolder.tv_desc.getLayoutParams();
//					layoutParams.width = -1;
//					layoutParams.height = DimenUtils.dip2px(getApplicationContext(), 200);
//					layoutParams.gravity = Gravity.CENTER;
//					txtHolder.tv_desc.setLayoutParams(layoutParams);
//				}
//			}
//
//			return convertView;
//		}
//	}
//
//	public class VideoViewHolder {
//		private LinearLayout mPlaycontainer;
//	}
//
//	public class TxtHolder {
//		private TextView tv_desc;
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		try {
//			if (netStateOnReceive != null) {
//				unregisterReceiver(netStateOnReceive);
//			}
//			netStateOnReceive = null;
//
//			if (mAdView != null) {
//				mAdView.release();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.feeds_load:
//
//			if (mFeedsHandler != null) {
//				mFeedsHandler.load();
//			}
//			break;
//		}
//	}
//
//	private void initReceiver() {
//		netStateOnReceive = new NetStateOnReceive();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//		registerReceiver(netStateOnReceive, filter);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		if (mAdView != null) {
//			mAdView.pause();
//		}
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		if (mAdView != null) {
//			mAdView.resume();
//		}
//	}
//
//}
