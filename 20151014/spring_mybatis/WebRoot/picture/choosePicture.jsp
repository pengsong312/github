<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.pm.system.entity.User"%>
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
    <base href="<%=basePath%>">    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <title>图片修改选择-素材库</title>

    <!-- Bootstrap 调试使用-->
    <!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="material/css/bootstrap.min.css">
    <!-- font-awesome 矢量图标-->
    <link rel="stylesheet" href="material/css/font-awesome.min.css"/>

    <!-- user define -->
    <link rel="stylesheet" href="material/css/main.css"/>
    <link rel="stylesheet" href="material/css/index.style.css"/>
    <link rel="stylesheet" href="material/css/pic.style.css"/>
    
	
    <!-- jQuery -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="material/js/jquery-1.11.2.min.js"></script>
    <script src="material/js/bootstrap.min.js"></script>
    <script src="material/js/masonry.pkgd.min.js"></script>
    <script src="material/js/imagesloaded.pkgd.min.js"></script>

    <script src="material/js/main.js"></script>
    <!-- this page script-->
    <script type="text/javascript">

    	$(document).ready(function() {
    		$(document).bind('click', '#wrapper .imgBlock a', function (e) {
    			var TthisLink = $(e.target).attr('src');
    				TthisPictureIntf = $(e.target).parents('.flowItem').find('.pictureIntf').html();  
    				TthisNote = $(e.target).parents('.flowItem').find('.note').html();
    				TthisThumb = $(e.target).parents('.flowItem').find('.thumb').html(); 
					TthisPicture = $(e.target).parents('.flowItem').find('.picture').html(); 
    				$('.modalFrame .modal-body img').attr('src', TthisLink);
    				$('#myLargeModalLabel').html(TthisNote);
    				$('.picture').html(TthisPicture);
    				/*
					* 点赞与下载
					*/
    				$('#addThumb').val(TthisPictureIntf); 				
    				$('#thumb').val(TthisThumb);
    				/* picture download */
  					var begin = TthisLink.lastIndexOf("/")+1;
  					var end =  TthisLink.length
  					var imgName = TthisLink.substring(begin,end)
    				$('#filename').val(imgName);	
    		})
    		
    	})
    function delUploadUser(){
    	
    	var $select = $("#uploadUser");
		var $options = $select.find("option");
		for(var i=0,max=$options.length;i<max;i++){
    		var $item1 = $options.eq(i);
    		for(var j=0;j<max;j++){
       		 	if(i==j){
            		continue;
		        }
			var $item2 = $options.eq(j);        
        	if($item1.attr("value") == $item2.attr("value")){
            	i > j ? $item1.remove() : $item2.remove();
        		}        
    		}
		}
    
    }
    /*删除option中重复的值*/	   
   	 function delMaterialType(){
   	 	var $select = $("#classification");
		var $options = $select.find("option");
		for(var i=0,max=$options.length;i<max;i++){
    		var $item1 = $options.eq(i);
    		for(var j=0;j<max;j++){
       		 	if(i==j){
            		continue;
		        }
			var $item2 = $options.eq(j);        
        	if($item1.attr("value") == $item2.attr("value")){
            	i > j ? $item1.remove() : $item2.remove();
        		}        
    		}
		}
	}
	
	function checkAdmin(){
		var  roleName = $("#roleName").val();
		var  userId = $("#userId").val();	
		if(roleName!='管理员'){
			alert("对不起，您无权操作！");
			return false;
		}
		else{
			window.location="/spring_mybatis/user/findAllUser.do?userId="+userId;
			return true;
		}
	}
	
	function delPic(pictureNumber){
		if(confirm("确定删除？")){
			window.location = "/spring_mybatis/picture/delPicture.do?pictureNumber="+pictureNumber;
		}
	}
    </script>
