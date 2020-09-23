package com.mintegral.sdk.demo.bean;


public class AdStyleInfoBean {

	private String adStyleName;
	private int adImageResId;
	private int adIconResId;



	public AdStyleInfoBean(String adStyleName, int adImageResId, int adIconResId) {
		super();
		this.adStyleName = adStyleName;
		this.adImageResId = adImageResId;
		this.adIconResId = adIconResId;
	}

	public String getAdStyleName() {
		return adStyleName;
	}

	public void setAdStyleName(String adStyleName) {
		this.adStyleName = adStyleName;
	}

	public int getAdIconResId() {
		return adIconResId;
	}

	public void setAdIconResId(int adIconResId) {
		this.adIconResId = adIconResId;
	}

	public int getAdImageResId() {
		return adImageResId;
	}

	public void setAdImageResId(int adImageResId) {
		this.adImageResId = adImageResId;
	}

}
