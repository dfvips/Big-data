<%@page import="com.zdata.model.SysUser"%>
<%@page import="com.zdata.model.SysSoft"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String serverPath = request.getServerName()+":"+request.getServerPort()+path+"/";
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	SysSoft soft = (SysSoft) session.getAttribute("curSoft");
	String softId = soft.getSoftId();
	String softName = soft.getSoftName();
	String webUrl = soft.getWebUrl();
	String mainUrl = soft.getMainUrl();
	String softBottom = soft.getSoftCreate()+",版本："+soft.getSoftVersion();
	SysUser user = (SysUser) session.getAttribute("curUser");
	String encoded = user.getEncoded();
	String userId = user.getUserId();
	String userName = user.getUserName();
	String publicKey = user.getPublicKey();
%>
<!DOCTYPE html>
<html>
<head>
<title>系统后台</title>
	<!-- Meta tag Keywords -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="utf-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/startic/images/cy.png" type="image/x-icon" />
	
	<meta name="keywords" content="" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layer-v3.1.1/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath}/startic/js/fileutil.js"></script>
	<script src="${pageContext.request.contextPath}/startic/js/config.js"></script>
	<style type="text/css">
		.message-box {
		    cursor: pointer;
		    width: 100px;
		    height: 28px;
		    position: fixed;
		    z-index: 9999;
		    right: 120px;
		    bottom: 5px;
		}
		
		.message-box #buttonIcon {
		    height: 100%;
		    float: right;
		}
		
		.hover-box {
		    display: none;
		    width: 270px;
		    height: 240px;
		    background: #fff;
		    z-index: 9999;
		    position: absolute;
		    right: 2px;
		    bottom: 26px;
		    box-shadow: 1px 1px 5px #ddd;
		    border-radius: 6px;
		}
		
		.hover-box .title {
		    position: relative;
		    width: 100%;
		    height: 14%;
		    border-bottom: 1px solid #f0eded;
		}
		
		.hover-box .title .text {
		    position: absolute;
		    top: 9px;
		    left: 10px;
		    font-size: 14px;
		    color: #00ced1;
		    font-weight: bold;
		}
		
		.hover-box .title img {
		    position: absolute;
		    right: 0;
		    height: 70%;
		    padding: 4px;
		}
		
		.hover-box .content {
		    width: 100%;
		    height: 86%;
		    padding: 10px 0;
		    box-sizing: content-box;
		}
		
		.hover-box .content ul {
		    position: relative;
		    height: 100%;
		}
		
		.hover-box .content ul li {
		    padding: 5px 23px;
		    font-size: 12px;
		    font-weight: 100;
		    color: #6183bd;
		}
		
		.hover-box .content ul li .count {
		    color: #ed3232;
		    font-weight: 600;
		    margin-left: 5px;
		}
		
		.hover-box .content ul li:hover,
		.hover-box .content ul li:active,
		.hover-box .content ul li:visited {
		    color: #d75555
		}
		
		.hover-box .content ul li:last-child {
		    position: absolute;
		    bottom: 15px;
		    right: -14px;
		}
	</style>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div id="softName" class="layui-logo"><%=softName %></div>
			<!-- 头部区域（可配合layui已有的水平导航） -->
			<ul class="layui-nav layui-layout-left" id="menuUl" lay-filter="top-menu">
				<li id="icon_li" class="layui-nav-item">
					<a id="icon_a" href="javascript:;" title="侧边伸缩" onclick="iconHide();">
					  <i id="icon_menu" class="layui-icon layui-icon-shrink-right"></i>
					</a>
				</li>
			</ul>
			<ul id="ul_right" class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:void(0);" title="系统首页" onclick="addTab('系统首页','${pageContext.request.contextPath}<%=mainUrl %>?softId=<%=softId %>','','<%=softId%>000')"><i class="layui-icon layui-icon-home"></i></a></li>
				<li class="layui-nav-item"><a href="javascript:void(0);" title="任务中心" onclick="addTab('任务中心','<%=basePath %>/zdata-oas/views/task/index.jsp?softId=<%=softId %>&userId=<%=encoded %>&publicKey=<%=publicKey %>','','<%=softId%>')"><i class="layui-icon layui-icon-notice"></i></a></li>
				<li class="layui-nav-item"><a title="网站" href="<%=webUrl %>?softId=<%=softId %>" target="_Blank"><i class="layui-icon layui-icon-website"></i></a></li>
				<li class="layui-nav-item"><a href="javascript:;"> <%=userName %>
				</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:void(0);" onclick="userInfo()">个人信息</a>
						</dd>
						<dd>
							<a href="javascript:void(0);" onclick="upPassword()">修改密码</a>
						</dd>
						<dd>
							<a href="javascript:void(0);" onclick="outlogin()">退出登录</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="javascript:void(0);" title="版本信息" onclick="showSystemInfo()"><i class="layui-icon layui-icon-more-vertical"></i></a></li>
			</ul>
		</div>
		
		<!-- 侧边菜单 -->
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<ul class="layui-nav layui-nav-tree" lay-filter="left-menu" id="menuLeft">
				</ul>
			</div>
		</div>
		<!-- 主体内容 -->
      	<div class="layui-body" style="padding: 0px 5px 0px;">
			<!-- 上传附件 -->
			<div class="layui-tab layui-tab-card" lay-allowclose="true" lay-filter="funcTab">
				<ul class="layui-tab-title">
					<li lay-id="<%=softId%>" class="layui-this">首页</li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<iframe id="main_ifram" scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}<%=mainUrl %>?softId=<%=softId %>" style="width:100%;"></iframe>
					</div>
				</div>
			</div>
			<%-- <iframe id="main" name="main" src="${pageContext.request.contextPath}<%=softRunMain %>" style="width: 100%; height: 99%" frameborder="0">
			</iframe> --%>
			<!-- <form id="uploadFile" enctype="multipart/form-data">
				<table id="fileContents" class="layui-table">
					<colgroup>
						<col width="100">
						<col width="200">
						<col width="250">
						<col >
						<col width="150">
					</colgroup>
					<thead>
						<tr>
							<td colspan="4"><span>上传附件</span></td>
							<td >
								<button type="button" onclick="addAttachment()" class="layui-btn layui-btn-primary layui-btn-radius layui-btn-xs layui-btn-normal">增加</button>
								<button type="button" onclick="delAttachment()" class="layui-btn layui-btn-danger layui-btn-radius layui-btn-xs layui-btn-normal">删除</button>
							</td>
						</tr>
					</thead>
					<thead>
						<tr>
							<th>序号</th>
							<th>附件文件名</th>
							<th>标题</th>
							<th colspan="2">描述</th>
						</tr>
					</thead>
					<tr>
						<td id="array_1"><span>1</span></td>
						<td><input type="file" onchange="uploadFile(1)" name="files"> 
							文件地址
							<input type="hidden" id="fileUrl_1" name="fileUrl_1"> 
							文件大小
							<input type="hidden" id="fileSize_1" name="fileSize_1"> 
							文件后缀
							<input type="hidden" id="fileSub_1" name="fileSub_1">
						</td>
						<td>
							文件名称 
							<input class="layui-input" id="fileName_1" name="fileName_1">
						</td>
						<td colspan="2">
							文件描述 
							<input class="layui-input" name="fileDesc_1" id="fileDesc_1">
						</td>
					</tr>
				</table>
			</form>
			<button type="button" onclick="save()">保存数据</button>
			<table id="fileInfo">
			</table> -->
			
      	</div>
		<div class="layui-footer" style="text-align: center;">
			<!-- 底部固定区域 -->
			<%=softBottom %>
		</div>
		<div class="message-box">
             <img src="${pageContext.request.contextPath}/startic/images/sendread.png" id="buttonIcon">
             <div class="hover-box">
                 <div class="title">
                     <div class="text">待办事项
                     </div>
                 <img src="${pageContext.request.contextPath}/startic/images/close.png" alt="closePop" id="closePop">
             	 </div>
                 <div class="content">
                 	<ul>
                 		
                 	</ul>
                 </div>
            </div>
       </div>
	</div>
	
	<script type="text/javascript">
	let softId = "<%=softId %>";
	let userId =  "<%=userId %>";
	let encoded = "<%=encoded %>";
	let publicKey = "<%=publicKey%>";
	let basePath = "<%=basePath%>";
	let serverPath = "<%=serverPath%>";
	let softName = "<%=softName %>";
	var timer;
	$(function(){
		
		//全局的ajax访问，处理ajax清求时sesion超时 
		$.ajaxSetup({
		    contentType : "application/x-www-form-urlencoded;charset=utf-8",
		    complete : function(XMLHttpRequest, textStatus) {
		        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
		        if (sessionstatus == "timeout") {
		            // 如果超时就处理 ，指定要跳转的页面
		            window.location.replace("/");
		        }
		    }
		});
		
		initTopMenu(); //初始化头部菜单
		initJsAndCss();
		initWebSocket();//初始化webSocket会话连接
		//做监听
		window.setInterval(function() {
			var tab = getCookie("tab");
			var title = getCookie("title");
			var url = getCookie("url");
			var icon = getCookie("icon");
			if( tab != "" && tab != null){
				addTab(title,url,icon,tab);
				console.log("监听到新tab:"+tab+":"+title+":"+url+":"+icon);
			}
			//获取退出登录cookie值
			var outLogin = getCookie("outLogin");
			if(outLogin=="true"){
				//退出登录
				window.location.href="${pageContext.request.contextPath}/login.do";
				setCookie("outLogin","false");
			}
		},1000);
		if(softId=="120"){
			$('#softName').remove();
			$('#menuUl').css("left","0px");
			$('#menuUl').css("position","");
			document.getElementById("icon_menu").className='layui-icon';
			//$('#icon_a').remove();
			//$('#icon_menu').remove();
			//$('#icon_li').remove();
			//$('#main_ifram').attr("src", "39.98.90.181:8081/main.html");
		}
	});
	
	function initWebSocket(){
		if("WebSocket" in window){
			var ws = new WebSocket("ws://"+serverPath+"/ws?userId="+userId);
			ws.onopen = function(){
				//ws.send("发送数据！");
			}
			ws.onmessage = function(event){
				var received_msg = event.data;
				var result = JSON.parse(received_msg);
				if(result.status=="success"){
					var rows = result.rows;
					//有数据
					if(rows.length>0){
						let resultHtml = '';
						for(var i=0;i<rows.length;i++){
							if(rows[i].func!=null){
								resultHtml += '<li id="li'+rows[i].funcId+'" onclick="showRemindTab(' + JSON.stringify(rows[i].func).replace(/"/g, '&quot;') + ')">'+rows[i].remindName+'(<span class="count">'+rows[i].remindCount+'</span>)</li>';
							}else{
								resultHtml += '<li id="li'+rows[i].funcId+'" onclick="showRemindInfoTab(\''+rows[i].funcId+'\')">'+rows[i].remindName+'(<span class="count">'+rows[i].remindCount+'</span>)</li>';
							}
						}
						resultHtml  += '<li id="ignore" onclick="ignoreAction()">忽略全部</li>'
						$(".content ul").append(resultHtml);
					}else{
						clearTimeout(timer);
					}
				}
			}
			ws.onclose = function(){
				
			}
		}else {
			layer.msg("您的浏览器不支持 WebSocke！");
		}
	}
	
	function showRemindTab(func){
		//判断是否为全URL显示
		if(func.funcType=="1"){
			var url = func.funcUrl+"?"+func.funcParam;
			var icon = func.funcImg;//图标,暂时不用
			var title = func.funcName;//菜单名称
			var funcId = func.funcId;//功能Id
			addTab(title,url,icon,funcId);
		}else if(func.funcType=="2"){
			var url = basePath+func.urlBasic+"?"+func.funcParam;
			var icon = func.funcImg;//图标,暂时不用
			var title = func.funcName;//菜单名称
			var funcId = func.funcId;//功能Id
			addTab(title,url,icon,funcId);
		}
	}
	
	function showRemindInfoTab(funcId){
		if(funcId==softId+userId){
			userInfo();
		}
	}
	
	function initJsAndCss() {
		var h = $(window).height() - 172;
		$("iframe").css("height", h + "px");
		// 控制窗口显示隐藏
        $(".message-box").hover(function() {
            $(".hover-box").show();
        }, function() {
            $(".hover-box").hide();
        });
        $('#closePop').click(function() {
            $(".hover-box").hide();
        });
        noticeShow();
	}
	
	function ignoreAction(){
		var imgId = document.getElementById("buttonIcon");
		if(imgId.style.visibility!="visible"){
			imgId.style.visibility="visible";
		}
		clearTimeout(timer);
	}
	
	function noticeShow(){
		var imgId = document.getElementById("buttonIcon");
		if(imgId.style.visibility=="visible"){
			imgId.style.visibility="hidden";
		}else{
			imgId.style.visibility="visible";
		}
		timer = setTimeout('noticeShow()',500);
	}
	
	//侧边栏伸缩
	function iconHide(){
		var obj = document.getElementById("icon_menu");
		if(obj.className=='layui-icon layui-icon-shrink-right'){
			obj.className = 'layui-icon layui-icon-spread-left';
			hide();
		}else{
			obj.className='layui-icon layui-icon-shrink-right';
			show();
		}
	}
	//侧边菜单隐藏
	function hide(){
		$('.layui-side span').hide();
        $('.layui-side').animate({width:'50px'});
        $('.layui-body').animate({left:'50px'});
        $('.layui-footer').animate({left:'50px'});
	}
	//侧边菜单显示
	function show(){
		$('.layui-side span').show();  
        $('.layui-side').animate({width:'200px'});
        $('.layui-body').animate({left:'200px'});
        $('.layui-footer').animate({left:'200px'});
	}
	//获取cookie
	function getCookie(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg)){
			return unescape(arr[2]);
		}else{
			return null;
		}
	}
	
	//显示版本信息
	function showSystemInfo(){
		layer.open({
	        type: 1,
	        title: false,
	        closeBtn: false,
	        area: ['300px','700px'],
	        shade: [0.5, '#393D49'],
	        shadeClose: true,
	        offset: 'r',
	        id: 'sysInfo',
	        moveType: 1,
	        content: '<div class="layui-layer-content"><div class="layui-card-header">版本信息</div><div class="layui-card-body layui-text">'+
	        '</div></div>'
	     });
	}
	
	function setCookie(c_name,value){
	    var days = 1; //定义一天
	    var exp = new Date();
	    exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
	    // 写入Cookie, toGMTString将时间转换成字符串
	    document.cookie = c_name + "=" + escape(value) + ";expires=" + exp.toGMTString + ";path=/;domain=" + document.domain;
	}
	
	var isShow = true;  //定义一个标志位
	function changeMenu(funcId){
		/* $('.layui-nav-item span').each(function(){
            if($(this).is(':hidden')){
                $(this).show();
            }else{
                $(this).hide();
            }
        });
        //判断isshow的状态
        if(isShow){
            $('.layui-side.layui-bg-black').width(60); //设置宽度
            //$('.layui-side.layui-bg-black lu li').css('margin-right', '70%');  //修改图标的位置
            //将footer和body的宽度修改
            $('.layui-body').css('left', 60+'px');
            $('.layui-footer').css('left', 60+'px');
            //将二级导航栏隐藏
            $('dd span').each(function(){
                $(this).hide();
            });
            //修改标志位
            isShow =false;
        }else{
            $('.layui-side.layui-bg-black').width(200);
            //$('.layui-nav-item i').css('margin-right', '10%');
            $('.layui-body').css('left', 200+'px');
            $('.layui-footer').css('left', 200+'px');
            $('dd span').each(function(){
                $(this).show();
            });
            isShow =true;
        }
		 */
	}
	
	function initTopMenu(){
		$.ajax({
			url:'${pageContext.request.contextPath}/sysFunc/queryByFuncId.do',
			type:'post',
			data:{
				userId: encoded,
				funcId: softId,
				publicKey: publicKey
			},
			async:false,
			dataType:'json',
			success:function(reData){
				if(reData.status=="success"){
					
					var data = reData.rows;
					for(var i=0;i<data.length;i++){
						if(i>3&&softId!="120"){
							var result="<li class=\"layui-nav-item\"><a href=\"javascript:;\">更多功能</a><dl class=\"layui-nav-child\">";
							for(var j=4;j<data.length;j++){
								result += "<dd><a href=\"javascript:void(0)\" onclick=\"initLeftMenu('"+data[j].funcId+"')\"><i class=\"layui-icon "+data[j].funcImg+"\"></i>&nbsp;&nbsp;"+data[j].funcName+"</a></dd>";
							}
						    result += "</dl></li>";
						    document.getElementById("menuUl").innerHTML += result;
						    break;
						}else if(i==data.length-1&&softId=="120"){
							document.getElementById("ul_right").innerHTML = "<li class=\"layui-nav-item\" lay-id="+i+"><a href=\"#\" onclick=\"initLeftMenu('"+data[i].funcId+"')\"><i class=\"layui-icon "+data[i].funcImg+"\"></i>&nbsp;&nbsp;"+"</a></li>" + document.getElementById("ul_right").innerHTML;
						}else{
							document.getElementById("menuUl").innerHTML += "<li class=\"layui-nav-item\" lay-id="+i+"><a href=\"#\" onclick=\"initLeftMenu('"+data[i].funcId+"')\"><i class=\"layui-icon "+data[i].funcImg+"\"></i>&nbsp;&nbsp;"+data[i].funcName+"</a></li>";
						}
						if(i==0){
							initLeftMenu(data[i].funcId);
						}
					}
					layui.use('element', function(){
						var element = layui.element;
						//刷新top菜单
						element.render('nav', 'top-menu');
					});					
				}
			}
		});
	}
	
	
	
	//左侧栏菜单
	function initLeftMenu(funcId){
		var obj = document.getElementById("icon_menu");
		if(obj.className=='layui-icon layui-icon-spread-left'){
			obj.className='layui-icon layui-icon-shrink-right';
			show();
		}
		//清除内容
		document.getElementById("menuLeft").innerHTML="";
		$.ajax({
			url:'${pageContext.request.contextPath}/sysFunc/queryByFuncId.do',
			type:'post',
			data:{
				userId: encoded,
				funcId: funcId,
				publicKey: publicKey
			},
			async:false,
			dataType:'json',
			success:function(reData){
				if(reData.status=="success"){
					var data = reData.rows;
					for(var i=0;i<data.length;i++){
						if(data[i].isParent==1){
							var result = "<li class=\"layui-nav-item\"><a href=\"javascript:;\"><i class=\"layui-icon "+data[i].funcImg+"\" id=\"func_"+data[i].funcId+"\" onmousemove=\"showFuncName('"+data[i].funcId+"','"+data[i].funcName+"')\"></i>&nbsp;&nbsp;<span>"+data[i].funcName+"</span></a><dl class=\"layui-nav-child\">";
							var children = data[i].children;
							for(var j=0;j<children.length;j++){
								//判断是否为全URL显示
								if(children[j].funcType=="1"){
									var url = children[j].url+"?"+children[j].funcParam;
									var icon = children[j].funcImg==null?"layui-icon-tabs":children[j].funcImg;//图标,暂时不用
									var title = children[j].funcName;//菜单名称
									var funcId = children[j].funcId;//功能Id
									result += "<dd><a href=\"javascript:void(0)\" onclick=\"addTab('"+title+"','"+url+"','"+icon+"','"+funcId+"')\"><i class=\"layui-icon "+icon+"\" id=\"func_"+funcId+"\" onmousemove=\"showFuncName('"+funcId+"','"+title+"')\"></i>&nbsp;&nbsp;<span>"+children[j].funcName+"</span></a></dd>"
								}else if(children[j].funcType=="2"){
									var url = basePath+children[j].urlBasic+"?"+children[j].funcParam;
									var icon = children[j].funcImg==null?"layui-icon-tabs":children[j].funcImg;//图标,暂时不用
									var title = children[j].funcName;//菜单名称
									var funcId = children[j].funcId;//功能Id
									result += "<dd><a href=\"javascript:void(0)\" onclick=\"addTab('"+title+"','"+url+"','"+icon+"','"+funcId+"')\"><i class=\"layui-icon "+icon+"\" id=\"func_"+funcId+"\" onmousemove=\"showFuncName('"+funcId+"','"+title+"')\"></i>&nbsp;&nbsp;<span>"+children[j].funcName+"</span></a></dd>"
								}
							}
							result += "</dl></li>";
							document.getElementById("menuLeft").innerHTML += result;
						}else{
							if(data[i].funcType=="1"){
								var url = data[i].url+"?"+data[i].funcParam;
								var icon = data[i].funcImg==null?"layui-icon-tabs":data[i].funcImg;//图标,暂时不用
								var title = data[i].funcName;//菜单名称
								var funcId = data[i].funcId;//功能Id
								document.getElementById("menuLeft").innerHTML += "<li class=\"layui-nav-item\"><a href=\"javascript:void(0)\" onclick=\"addTab('"+title+"','"+url+"','"+icon+"','"+funcId+"')\"><i class=\"layui-icon "+data[i].funcImg+"\" id=\"func_"+funcId+"\" onmousemove=\"showFuncName('"+funcId+"','"+title+"')\"></i>&nbsp;&nbsp;<span>"+data[i].funcName+"</span></a></li>";
							}else if(data[i].funcType=="2"){
								var url = basePath+data[i].urlBasic+"?"+data[i].funcParam;
								var icon = data[i].funcImg==null?"layui-icon-tabs":data[i].funcImg;//图标,暂时不用
								var title = data[i].funcName;//菜单名称
								var funcId = data[i].funcId;//功能Id
								document.getElementById("menuLeft").innerHTML += "<li class=\"layui-nav-item\"><a href=\"javascript:void(0)\" onclick=\"addTab('"+title+"','"+url+"','"+icon+"','"+funcId+"')\"><i class=\"layui-icon "+data[i].funcImg+"\" id=\"func_"+funcId+"\" onmousemove=\"showFuncName('"+funcId+"','"+title+"')\"></i>&nbsp;&nbsp;<span>"+data[i].funcName+"</span></a></li>";
							}
						}
					}
					layui.use('element', function(){
						var element = layui.element;
						//刷新top菜单
						element.render('nav', 'left-menu');
						element.on('nav(left-menu)',function(data){
							//菜单缩进情况需要进行张开
							var obj = document.getElementById("icon_menu");
							if(obj.className=='layui-icon layui-icon-spread-left'){
								obj.className='layui-icon layui-icon-shrink-right';
								show();
							}
						});
					});
				}
			}
		});
	}
	
	//显示菜单名称
	function showFuncName(funcId,funcName){
		var obj = document.getElementById("icon_menu");
		if(obj.className=='layui-icon layui-icon-spread-left'){
			//刷新top菜单
			layer.closeAll('tips'); //关闭所有的tips层 
			layer.tips(funcName, '#func_'+funcId,{
				tips: 1
			});
		}
	}
	
	/**
	 * 删除cookie
	 */
	function delCookie(name){
		
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval= getCookie(name);
		if(cval!=null){
			document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		}
	}
	
	function addTab(title,url,icon,funcId){
		var content = '<iframe scrolling="auto" frameborder="0"  src="' + url +
				'" style="width:100%;"></iframe>';	
		layui.use('element', function(){
			var element = layui.element;
			element.tabDelete('funcTab', funcId);
			element.tabAdd('funcTab', {
				title: title,
				content: content,
				id: funcId
			});
			//Hash地址的定位
  			var layid = location.hash.replace(/^#funcTab=/, '');
 			element.tabChange('funcTab', funcId);
  			element.on('tab(funcTab)', function(elem){
  				var tabId = $(this).attr('lay-id');//tabId;
  				var tab = getCookie("tab");
  				
  				//相等时候cookie中tab,title,icon,url设置为空
  				if(tab==tabId){
  				    setCookie("tab","");
  				    setCookie("title","");
  				    setCookie("icon","");
  				    setCookie("url","");
  				}
    			location.hash = 'funcTab='+ $(this).attr('lay-id');
  			});
			FrameWH();
			function FrameWH() {
				var h = $(window).height() - 172;
				$("iframe").css("height", h + "px");
			}
		});
	}
	
	//个人 信息
	function userInfo(){
		var url  = zjbs.path + 'xtUserInfo/info';
		var title = '个人信息';
		var icon = 'layui-icon-username';
		//功能Id由softId+userId
		var funcId = softId + userId;
		addTab(title,url,icon,funcId);
	}
	
	//退出登录
	function outlogin(){
		$.ajax({
			url:'${pageContext.request.contextPath}/outLogin.do',
			type:'post',
			dataType:'json',
			success:function(reData){
				if(reData.success){
					window.location.href="${pageContext.request.contextPath}/login.do";
				}
			}
		});
	}
	
	//修改密码
	function upPassword(){
		layer.prompt({
			  formType: 1,
			  value: '',
			  title: '请输入密码'
		}, function(value, index, elem){
			$.ajax({
				url:'${pageContext.request.contextPath}/user/setPassword.do',
				type:'post',
				dataType:'json',
				data:{
					newPassword:value
				},
				success:function(reData){
					if(reData.status=="success"){
						layer.msg("修改密码成功！");
					}else if(reData.status=="fail"){
						layer.msg(reData.msg);
					}
				}
			});
			layer.close(index);
		});
	}
	</script>
</body>
</html>