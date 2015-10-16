<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.pm.system.entity.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<title>个人中心</title>


		<!-- Bootstrap 调试使用-->
		<!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="material/css/bootstrap.min.css">
		<!-- font-awesome 矢量图标-->
		<link rel="stylesheet" href="material/css/font-awesome.min.css" />

		<!-- user define -->
		<link rel="stylesheet" href="material/css/main.css" />
		<link rel="stylesheet" href="material/css/index.style.css" />
		<link rel="stylesheet" href="material/css/pic.style.css" />


		<!-- jQuery -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="material/js/jquery-1.11.2.min.js"></script>
		<script src="material/js/bootstrap.min.js"></script>
		<script src="material/js/masonry.pkgd.min.js"></script>
		<script src="material/js/imagesloaded.pkgd.min.js"></script>

		<script src="material/js/main.js"></script>
	<!-- this page script-->
	<script type="text/javascript">
		
	function checkAdmin(){
		var  roleName = $("#roleName").val();
		var  userId = $("#userId").val();	
		if(roleName!='管理员'){
			alert("对不起，您无权操作！");
			return false;
		}
		else{
			window.location="user/findAllUser2.do";
			return true;
		}
	}
	</script>


</head>
<body>
	<input type="hidden" id="roleName" value="${user.roles.roleName}"/>
	<input type="hidden" id="userId" value="${user.userId}"/>
	<!--header-->
	<header id='header'>
		<div class="container">
			<div class="logo pull-left"><a href="site/findAllWebCase.do"><img src="material/images/logo.png"/></a></div>			
		</div>
	</header>
	
		<div align="center" style="" ><br><br><br>
	<center><span style="color: red">${msg}</span></center>
	<br>
	<center><a href="${linked}">返回</a></center>
		</div>

</body>
</html>
