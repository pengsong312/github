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
    <title>动效-素材库</title>

    <!-- Bootstrap 调试使用-->
    <!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="material/css/bootstrap.min.css">
    <!-- font-awesome 矢量图标-->
    <link rel="stylesheet" href="material/css/font-awesome.min.css"/>

    <!-- user define -->
    <link rel="stylesheet" href="material/css/main.css"/>
    <link rel="stylesheet" href="material/css/index.style.css"/>
    <link rel="stylesheet" href="material/css/animate.style.css"/>
    
	
    <!-- jQuery -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="material/js/jquery-1.11.2.min.js"></script>
    <script src="material/js/bootstrap.min.js"></script>
    <script src="material/js/masonry.pkgd.min.js"></script>
    <script src="material/js/imagesloaded.pkgd.min.js"></script>

    <script src="material/js/main.js"></script>
    <script type="text/javascript">
    function delMaterialType(){
   	 	var $select = $("#materialType");
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
			window.location="/spring_mybatis/user/findAllUser2.do";
			return true;
		}
	}
	
	  	
	function dynamicUrl(dynamicNumber){
		var dynamicUrl = $(".dynamicUrl").val();
		/*$.ajax(  
              	{  
                   type:"get",  
                   url:"dynamic/addDynamicLook.do",
                   data:{"dynamicNumber":dynamicNumber},  
                   dataType:"json",  
                   success:function(data)  
                    {  
                        if(data.result=="0")  
                        { 
                          	
                        }else{
                        	alert("增加浏览失败！");
                        }
                    },  
                    error:function()  
                    {  
                        alert("增加浏览失败！");  
                    }  
               	 }); */
        window.location="/spring_mybatis/dynamic/addDynamicLook.do?dynamicNumber="+dynamicNumber;
		window.open(dynamicUrl);
	}
    </script>
    <!-- this page script-->
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
				<form action="dynamic/findAllDynamic.do">
					<div class="form-group POS-R">
						<input type="text" class="form-control" name="dynamicIntf" id="dynamicIntf" value="" placeholder="输入编号"/>
						<input type="submit" class="form-control input-group-addon" value=''/>
					</div>
					
					<div class="form-group selectGroup">
						<div class="input-group selectItem">
							<select name="materialType" class="form-control" autofocus="" id="materialType" onclick="delMaterialType()">
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
				<li class="active"><a href="dynamic/findAllDynamic.do">动效素材<span class="line"></a></li>
				<li><a href="template/findAllTemplate.do">模板素材<span class="line"></a></li>
				<li><a href="picture/findAllPicture.do">图片素材<span class="line"></a></li>
			</ul>
		</div>
	</nav>

	<section id='wrapper'>
		<div class="container">
			<div class="row flowContent POS-R FONT12" data-label='animate'><!--
			<c:forEach items="${dynamicList}" var="dynamicList">
				<div class="col-xs-3 flowItem">
					<div class="infoBlock">
						<div class="imgBlock">
							<img src="${dynamicList.picture}"/>
						</div>
						<p>编号：<span>${dynamicList.dynamicIntf}</span></p>
						<p>类型：<span>${dynamicList.materialType}</span></p>
						<p>描述：<span>${dynamicList.describle}</span></p>
						
						<div class="mediaBlock">
							<p>
								<a href=""><i class="fa fa-eye"></i>${dynamicList.look}</a>
								<a href=""><i class="fa fa-heart"></i>${dynamicList.thumb}</a>
							</p>
						</div>
					</div>
					<div class="hoverBlock">
						<div class="HimgBlock">
							<img src="${dynamicList.picture}"/>
						</div>	
						
						<p>编号：<span>${dynamicList.dynamicIntf}</span></p>
						<p class="text-center">
							<a href="${dynamicList.dynamicUrl}" class="btn btn-default">案例</a>
							<a href="#" class="btn btn-primary">详情</a>
						</p>
					</div>
				</div>
				</c:forEach>
			--></div>
		</div>
	</section>
	
	<section id="loading">
		<div class="container">
			<div class="loadingPic"><img src="material/images/loading.gif" alt="" /></div>
		</div>
	</section>
	
	<section id="noMore">
		<div class="container">
			<a href="dynamic/findAllDynamic.do" class="btn-noMore">没有更多了</a>
		</div>
	</section>
	<input type='hidden' id='jsonData' value='${json}'/>
</body>
</html>





