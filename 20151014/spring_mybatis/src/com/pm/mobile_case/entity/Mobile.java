package com.pm.mobile_case.entity;

import java.util.List;

public class Mobile {

	private String mobileNumber;
	private String mobileIntf;
	private String style;
	private String color;
	private String industry;
	private String designer;
	private String mobileUrl;
	private String picture;
	private String describe;
	private int look;
	private int thumb;
	private List pictureList;

	public Mobile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mobile(String mobileNumber, String mobileIntf, String style,
			String color, String industry, String designer, String mobileUrl,
			String picture, String describe, int look, int thumb,
			List pictureList) {
		super();
		this.mobileNumber = mobileNumber;
		this.mobileIntf = mobileIntf;
		this.style = style;
		this.color = color;
		this.industry = industry;
		this.designer = designer;
		this.mobileUrl = mobileUrl;
		this.picture = picture;
		this.describe = describe;
		this.look = look;
		this.thumb = thumb;
		this.pictureList = pictureList;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileIntf() {
		return mobileIntf;
	}

	public void setMobileIntf(String mobileIntf) {
		this.mobileIntf = mobileIntf;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getMobileUrl() {
		return mobileUrl;
	}

	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getLook() {
		return look;
	}

	public void setLook(int look) {
		this.look = look;
	}

	public int getThumb() {
		return thumb;
	}

	public void setThumb(int thumb) {
		this.thumb = thumb;
	}

	public List getPictureList() {
		return pictureList;
	}

	public void setPictureList(List pictureList) {
		this.pictureList = pictureList;
	}

	
}
