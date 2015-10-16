package com.pm.system.entity;
/**
 * @author pengsong
 * @see 权限信息
 */
public class Permission {

	private String permissionId;//权限ID
	private String functionId;//功能ID
	private String functionName;//修改权限
	private String function;//浏览权限

	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Permission(String permissionId, String functionId,
			String functionName, String function) {
		super();
		this.permissionId = permissionId;
		this.functionId = functionId;
		this.functionName = functionName;
		this.function = function;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	

}
