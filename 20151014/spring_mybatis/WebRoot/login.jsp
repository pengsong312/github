<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    
    <title>登陆-素材库</title>

    <!-- Bootstrap 调试使用-->
    <!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="material/css/bootstrap.min.css">
   	<!-- font-awesome 矢量图标-->
    <link rel="stylesheet" href="material/css/font-awesome.min.css"/>

    <!-- user define -->
    <link rel="stylesheet" href="material/css/login.style.css"/>
	
    <!-- jQuery -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="material/js/jquery-1.11.2.min.js"></script>
    <script src="materialjs/bootstrap.min.js"></script>
    <script src="materialjs/main.js"></script>
    <!-- this page script-->
    <script type="text/javascript">
    
    	$(document).ready(function(){ 
				$(':input').each(function(){ 
				   if($(this).attr('type')=='text'){
					    $(this).val('');
					   }
					  });
				}); 
				
		$(window).keydown(function(e) {
			switch (e.keyCode) {
				case 13:
					$('#login').trigger('click');
					break;
				default:
					return true;
					break;
				}
		})
    
    
    	function checkForm(){
    		var user_name = $("#userName").val();
    		var pass_word = $("#passWord").val();
    		if(user_name==""){
    			document.getElementById("nullUsername").innerHTML="请输入用户名";
    			document.getElementById("nullUsername").style.display="";
    			return false;
    		}
    		if(pass_word==""){
    			 document.getElementById("nullPassword").innerHTML="请输入密码";
    			 document.getElementById("nullPassword").style.display="";
    			return false;
    		}
    		return true;
    	}
    	function sub(){
    		if(checkForm()){
    			document.loginForm.submit();
    		}
    	}
    	function delErrorUserName(){
    		var error = document.getElementById("nullUsername").value;
    		if(error!=''){
    			document.getElementById("nullUsername").innerHTML="";
    			document.getElementById("nullUsername").style.display="none";
    		}
    	}
    	function delErrorPassWord(){
    		var error = document.getElementById("nullPassword").value;
    		if(error!=''){
    			document.getElementById("nullPassword").innerHTML="";
    			document.getElementById("nullPassword").style.display="none";
    		}
    	}
    </script>
</head>
<body>
	<div id="loginFrame">
		<div class="container">
			<div class="top center-block"><img src="material/images/login-top.png"/></div>
			
			<form action="user/login.do" method="post" id="loginForm" name="loginForm">
				<div class="loginForm center-block">
					<div class="form-group ">
						<input type="text" class="form-control" name="userName" id="userName" value="" placeholder="用户名" onchange="delErrorUserName()"/>
						<div id="nullUsername" style="display:none;color: red;"></div>
						<div class="loginUser"></div>
					</div>
					<div class="form-group ">
						<input type="password" class="form-control" name="passWord" id="passWord" value="" placeholder="密码" onchange="delErrorPassWord()"/>
						<div id="nullPassword" style="display:none;color: red;"></div>
						<div class="msg" style="display:online;color: red;">${msg}</div>
						<div class="loginPassword" id="loginPassword" style="display:online;color: red;"></div>
					</div>
					
					<div class="form-group ">
						<button type="button" class="btn btn-primary btn-login" onclick="sub()" id="login">登录</button>
					</div>
				</div>
				
			</form>
		</div>
	</div>
</body>
</html>





