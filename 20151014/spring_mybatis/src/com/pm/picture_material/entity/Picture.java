package com.pm.picture_material.entity;

public class Picture {
	
	private String pictureNumber; // 图片ID
	private String pictureIntf; // 图片ID
	private String note; // 备注
	private String uploadUser; // 上传者
	private String userName;
	private String classification; // 分类
	private String pictureUrl; // 图片
	private String picture; // 图片	
	private String copyrightInformation;// 版权信息
	private int download; // 查看次数
	private int thumb;// 点赞
	private int look;


	public Picture() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Picture(String pictureNumber, String pictureIntf, String note,
			String uploadUser, String userName, String classification,
			String pictureUrl, String picture, String copyrightInformation,
			int download, int thumb, int look) {
		super();
		this.pictureNumber = pictureNumber;
		this.pictureIntf = pictureIntf;
		this.note = note;
		this.uploadUser = uploadUser;
		this.userName = userName;
		this.classification = classification;
		this.pictureUrl = pictureUrl;
		this.picture = picture;
		this.copyrightInformation = copyrightInformation;
		this.download = download;
		this.thumb = thumb;
		this.look = look;
	}


	public String getPictureNumber() {
		return pictureNumber;
	}


	public void setPictureNumber(String pictureNumber) {
		this.pictureNumber = pictureNumber;
	}


	public String getPictureIntf() {
		return pictureIntf;
	}


	public void setPictureIntf(String pictureIntf) {
		this.pictureIntf = pictureIntf;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getUploadUser() {
		return uploadUser;
	}


	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getCopyrightInformation() {
		return copyrightInformation;
	}


	public void setCopyrightInformation(String copyrightInformation) {
		this.copyrightInformation = copyrightInformation;
	}


	public int getDownload() {
		return download;
	}


	public void setDownload(int download) {
		this.download = download;
	}


	public int getThumb() {
		return thumb;
	}


	public void setThumb(int thumb) {
		this.thumb = thumb;
	}


	public int getLook() {
		return look;
	}


	public void setLook(int look) {
		this.look = look;
	}

	
}