</head>
<body>
		<%
			User user = (User)request.getSession().getAttribute("user");
		%>	
	<input type="hidden" id="roleName" value="<%=user.getRoles().getRoleName() %>"/>
	<input type="hidden" id="userId" value="<%=user.getRoleIds() %>"/>
	<!--header-->
	<header id='header'>
		<div class="container">
			<div class="logo pull-left"><a href="site/findAllWebCase.do"><img src="material/images/logo.png"/></a></div>			
			<div class="user pull-right dropdown">
		
				<div class="userPic pull-left"><img src="<%=user.getPhoto()%>"/></div>
				<a href="#" class="name pull-left FONT14 COLOR-W dropdown-toggle" data-toggle='dropdown' id='dropD'><%=user.getRealName()%><span class="fa fa-angle-down COLOR-W btn-down"></span></a>
				<ul class="dropdown-menu" aria-labelledby="dropD">					
	                <li><a href="user/toSet.do">设置</a></li>
	                <li><a onclick='checkAdmin()'>管理用户</a></li>
	                <li>
	                	<a>上传<span class="caret"></span></a>
	                	<ul>
	                		<li><a href="site/toUploadSite.do">网站案例</a></li>
	                		<li>
	                			<a>移动案例<span class="caret"></span></a>
	                			<ul>
	                				<li><a href="mobile/toUploadMobile.do">手机站</a></li>
	                				<li><a href="mobile/toUploadApp.do">APP</a></li>
	                			</ul>
	                		</li>
	                		<li><a href="dynamic/toUploadDynamic.do">动效素材</a></li>
	                		<li><a href="template/toUploadTemplate.do">模板素材</a></li>
	                		<li><a href="picture/toUploadPicture.do">图片素材</a></li>
	                	</ul>
	                </li>
	                <li>
	                	<a>修改<span class="caret"></span></a>
	                	<ul>
	                		<li><a href="site/chooseSite.do">网站案例</a></li>
	                		<li>
	                			<a>移动案例<span class="caret"></span></a>
	                			<ul>
	                				<li><a href="mobile/chooseMobile.do">手机站</a></li>
	                				<li><a href="mobile/chooseApp.do">APP</a></li>
	                			</ul>
	                		</li>
	                		<li><a href="dynamic/chooseDynamic.do">动效素材</a></li>
	                		<li><a href="template/chooseTemplate.do">模板素材</a></li>
	                		<li><a href="picture/choosePicture.do">图片素材</a></li>
	                	</ul>
	                </li>
	                <li role="separator" class="divider"></li>
	                <li><a href="user/logout.do">注销</a></li>
             	 </ul>
			</div>
		</div>
	</header>

	<!--banner-->
	<div id="banner">
		<div class="container">
			<div class="bannerPic pull-left"><img src="material/images/banner-item1.png"/></div>
			
			<div class="bannerForms pull-right">
				<form action="picture/findUpdatePictures.do" method="post">
					<div class="form-group POS-R">
						<input type="text" class="form-control" name="pictureIntf" id="pictureIntf" value="" placeholder="输入编号"/>
						<input type="submit" class="form-control input-group-addon" value=''/>
					</div>
					<div class="form-group selectGroup">
						<div class="input-group selectItem">
							<select name="classification" class="form-control" autofocus="" onclick="delMaterialType()" id="classification">
								<option value="" selected="selected">类型</option>
					      		<option value="极简主义">极简主义</option>
					      		<option value="清新">清新</option>
					      		<option value="扁平化">扁平化</option>
					      		<option value="商城化">商城化</option>
					      		<option value="多图">多图</option>
					      		<option value="内容优先">内容优先</option>
					      		<option value="其他">其他</option>
							</select>
						</div>
						<div class="input-group selectItem">
							<select name="uploadUser" class="form-control" autofocus="" onclick="delUploadUser()" id="uploadUser">
					      		<option value="" selected="selected">上传者</option>
					      		<c:forEach items="${pictureList}" var="pictureList">
					      		<option value="${pictureList.uploadUser}">${pictureList.uploadUser}</option>
					      		</c:forEach>
							</select>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!--nav-->
	<nav id="nav">
		<div class="container">
			<ul>
				<li><a href="site/findAllWebCase.do">网站案例<span class="line"></span></a></li>
				<li><a href="mobile/findAllMobile.do">移动端案例<span class="line"></a></li>
				<li><a href="dynamic/findAllDynamic.do">动效素材<span class="line"></a></li>
				<li><a href="template/findAllTemplate.do">模板素材<span class="line"></a></li>
				<li class="active"><a href="picture/findAllPicture.do">图片素材<span class="line"></a></li>
			</ul>
		</div>
	</nav>
	
	
	<section id='wrapper'>
		<div class="container">
			<div class="row flowContent POS-R FONT12" data-label='choosePicture'>
			</div>
		</div>
	</section>
	
	<section id="loading">
		<div class="container">
			<div class="loadingPic"><img src="material/images/loading.gif" alt="" /></div>
		</div>
	</section>
	
	<section id="noMore">
		<div class="container">
			<a href="picture/findAllPicture.do" class="btn-noMore">没有更多了</a>
		</div>
	</section>
	
	<input type='hidden' id='jsonData' value='${json}'/>
</body>
</html>





