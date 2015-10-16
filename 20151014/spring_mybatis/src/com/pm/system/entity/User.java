package com.pm.system.entity;

import java.util.List;

public class User {

	private String userId;
	private String userName;
	private String realName;
	private String passWord;
	private String photo;
	private String roleIds;
	private Role roles;
	private List<Permission> Permissions;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public User(String userId, String userName, String realName,
			String passWord, String photo, String roleIds, Role roles,
			List<Permission> permissions) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		this.passWord = passWord;
		this.photo = photo;
		this.roleIds = roleIds;
		this.roles = roles;
		Permissions = permissions;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public List<Permission> getPermissions() {
		return Permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		Permissions = permissions;
	}
	
}
