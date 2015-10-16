$(document).ready(function(){
	var $loadingNode = $('#loading');
	
	var aGhostIndexArray = [],
		iGhostCount = 0,
		iCurrentIndex = 0;
		
	var $flowNode = $('.flowContent'),
		cNewDom = '',
		oMsnry,
		cLabel = $flowNode.data('label'),
		cJsonUrl = '';
	
//	fnInitData ();
	
	
	var jsonStr = $('#jsonData').val();
	var jsonObj; // --> parse Json
	if (jsonStr) {
		jsonObj = JSON.parse(jsonStr).list; // --> parse Json
		console.log(jsonObj)
		iGhostCount = jsonObj.length;
	}

	fnInitGhostArray();
	
	fnGetNewItems(jsonObj);
	
	$('#loading').show();
	$flowNode.imagesLoaded(function(){
		fnInitMasonry();
		$('#loading').hide();
	});

	
    $(window).scroll(function() {
	    if($(document).height() - $(window).height() - $(document).scrollTop() < 10) {
	    	if( iCurrentIndex >= iGhostCount) {
	    		$('#noMore').show();
	    	} else {
	    		$('#loading').show();
				fnGetNewItems(jsonObj);
				$flowNode.imagesLoaded(function(){
					fnInitMasonry();
					$('#loading').hide();
				});
	    	}
	    }
	});
	
    //user dropdown
    $('#header ul.dropdown-menu li').click(function(e) {
    	
    	if($(this).children('ul')[0] != undefined) {
    		var  roleName = $("#roleName").val();
    		if(roleName=="管理员"){
    			$(this).children('ul').stop().slideToggle();
    			e.stopPropagation();
    		}
    		else if(roleName=="设计师"){
    			$(this).children('ul').stop().slideToggle();
    			e.stopPropagation();
    			 
    		}else{
    			alert("对不起，您无权操作！"); 
    		}
    	}
    })
    $('#header ul.dropdown-menu li').children('ul').click(function(e) {
    	e.stopPropagation();
    })

//	function fnInitData () {
//		switch (cLabel) {
//			case 'web':
//				cJsonUrl = 'js/index.json';
//				
//				break;
//			case 'app':
//				cJsonUrl = 'js/app.json';
//				break;
//			case 'animate':
//				cJsonUrl = 'js/animate.json';
//				break;
//			case 'pic':
//				cJsonUrl = 'material/js/pic.json';
//				break;
//				
//			default:
//				break;
//		}
//	}
	
	function fnInitMasonry () {
		oMsnry = new Masonry($flowNode[0], {
	        itemSelector: '.flowItem',
			columnWidth: '.flowItem',
			transitionDuration: '0.8s',
			hiddenStyle: { opacity: 0 },
			visibleStyle: { opacity: 1}
	    });
	}
	//随机显示(打乱排序规则)
	function fnInitGhostArray () {
		for(var i=0; i < iGhostCount; i++){
			aGhostIndexArray[i] = i; 
		}
		/*aGhostIndexArray.sort(function(a, b) {
			if(Math.random() > 0.5) {
				return a - b;
			} else {
				return b - a;
			}
		});*/
	}
	
	function fnGetNewItems(result) {
		for(var i = 0; i < 8; i++) {
			if(iCurrentIndex < iGhostCount) {
				fnAppendNewDom(aGhostIndexArray[iCurrentIndex], result);
				$('.flowItem').eq(iCurrentIndex).css('opacity', '0');
				iCurrentIndex++;
			}
		}
		$('.flowItem').animate({'opacity': 1}, 300);
	}
	
    function fnAppendNewDom (curr, result) {
    	
    	cNewDom = '';
/*    	var Tsp;
		var re = /^[1-9]([0-9\.]+\d)?$/;*/
    	switch (cLabel) {
			case 'web':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].siteIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="#"><i class="fa fa-eye"></i>'+ result[curr].look +'</a>'; //浏览字段
				cNewDom += '<a href="#"><i class="fa fa-heart"></i>'+ result[curr].thumb +'</a>'; //喜欢字段
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '<div class="hoverBlock">';
				cNewDom += '<div class="HimgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].siteIntf +'</span></p>'; //编号字段
				cNewDom += '<p class="text-center">';
				cNewDom += '<a href="'+ result[curr].siteUrl +'" class="btn btn-default" target="_blank">案例</a>'; //链接-待添加
				cNewDom += '<a href="site/findOneSiteByIntf.do?siteIntf='+result[curr].siteIntf+'" class="btn btn-primary">详情</a>'; //链接-待添加
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'chooseSite':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/><span>'; 
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].siteIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<p>描述：<span>'+ result[curr].describe +'</span></p>';
				cNewDom += '<p>案例链接：<span>'+ result[curr].siteUrl +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="site/toUpdateSite.do?siteIntf='+result[curr].siteIntf+'" ><span style="color:#184691;">修改<span></a>';
				cNewDom += '<a onclick="delSite('+result[curr].siteNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '<a href="site/toAddPic.do?siteIntf='+result[curr].siteIntf+'" ><span style="color:#184691;">增加图片<span></a>';
				cNewDom += '<a href="site/toDelPic.do?siteIntf='+result[curr].siteIntf+'" ><span style="color:#184691;">删除图片<span></a>';
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'mobile':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].mobileIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="mobile/addMobileLook.do?look='+result[curr].look+'&mobileIntf='+result[curr].mobileIntf+'"><i class="fa fa-eye"></i>'+ result[curr].look +'</a>'; //浏览字段
				cNewDom += '<a href="mobile/addMobileThumb.do?thumb='+result[curr].thumb+'&mobileIntf='+result[curr].mobileIntf+'"><i class="fa fa-heart"></i>'+ result[curr].thumb +'</a>'; //喜欢字段
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '<div class="hoverBlock">';
				cNewDom += '<div class="HimgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].mobileIntf +'</span></p>'; //编号字段
				cNewDom += '<p class="text-center">';
				cNewDom += '<a href="'+ result[curr].mobileUrl +'" class="btn btn-default" target="_blank">案例</a>'; //链接-待添加
				cNewDom += '<a href="mobile/findOneMobileByIntf.do?mobileIntf='+result[curr].mobileIntf+'" class="btn btn-primary">详情</a>'; //链接-待添加
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;			
			case 'chooseMobile':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].mobileIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
