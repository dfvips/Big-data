<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div class="layui-fluid" style="margin-top:20px;">
		<div class="layui-row">
			<div class="layui-col-xs12">
				<form class="layui-form" action="" lay-filter="softForm">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">软件Id</label>
							<div class="layui-input-inline">
								<input type="text" name="softId" required
									lay-verify="required" placeholder="软件Id" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">软件名称</label>
							<div class="layui-input-inline">
								<input type="text" name="softName" required lay-verify="required"
									placeholder="请输入软件名称" autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">版本</label>
							<div class="layui-input-inline">
								<input type="text" name="softVersion" required
									lay-verify="required" value="Ver1.0" placeholder="软件Id" autocomplete="off"
									class="layui-input">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">创建者</label>
							<div class="layui-input-inline">
								<input type="text" name="softCreatau" value="肇庆市智库网络科技有限公司" required lay-verify="required"
									placeholder="请输入创建者" autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">软件简介</label>
						<div class="layui-input-block">
							<textarea name="softIntro" placeholder="请输入软件简介"
								class="layui-textarea"></textarea>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
