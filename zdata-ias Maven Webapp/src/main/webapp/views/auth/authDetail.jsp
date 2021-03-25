<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"";
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
  </head>
  
  <body>
	<div class="layui-fluid">
		<div class="layui-row">
			<div class="layui-col-md12">
			</div>
		</div>
	</div>
    <script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
    <script src="${pageContext.request.contextPath}/startic/layui/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		let basePath = '<%=basePath%>';
		let serverPath = '<%=serverPath%>';
		(function() {
		
		})();
	</script>
  </body>
</html>
