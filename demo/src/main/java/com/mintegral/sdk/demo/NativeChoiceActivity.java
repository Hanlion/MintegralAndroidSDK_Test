package com.mintegral.sdk.demo;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mintegral.sdk.demo.adapter.CommonAdapter;
import com.mintegral.sdk.demo.adapter.ViewHolder;
import com.mintegral.sdk.demo.bean.AdStyleInfoBean;
import com.mintegral.sdk.demo.view.CommonTitleLayout;

import java.util.ArrayList;
import java.util.List;

public class NativeChoiceActivity extends BaseActivity {
	private static final int POSITION_NATIVE = 0;
	private static final int POSITION_NATIVE_VIDEO = 1;
	private static final int POSITION_GOTO_NATIVE_MUL = 2;
	private static final int POSITION_NATIVE_SIMPLE = 3;
	private GridView mGvNative;
	private CommonTitleLayout mTitleLayout;

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_native_choic;
	}

	@Override
	public void initView() {
		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
		mGvNative = (GridView) findViewById(R.id.mintegral_demo_gv_native_list);
	}

	@Override
	public void initData() {
		mTitleLayout.setTitleText("NativeList");
		List<AdStyleInfoBean> nativeSelectlist = new ArrayList<AdStyleInfoBean>();
		AdStyleInfoBean nativeStyle = new AdStyleInfoBean("Native", R.drawable.mintegral_demo_native, 0);
		AdStyleInfoBean nativeVideoStyle = new AdStyleInfoBean("Native_Video", R.drawable.mintegral_demo_native_video, 0);
		AdStyleInfoBean nativeMulStyle = new AdStyleInfoBean("Native_Mul", R.drawable.mintegral_demo_native_mul, 0);
		AdStyleInfoBean nativeSimpleStyle = new AdStyleInfoBean("Native_Simple", R.drawable.mintegral_demo_simple, 0);

		nativeSelectlist.add(nativeStyle);
		nativeSelectlist.add(nativeVideoStyle);
		nativeSelectlist.add(nativeMulStyle);
		nativeSelectlist.add(nativeSimpleStyle);
		CommonAdapter<AdStyleInfoBean> adapter = new CommonAdapter<AdStyleInfoBean>(nativeSelectlist,
				R.layout.mintegral_demo_item_natives, this) {

			@Override
			public void convert(ViewHolder helper, AdStyleInfoBean item) {
				helper.setImageResource(R.id.mintegral_demo_iv_name, item.getAdImageResId());
				helper.setText(R.id.mintegral_demo_tv_name, item.getAdStyleName());
			}
		};
		mGvNative.setAdapter(adapter);
	}

	@Override
	public void setListener() {
		mGvNative.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
				case POSITION_NATIVE:
					intent = new Intent(NativeChoiceActivity.this, NativeActivity.class);
					startActivity(intent);
					break;
				case POSITION_GOTO_NATIVE_MUL:
					intent = new Intent(NativeChoiceActivity.this, NativeMultemplateActivity.class);
					startActivity(intent);
					break;
				case POSITION_NATIVE_SIMPLE:
					intent = new Intent(NativeChoiceActivity.this, NativeSimpleActivity.class);
					startActivity(intent);
					break;
				case POSITION_NATIVE_VIDEO:
					intent = new Intent(NativeChoiceActivity.this, NativeVideoRecycleViewActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
	}
}
