<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.context.annotation.Import"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.pm.site_design.entity.WebCase"%>
<%@page import="com.pm.system.entity.User"%>
<%@page import="java.util.List"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    
    <title>网站案例详细-素材库</title>

    <!-- Bootstrap 调试使用-->
    <!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="material/css/bootstrap.min.css">
    <!-- font-awesome 矢量图标-->
    <link rel="stylesheet" href="material/css/font-awesome.min.css"/>

    <!-- user define -->
    <link rel="stylesheet" href="material/css/main.css"/>
    <link rel="stylesheet" href="material/css/detail.style.css"/>
	<style type="text/css">
		b, a.zan {
			user-select: none;
			-webkit-user-select: none;	
			cursor: pointer;
   		 }
  	    b { cursor: pointer;}
	</style>
    <!-- jQuery -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="material/js/jquery-1.11.2.min.js"></script>
    <script src="material/js/bootstrap.min.js"></script>
    <script src="material/js/masonry.pkgd.min.js"></script>
    <script src="material/js/imagesloaded.pkgd.min.js"></script>

    <script src="material/js/main.js"></script>
    <!-- this page script-->
    <script type="text/javascript">
    	$(document).ready(function () {
            $('.ctr-left').click(function() {
                var $oThisList = $(this).parent().find('.detailWindow ul');
                if(!$oThisList.is(':animated')) {
                    var iPicTotal = $oThisList.children('li').length;
                    var vWindowWidth = $(this).parent().find('.detailWindow').width(),
                        vItemWidth = $oThisList.children('li').not('.active').width() + 10,
                        vMarginWidth = 26;
                        
                    var iGroup = Math.ceil(vWindowWidth / (vItemWidth + vMarginWidth));

                    var vThisListPoz = parseInt($oThisList.css('left').split('px')[0]) + (vItemWidth + vMarginWidth) * iGroup;

                    if (vThisListPoz >= 0) {
                        vThisListPoz = 0;
                    }
                    $oThisList.stop().animate({
                        left: vThisListPoz + 'px'
                    });
                }
            })

            $('.ctr-right').click(function() {
                var $oThisList = $(this).parent().find('.detailWindow ul');
                if(!$oThisList.is(':animated')) {
                    var iPicTotal = $oThisList.children('li').length;

                    var vWindowWidth = $(this).parent().find('.detailWindow').width(),
                        vItemWidth =  $oThisList.children('li').not('.active').width() + 10,
                        vMarginWidth = 26;
                    var iGroup = Math.ceil( vWindowWidth / (vItemWidth + vMarginWidth) );
			
                    var vThisListPoz = parseInt($oThisList.css('left').split('px')[0]) - (vItemWidth + vMarginWidth) * iGroup;

					console.log(iGroup)
                    var vPozJudge = -(vItemWidth + vMarginWidth) * iPicTotal + (vItemWidth + vMarginWidth) * iGroup;
                    if( vThisListPoz <= vPozJudge) {
                        vThisListPoz = vPozJudge;
                    }
                    if( vThisListPoz > 0){
                        vThisListPoz = 0;
                    }
                    $oThisList.stop().animate({
                        left: vThisListPoz + 'px'
                    });
                }
            })
            
            $('.picLeft').click(function() {
            	$('.detailWindow li.active').prev('li').find('a').trigger('click');
            })
            $('.picRight').click(function() {
            	$('.detailWindow li.active').next('li').find('a').trigger('click');
            })
            
            $('.detailWindow li a').click(function() {
            	var TthisImg = $(this).find('img').attr('src');
            	$('.detailPic img').attr('src', TthisImg);
            	
            	$('.detailWindow li').removeClass('active');
            	$(this).parent().addClass('active');
            	return false;
            })
            $('.detailWindow li:first-child a').trigger('click');
            
            $(window).keydown(function(event){
			  switch(event.keyCode) {
			    case 37: 
			    	$('.detailWindow li.active').prev('li').find('a').trigger('click');
			    	break;
			    case 39:
			    	$('.detailWindow li.active').next('li').find('a').trigger('click');
			    	break;
			  }
			});
			
			$("a.zan").on("click",function(e){
				var siteNumber = $("#siteNumber").val()
				var thumb = $("#thumb").val()
				$.ajax(  
              	{  
                   type:"post",  
                   url:"site/addSiteThumb.do",  
                   data:{"siteNumber":siteNumber},  
                   dataType:"json",  
                   success:function(data)  
                    {  
                        if(data.result=="0")  
                        { 
                          	
                        }else{
                        	alert("点赞失败！");
                        }
                    },  
                    error:function()  
                    {  
                        alert("点赞失败！");  
                    }  
               	 }); 
               	 anp(e); 
               	 return false; 
			 })
			 
			 function anp(e){
	 			var n=1;
				var $i=$("<b>").text("+"+n);
				var x=e.pageX,y=e.pageY;
				$i.css({top:y-20,left:x,position:"absolute",color:"#E94F06"});
				$("body").append($i);
				$i.animate({top:y-180,opacity:0,"font-size":"1.4em"},1500,function(){
					$i.remove();
				});
				 e.stopPropagation();
				}
				
    	})
    
    function delDesigner(){
   	 	var $select = $("#designer");
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
	function delStyle(){
   	 	var $select = $("#style");
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
	function delIndustry(){
   	 	var $select = $("#industry");
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
	function delColor(){
   	 	var $select = $("#color");
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
			<div class="bannerPic pull-left">
				<img src="material/images/banner-item1.png" />
			</div>

			<div class="bannerForms pull-right">
			<form action="site/findAllWebCase.do" method="post">
				<div class="form-group POS-R">
					<input type="text" class="form-control" name="siteIntf" id="siteIntf" value="" placeholder="输入编号" />
					<input type="submit" class="form-control input-group-addon" value='' />
				</div>
				<div class="form-group selectGroup">
					<div class="input-group selectItem">
						<select name="style" class="form-control" autofocus="" onclick="delStyle()" id="style">
							<option value="" selected="selected">风格</option>
							<c:forEach items="${webCaseList}" var="webCaseList">
							<option value="${webCaseList.style}">${webCaseList.style}</option>
							</c:forEach>
						</select>
					</div>
					<div class="input-group selectItem">
						<select name="industry" class="form-control" autofocus="" id="industry" onclick="delIndustry()">
							<option value="" selected="selected">行业</option>
							<c:forEach items="${webCaseList}" var="webCaseList">
							<option value="${webCaseList.industry}">${webCaseList.industry}</option>
							</c:forEach>
						</select>
					</div>
					<div class="input-group selectItem">
						<select name="color" class="form-control" autofocus="" id="color" onclick="delColor()">
							<option value="" selected="selected">颜色</option>
							<c:forEach items="${webCaseList}" var="webCaseList">
							<option value="${webCaseList.color}">${webCaseList.color}</option>
							</c:forEach>
						</select>
					</div>
					<div class="input-group selectItem">
						<select name="designer" class="form-control" autofocus="" id="designer" onclick="delDesigner()">
							<option value="" selected="selected">设计师</option>
							<c:forEach items="${webCaseList}" var="webCaseList">
							<option value="${webCaseList.designer}">${webCaseList.designer}</option>
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
				<li class="active"><a href="site/findAllWebCase.do">网站案例<span class="line"></span></a></li>
				<li><a href="mobile/findAllMobile.do">移动端案例<span class="line"></a></li>
				<li><a href="dynamic/findAllDynamic.do">动效素材<span class="line"></a></li>
				<li><a href="template/findAllTemplate.do">模板素材<span class="line"></a></li>
				<li><a href="picture/findAllPicture.do">图片素材<span class="line"></a></li>
			</ul>
		</div>
	</nav>
	
	<section id='wrapper'>
		<div class="container">
			<h4 class="text-center">${webCaseDetail.industry }公司官网设计</h4>
			<div class="discrip">
				描述：<span>${webCaseDetail.describe }</span>
				<a class='pull-right zan'>
					<input id="siteNumber" type="hidden" value="${webCaseDetail.siteNumber }"/>					
					<i class="fa fa-heart">&nbsp;点赞</i>
				</a>
			</div>
			<div class="detailFrame POS-R">
				<div class="ctr ctr-left"></div>
				<div class="ctr ctr-right"></div>
				<div class="detailWindow POS-R">
					<ul>
					<%
						WebCase webCase = (WebCase)request.getAttribute("webCaseDetail");
						for(int i=0;i<webCase.getPictureList().size();i++){
					%>	
						<li class="active"><a href="#"><img src="<%=webCase.getPictureList().get(i) %>"/></a></li>
		
					<%
					}
						
					 %>
						<li class="active"><a href="#"><img src="material/images/noPic.jpg"/></a></li>
					</ul>		
				</div>
			</div>	
			<div class="detailPic">
				<div class="picLeft"><span class="fa fa-angle-left"></span></div>
				<div class="picRight"><span class="fa fa-angle-right"></span></div>
				<img src="" alt="" class="img-thumbnail"/>
			</div>
		</div>
	</section>
	
</body>
</html>