//				cNewDom += '<a href="mobile/addMobileLook.do?look='+result[curr].look+'&mobileIntf='+result[curr].mobileIntf+'"><i class="fa fa-eye"></i>'+ result[curr].look +'</a>'; //浏览字段
//				cNewDom += '<a href="mobile/addMobileThumb.do?thumb='+result[curr].thumb+'&mobileIntf='+result[curr].mobileIntf+'"><i class="fa fa-heart"></i>'+ result[curr].thumb +'</a>'; //喜欢字段
				cNewDom += '<a href="mobile/toUpdateMobile.do?mobileIntf='+result[curr].mobileIntf+'"><span style="color:#184691;">修改<span></a>';
				cNewDom += '<a onclick="delMobile('+result[curr].mobileNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '<a href="mobile/toAddMobile.do?mobileIntf='+result[curr].mobileIntf+'" ><span style="color:#184691;">增加图片<span></a>';
				cNewDom += '<a href="mobile/toDelMobilePic.do?mobileIntf='+result[curr].mobileIntf+'" ><span style="color:#184691;">删除图片<span></a>';
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'app':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<a href="#" data-toggle="modal" data-target=".modalFrame"><img src="'+ result[curr].picture +'"/></a>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span class="appIntf">'+ result[curr].appIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<p>描述：<span class="describe">'+ result[curr].describe +'</span></p>';
				cNewDom += '<span class="appUrl" style = "display:none;">'+ result[curr].appUrl +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p><a href="mobile/addAppLook.do?look='+result[curr].look+'&appIntf='+result[curr].appIntf+'"><i class="fa fa-eye"></i><span class="look">'+ result[curr].look +'</span></a>';
				cNewDom += '<a href="mobile/addAppThumb.do?thumb='+result[curr].thumb+'&appIntf='+result[curr].appIntf+'"><i class="fa fa-heart"></i><span class="thumb">'+ result[curr].thumb +'</span></a></p>';
				cNewDom += '</div>';
				cNewDom += '<div class="hoverBlock">';
				cNewDom += '<div class="HimgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].appIntf +'</span></p>'; //编号字段
				cNewDom += '<p class="text-center">';
				cNewDom += '<a href="'+ result[curr].appUrl +'" class="btn btn-default" target="_blank">案例</a>'; //链接-待添加
				cNewDom += '<a href="mobile/findOneAppByIntf.do?appIntf='+result[curr].appIntf+'" class="btn btn-primary">详情</a>'; //链接-待添加
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'chooseApp':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].appIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="mobile/toUpdateApp.do?appIntf='+result[curr].appIntf+'"><span style="color:#184691;">修改<span></a>';
				cNewDom += '<a onclick="delApp('+result[curr].appNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '<a href="mobile/toAddApp.do?appIntf='+result[curr].appIntf+'" ><span style="color:#184691;">增加图片<span></a>';
				cNewDom += '<a href="mobile/toDelAppPic.do?appIntf='+result[curr].appIntf+'" ><span style="color:#184691;">删除图片<span></a>';
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'animate':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
//				cNewDom += '<a href="'+result[curr].dynamicUrl+'" target="_blank"><img src="'+ result[curr].picture +'"/></a>'; //图片链接字段
				cNewDom += '<a onclick="dynamicUrl('+result[curr].dynamicNumber+')"><img src="'+ result[curr].picture +'"/></a>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p><input type="hidden" class="dynamicUrl" value="'+result[curr].dynamicUrl+'"/></p>';
				cNewDom += '<p>编号：<span>'+ result[curr].dynamicIntf +'</span></p>'; //编号字段
				cNewDom += '<p>类型：<span>'+ result[curr].materialType +'</span></p>';  //类型字段
				cNewDom += '<p>描述：<span>'+ result[curr].describle +'</span></p>';  //描述字段 
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a><i class="fa fa-eye"></i>'+ result[curr].look +'</a>'; //浏览字段
				cNewDom += '<a href="dynamic/addDynamicThumb.do?thumb='+result[curr].thumb+'&dynamicNumber='+result[curr].dynamicNumber+'"><i class="fa fa-heart"></i>'+ result[curr].thumb +'</a>'; //喜欢字段
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'chooseDynamic':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<a><img src="'+ result[curr].picture +'"/></a>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].dynamicIntf +'</span></p>'; //编号字段
				cNewDom += '<p>类型：<span>'+ result[curr].materialType +'</span></p>';  //类型字段
				cNewDom += '<p>描述：<span>'+ result[curr].describle +'</span></p>';  //描述字段
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="dynamic/toUpdateDynamic.do?dynamicIntf='+result[curr].dynamicIntf+'"><span style="color:#184691;">修改<span></a>';
				cNewDom += '<a onclick="delDynamic('+result[curr].dynamicNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'template':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].templateIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom += '<a href="template/addTemplateLook.do?look='+result[curr].look+'&templateIntf='+result[curr].templateIntf+'"><i class="fa fa-eye"></i>'+ result[curr].look +'</a>'; //浏览字段
				cNewDom += '<a href="template/addTemplateThumb.do?thumb='+result[curr].thumb+'&templateIntf='+result[curr].templateIntf+'"><i class="fa fa-heart"></i>'+ result[curr].thumb +'</a>'; //喜欢字段
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '<div class="hoverBlock">';
				cNewDom += '<div class="HimgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].templateIntf +'</span></p>'; //编号字段
				cNewDom += '<p class="text-center">';
				cNewDom += '<a href="'+ result[curr].templateUrl +'" class="btn btn-default" target="_blank">设计</a>'; //链接-待添加
				cNewDom += '<a href="template/findOneTemplateByIntf.do?templateIntf='+result[curr].templateIntf+'" class="btn btn-primary">预览</a>'; //链接-待添加
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'chooseTemplate':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<img src="'+ result[curr].picture +'"/>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span>'+ result[curr].templateIntf +'</span></p>'; //编号字段
				cNewDom += '<p>风格：<span>'+ result[curr].style +'</span></p>';  //风格字段
				cNewDom += '<p>行业：<span>'+ result[curr].industry +'</span></p>'; //行业字段
				cNewDom += '<p>颜色：<span class="colorBlock" style="background: '+ result[curr].color +'"></span></p>'; //颜色字段
				cNewDom += '<p>设计师：<span>'+ result[curr].designer +'</span></p>';
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				
				cNewDom += '<a href="template/toUpdateTemplate.do?templateIntf='+result[curr].templateIntf+'"><span style="color:#184691;">修改<span></a>';
				cNewDom += '<a onclick="delTemplate('+result[curr].templateNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '<a href="template/toAddTemplate.do?templateIntf='+result[curr].templateIntf+'" ><span style="color:#184691;">增加图片<span></a>';
				cNewDom += '<a href="template/toDelTemplatePic.do?templateIntf='+result[curr].templateIntf+'" ><span style="color:#184691;">删除图片<span></a>';
				cNewDom += '</p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'pic':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<a href="#" data-toggle="modal" data-target=".modalFrame"><img src="'+ result[curr].pictureUrl +'"/></a>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span class="pictureIntf">'+ result[curr].pictureIntf +'</span></p>'; //编号字段
				cNewDom += '<p>描述：<span class="classification">'+ result[curr].classification +'</span></p>';  //分类字段
				cNewDom += '<p>上传者：<span class="uploadUser">'+ result[curr].uploadUser +'</span></p>'; //作者字段
				cNewDom += '<p>版权信息：<span class="copyrightInformation">'+ result[curr].copyrightInformation +'</span></p>'; //版权字段
				cNewDom += '<p>备注：<span class="note">'+ result[curr].note +'</span></p>'; //备注字段
				cNewDom += '<span class="picture" style="display:none;">'+ result[curr].picture +'</span></p>'; //备注字段
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p>';
				cNewDom +='<a><i class="fa fa-eye"></i><span class="look">'+ result[curr].look +'</span></a>';
				cNewDom += '<a href="picture/addPictureThumb.do?thumb='+result[curr].thumb+'&pictureIntf='+result[curr].pictureIntf+'"><i class="fa fa-heart"></i><span class="thumb">'+ result[curr].thumb +'</span></a></p>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			case 'choosePicture':
				cNewDom += '<div class="col-xs-3 flowItem">';
				cNewDom += '<div class="infoBlock">';
				cNewDom += '<div class="imgBlock">';
				cNewDom += '<a href="#" data-toggle="modal" data-target=".modalFrame"><img src="'+ result[curr].pictureUrl +'"/></a>'; //图片链接字段
				cNewDom += '</div>';
				cNewDom += '<p>编号：<span class="pictureIntf">'+ result[curr].pictureIntf +'</span></p>'; //编号字段
				cNewDom += '<p>描述：<span class="classification">'+ result[curr].classification +'</span></p>';  //分类字段
				cNewDom += '<p>上传者：<span class="uploadUser">'+ result[curr].uploadUser +'</span></p>'; //作者字段
				cNewDom += '<p>版权信息：<span class="copyrightInformation">'+ result[curr].copyrightInformation +'</span></p>'; //版权字段
				cNewDom += '<p>备注：<span class="note">'+ result[curr].note +'</span></p>'; //备注字段
				cNewDom += '<span class="picture" style="display:none;">'+ result[curr].picture +'</span></p>'; //备注字段
				cNewDom += '<div class="mediaBlock">';
				cNewDom += '<p><a href="picture/toUpdatePicture.do?pictureIntf='+result[curr].pictureIntf+'"><span style="color:#184691;">修改<span></a>';
//				cNewDom += '<a href="picture/toAddPicture.do?pictureIntf='+result[curr].pictureIntf+'" ><span style="color:#184691;">增加<span></a>';
				cNewDom += '<a onclick="delPic('+result[curr].pictureNumber+')" ><span style="color:#184691;">删除<span></a>';
				cNewDom += '</p>'
				cNewDom += '</div>';
				cNewDom += '</div>';
				cNewDom += '</div>';
				break;
			default:
				break;
		}
		$flowNode.append(cNewDom);
    }
})