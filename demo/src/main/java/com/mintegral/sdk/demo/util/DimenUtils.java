package com.mintegral.sdk.demo.util;

import android.content.Context;


public class DimenUtils {
	
	
	/**
	 * According to the resolution of the mobile phone dp turn px
	 */
	public static int dip2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * According to the resolution of the mobile phone px Turn dp
	 */
	public static int px2dip(Context context,float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
