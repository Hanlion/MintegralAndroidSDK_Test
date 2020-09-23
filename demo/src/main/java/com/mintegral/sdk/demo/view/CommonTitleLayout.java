package com.mintegral.sdk.demo.view;

import com.mintegral.sdk.demo.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonTitleLayout extends LinearLayout {
	private View view;
	private TextView mTvTitle;

	@SuppressLint("NewApi")
	public CommonTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayout();
	}

	public CommonTitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
	}

	public CommonTitleLayout(Context context) {
		super(context);
		initLayout();
	}

	private void initLayout() {
		view = View.inflate(getContext(), R.layout.mintegral_demo_common_title_layout, this);
		mTvTitle = (TextView) view.findViewById(R.id.mintegral_demo_tv_title);
		mTvTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (getContext() instanceof Activity) {
						((Activity) getContext()).finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public void setTitleText(String titleName) {
		if (!TextUtils.isEmpty(titleName)) {
			mTvTitle.setText(titleName);
		}

	}
}
