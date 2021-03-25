<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统首页</title>
	<!-- Meta tag Keywords -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="utf-8">
	<meta name="keywords" content="" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
	
</head>
<body style="background:#f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space10"
			style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md6">
				<div class="layui-card">
					<div class="layui-card-header">代办事项</div>
					<div class="layui-card-body">
					<div class="layui-row layui-col-space20">
						<div class="layui-col-md6">
						 <blockquote class="layui-elem-quote">共享任务</blockquote>
						</div>
						<div class="layui-col-md6">
					     <blockquote class="layui-elem-quote">代办任务</blockquote>
						</div>
					</div>
					<div class="layui-row layui-col-space20">
						<div class="layui-col-md6">
						 <blockquote class="layui-elem-quote">已办任务</blockquote>
						</div>
						<div class="layui-col-md6">
					     <blockquote class="layui-elem-quote">流程追踪</blockquote>
						</div>
					</div>
					</div>
				</div>
			</div>
			<div class="layui-col-md6">
				<div class="layui-card">
					<div class="layui-card-header">快速导航 <button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon"></i></button></div>
					<div class="layui-card-body">
						<div class="layui-btn-container">
				            <button type="button" class="layui-btn layui-btn-primary">按钮一</button> 
				            <button type="button" class="layui-btn layui-btn-primary">按钮二</button> 
				            <button type="button" class="layui-btn layui-btn-primary">按钮三</button> 
				        </div>
					</div>
				</div>
			</div>
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">视频中心</div>
					<div class="layui-card-body"></div>
				</div>
			</div>
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">流程统计</div>
					<div class="layui-card-body"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
</body>
</html>