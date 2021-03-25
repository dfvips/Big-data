<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"";
	String mainPage = (String) request.getAttribute("mainPage");
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
	<meta http-equiv="description" content="">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/startic/layer-v3.1.1/layer/layer.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath}/startic/js/common.js"></script>
    
  </head>
  
  <body>
	<jsp:include page="<%=mainPage %>" />
  </body>
</html>
