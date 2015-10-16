package com.pm.mobile_case.entity;

import java.util.List;

public class App {

	private String appNumber;
	private String appIntf;
	private String style;
	private String color;
	private String industry;
	private String designer;
	private String appUrl;
	private String picture;
	private String describe;
	private int look;
	private int thumb;
	private List pictureList;
	
	public App() {
		super();
		// TODO Auto-generated constructor stub
	}

	public App(String appNumber, String appIntf, String style, String color,
			String industry, String designer, String appUrl, String picture,
			String describe, int look, int thumb, List pictureList) {
		super();
		this.appNumber = appNumber;
		this.appIntf = appIntf;
		this.style = style;
		this.color = color;
		this.industry = industry;
		this.designer = designer;
		this.appUrl = appUrl;
		this.picture = picture;
		this.describe = describe;
		this.look = look;
		this.thumb = thumb;
		this.pictureList = pictureList;
	}

	public String getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(String appNumber) {
		this.appNumber = appNumber;
	}

	public String getAppIntf() {
		return appIntf;
	}

	public void setAppIntf(String appIntf) {
		this.appIntf = appIntf;
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

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
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
