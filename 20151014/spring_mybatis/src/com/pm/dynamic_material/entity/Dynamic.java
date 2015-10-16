package com.pm.dynamic_material.entity;

public class Dynamic {
	
	 private String  dynamicNumber ;  
	 private String  dynamicIntf ;         
	 private String  picture;     
	 private String  materialType ;
	 private String  dynamicUrl;   
	 private String  describle ;
	 private int look;
	 private int thumb;
	 public Dynamic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Dynamic(String dynamicNumber, String dynamicIntf, String picture,
			String materialType, String dynamicUrl, String describle, int look,
			int thumb) {
		super();
		this.dynamicNumber = dynamicNumber;
		this.dynamicIntf = dynamicIntf;
		this.picture = picture;
		this.materialType = materialType;
		this.dynamicUrl = dynamicUrl;
		this.describle = describle;
		this.look = look;
		this.thumb = thumb;
	}
	public String getDynamicNumber() {
		return dynamicNumber;
	}
	public void setDynamicNumber(String dynamicNumber) {
		this.dynamicNumber = dynamicNumber;
	}
	public String getDynamicIntf() {
		return dynamicIntf;
	}
	public void setDynamicIntf(String dynamicIntf) {
		this.dynamicIntf = dynamicIntf;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getDynamicUrl() {
		return dynamicUrl;
	}
	public void setDynamicUrl(String dynamicUrl) {
		this.dynamicUrl = dynamicUrl;
	}
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
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
	
}
