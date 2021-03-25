<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
	<title>登录</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/startic/css/login.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">

 	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layer-v3.1.1/layer/layer.js"></script>
</head>
<script type="text/javascript">

</script>
<body>
<form action=""  method="post">
<div class="top_div"></div>
	<div
		style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
		<div style="width: 165px; height: 96px; position: absolute;">
			<div class="tou"></div>
			<div class="initial_left_hand" id="left_hand"></div>
			<div class="initial_right_hand" id="right_hand"></div>
		</div>
		<P style="padding: 30px 0px 10px; position: relative;">
			<span class="u_logo"></span> <input class="ipt" type="text" id="userId" name="userId" value=""
				placeholder="请输入用户名" value="">
		</P>
		<P style="position: relative;">
			<span class="p_logo"></span> <input class="ipt" id="password" name="password" value=""
				type="password" placeholder="请输入密码" value="">
		</P>
		<div id="login_tip">
			<span id="login_err">${errorMsg }</span>
		</div>
		<div
			style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
			<P style="margin: 0px 35px 20px 45px;">
				<span style="float: left;"><A
					style="color: rgb(204, 204, 204);" href="#"></A></span> <span
					style="float: right;"><A
					style="color: rgb(204, 204, 204); margin-right: 10px;" href="javascript:preregister()"></A>
					<input type="button" value="登  录" onclick="toLogin()" style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;">
				</span>
			</P>
		</div>
	</div>
</form>
<script type="text/javascript">
	$(function() {
		//得到焦点
		$("#password").focus(function() {
			$("#left_hand").animate({
				left : "150",
				top : " -38"
			}, {
				step : function() {
					if (parseInt($("#left_hand").css("left")) > 140) {
						$("#left_hand").attr("class", "left_hand");
					}
				}
			}, 2000);
			$("#right_hand").animate({
				right : "-64",
				top : "-38px"
			}, {
				step : function() {
					if (parseInt($("#right_hand").css("right")) > -70) {
						$("#right_hand").attr("class", "right_hand");
					}
				}
			}, 2000);
		});
		//失去焦点
		$("#password").blur(function() {
			$("#left_hand").attr("class", "initial_left_hand");
			$("#left_hand").attr("style", "left:100px;top:-12px;");
			$("#right_hand").attr("class", "initial_right_hand");
			$("#right_hand").attr("style", "right:-112px;top:-12px");
		});
		$("body").keydown(function(){
			if(event.keyCode=="13"){
				toLogin();
			}
		});
	});
	function toLogin(){
		var userId = $("#userId").val();
		if(userId==null||userId==""){
			layer.msg("用户名称不能为空！");
			return;
		}
		var password = $("#password").val();
		if(password==null||password==""){
			layer.msg("密码不能为空！");
			return;
		}
		$.ajax({
			url:'${pageContext.request.contextPath}/login.do',
			type:'post',
			dataType:'json',
			data:{
				userId:userId,
				password:password
			},
			async:false,
			success:function(reData){
				if(reData.success){
					window.location.href="${pageContext.request.contextPath}/main.do";
				}else{
					layer.msg(reData.message);
				}
			}
		})
	}
	
</script>
</body>
</html>