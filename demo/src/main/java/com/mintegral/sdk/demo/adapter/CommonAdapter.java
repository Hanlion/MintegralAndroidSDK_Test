package com.mintegral.sdk.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	private List<T> mDatas;

	private int mResLayoutId;

	private Context mContext;

	public CommonAdapter(List<T> mDatas, int resLayoutId, Context mContext) {
		super();
		this.mDatas = mDatas;
		this.mResLayoutId = resLayoutId;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;

	}

	@Override
	public Object getItem(int position) {
		if (mDatas != null) {
			return mDatas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, position, mResLayoutId, convertView, parent);
		convert(viewHolder, mDatas.get(position));
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder helper, T item);

}
