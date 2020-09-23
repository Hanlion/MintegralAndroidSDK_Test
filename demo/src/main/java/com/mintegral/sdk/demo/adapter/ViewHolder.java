package com.mintegral.sdk.demo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private SparseArray<View> mViews;

	private View mConvertView;

	private int mPosition;

	private ViewHolder(Context context, int position, int layoutId, View convertView, ViewGroup parent) {
		this.mViews = new SparseArray<View>();
		this.mPosition = position;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	public static ViewHolder getViewHolder(Context context, int position, int layoutId, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			return new ViewHolder(context, position, layoutId, convertView, parent);
		}
		return (ViewHolder) convertView.getTag();
	}

	public <T extends View> T getView(int viewId) {
		try {
			View view = mViews.get(viewId);
			if (view == null) {
				view = mConvertView.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return (T) view;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

    /** 
     * TextView set text
     *  
     * @param viewId 
     * @param text 
     * @return 
     */  
    public ViewHolder setText(int viewId, String text)  
    {  
        TextView view = getView(viewId);  
        view.setText(text);  
        return this;  
    }  
  
    /** 
     * ImageView set drawable
     *  
     * @param viewId 
     * @param drawableId 
     * @return 
     */  
    public ViewHolder setImageResource(int viewId, int drawableId)  
    {  
        ImageView view = getView(viewId);  
        view.setImageResource(drawableId);  
 
        return this;  
    }  
  

	public View getConvertView() {
		return mConvertView;
	}

	public int getmPosition() {
		return mPosition;
	}

}
