package com.pm.system.entity;

public class Role {
	
	private String roleId;
	private String roleName;
	private String permissionId;
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String roleId, String roleName, String permissionId) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.permissionId = permissionId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
}
