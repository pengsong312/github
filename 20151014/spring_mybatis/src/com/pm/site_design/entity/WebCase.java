package com.pm.site_design.entity;

import java.util.List;

public class WebCase {
	
	private String siteNumber;
	private String siteIntf;
	private String style;
	private String color;
	private String industry;
	private String designer;
	private String siteUrl;
	private String picture;
	private String describe;
	private int look;
	private int thumb;
	private List pictureList;//图片链接
	
	public WebCase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebCase(String siteNumber, String siteIntf, String style,
			String color, String industry, String designer, String siteUrl,
			String picture, String describe, int look, int thumb,
			List pictureList) {
		super();
		this.siteNumber = siteNumber;
		this.siteIntf = siteIntf;
		this.style = style;
		this.color = color;
		this.industry = industry;
		this.designer = designer;
		this.siteUrl = siteUrl;
		this.picture = picture;
		this.describe = describe;
		this.look = look;
		this.thumb = thumb;
		this.pictureList = pictureList;
	}

	public String getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}

	public String getSiteIntf() {
		return siteIntf;
	}

	public void setSiteIntf(String siteIntf) {
		this.siteIntf = siteIntf;
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

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
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
