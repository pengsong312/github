package com.pm.template_case.entity;

import java.util.List;

public class Template {

	private String templateNumber;
	private String templateIntf;
	private String style;
	private String color;
	private String industry;
	private String designer;
	private String templateUrl;
	private String describe;
	private String picture;
	private int look;
	private int thumb;
	private List pictureList;
	
	public Template() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Template(String templateNumber, String templateIntf, String style,
			String color, String industry, String designer, String templateUrl,
			String describe, String picture, int look, int thumb,
			List pictureList) {
		super();
		this.templateNumber = templateNumber;
		this.templateIntf = templateIntf;
		this.style = style;
		this.color = color;
		this.industry = industry;
		this.designer = designer;
		this.templateUrl = templateUrl;
		this.describe = describe;
		this.picture = picture;
		this.look = look;
		this.thumb = thumb;
		this.pictureList = pictureList;
	}

	public String getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}

	public String getTemplateIntf() {
		return templateIntf;
	}

	public void setTemplateIntf(String templateIntf) {
		this.templateIntf = templateIntf;
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

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
