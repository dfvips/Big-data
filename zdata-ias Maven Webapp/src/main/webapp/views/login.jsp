<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>后台登录</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/startic/images/cy.png" type="image/x-icon" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/startic/layui/layer-v3.1.1/layer/layer.js"></script>
<style>
#login {
	width: 1920px;
	height: 951px;
	background-image: url("startic/images/banner.jpg");
}
#loginForm{
	background-color: rgba(218, 218, 218, 0.35);
	height: 320px; 
	width: 310px;
	position: absolute;
	right : 300px;
	bottom: 250px;
}
#loginForm img{
	height: 34px;
	width: 34px;
}
input{
	height: 34px;
	width: 220px;
	border-radius: 5px;
	border: none;
	margin-top: -25px;
}
table{
	width: 300px;
}
.inputInfo{
	height: 60px;
	text-align: center;
}
.btn-primary{
	height: 35px;
	width: 120px;
	color: #ffffff;
	background-color: #3276b1;
	border: 0px;
	border-radius: 8px;
	margin-left:10px; 
}
.errorText{
	text-align: center;
	font-size: 15px;
}
</style>
</head>
<body class="easyui-layout">
	<div id="login">
		<form id="loginForm" name="defaultForm" method="post" class="form-horizontal">
			<table>
				<tr>
					<td style="text-align: center;font-size: 30px;padding-top:20px; color: #007AFF;height: 70px;">
						Welcome</td>
				</tr>
				<tr class="inputInfo">
					<td><img src="${pageContext.request.contextPath}/startic/images/head.png" style="padding-right: 10px"> <input style="padding-left: 10px"
						type="text" name="userId" id="userId" placeholder="账号/姓名/电话/"></td>
				</tr>
				<tr class="inputInfo">
					<td><img src="${pageContext.request.contextPath}/startic/images/lock.png" style="padding-right: 10px"> <input style="padding-left: 10px"
						type="password" name="password" id="password" placeholder="6-20数字字母组成">

					</td>
				</tr>
				<tr>
					<td>
						<div class="errorText"></div>
					</td>
				</tr>
				<tr style="height: 45px;">
					<td style="text-align: center;">
						<button type="button" id="submit" class="btn-primary" onclick="loginUser()">登&nbsp;&nbsp;&nbsp;录</button>
						<button type="reset" class="btn-primary">重&nbsp;&nbsp;&nbsp;置</button>
					</td>
				</tr>
				<tr style="height: 45px;">
					<td style="text-align: right;">
						<a href="${pageContext.request.contextPath}/douYin/login.do?clientKey=awb38n4p1c6zxoyp&responseType=code&scope=user_info,video.search&redirectUri=http://star.wlzkyun.com&state=1">抖音登录</a>
						&nbsp;&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/views/register.jsp">注册</a> 
						&nbsp;&nbsp;&nbsp; 
						<a href="#">忘记密码</a>&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<!-- end of footer -->  
<script type="text/javascript">
function loginUser(){
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
	var index = layer.load(1); //风格1的加载
	$.ajax({
		type: "post",
		url: "${pageContext.request.contextPath}/login.do",
		dataType: "json",
		data:{
			userId:userId,
			password:password
		},
		success: function(reData){
			if(reData.success){
				window.location.href="${pageContext.request.contextPath}/main.do";
			}else{
				layer.close(index);
				layer.msg(reData.message);
			}
		},
		error: function(){
			layer.msg("系统错误！");
			layer.close(index);
		}
	});
}

$(document).on('keyup', function(e) {
	if (e.keyCode === 13) {
		$("#submit").click();
	}
});
</script> 
</html>