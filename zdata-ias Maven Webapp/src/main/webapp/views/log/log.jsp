<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String softId = request.getParameter("softId");
	String userId = request.getParameter("userId");
	String publicKey = request.getParameter("publicKey");
	String funcId = request.getParameter("IASFuncID");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/startic/layui/css/layui.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
<script
	src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
</head>
<body style="background: #f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space5"
			style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-body">
						<div class="layui-row">
							<div class="layui-col-xs12">
								<fieldset class="layui-elem-field layui-field-title"
									style="margin-top: 20px;">
									<legend>搜索</legend>
								</fieldset>
								<div class="logSearchFrom">
									用户名称：
									<div class="layui-inline">
										<input class="layui-input" name="userName" id="userName"
											autocomplete="off">
									</div>
									ip地址：
									<div class="layui-inline">
										<input class="layui-input" name="ipAddress" id="ipAddress"
											autocomplete="off">
									</div>
									请求地址：
									<div class="layui-inline">
										<input class="layui-input" name="requestAddress" id="requestAddress"
											autocomplete="off">
									</div>
									<button class="layui-btn" data-type="reload">搜索</button>
								</div>
								<fieldset class="layui-elem-field layui-field-title"
									style="margin-top: 20px;">
									<legend>数据列表</legend>
								</fieldset>
								<div class="grid-demo grid-demo-bg1">
									<table class="layui-hide" id="logTable" lay-filter="logTable"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"
		type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/startic/js/common.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		let softId = '<%=softId%>';
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#logTable',
				url : '${pageContext.request.contextPath}/sysLog/find.do',
				request : {
					pageName : 'pageNumber',
					limitName : 'pageSize'
				},
				where : {
					softId : softId
				},
				height: "full-230",
				toolbar : true,
				title : '用户操作日志',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'userId',
					title : '用户Id',
					width : 120
				}, {
					field : 'userName',
					title : '用户名称',
					width : 120
				}, {
					field : 'ipAddress',
					width : 140,
					title : 'ip地址'
				}, {
					field : 'params',
					title : '请求参数'
				}, {
					field : 'requestAddress',
					title : '请求地址'
				}, {
					field : 'createTime',
					title : '时间',
					templet : function(d){
						var value = d.createTime;
						return fmtDateTime(value);
					}
				}] ],
				id: 'logReload',
				page : true,
				response : {
					statusCode : 200
				},
				parseData : function(res) { //将原始数据解析成 table 组件所规定的数据
					if (res.success) {
						return {
							"code" : 200, //解析接口状态
							"msg" : "", //解析提示文本
							"count" : res.total, //解析数据长度
							"data" : res.rows
						};
					}
				}
			});
			
		   var $ = layui.$, active = {
				reload : function() {
					var userName = $('#userName').val();
					var ipAddress = $('#ipAddress').val();
					var requestAddress = $('#requestAddress').val();
					//执行重载
					table.reload('logReload', {
						page : {
							curr : 1
							//重新从第 1 页开始
						},
						where : {
							userName : userName,
							ipAddress : ipAddress,
							requestAddress : requestAddress
						}
					}, 'data');
				}
			};
			$('.logSearchFrom .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	</script>
</body>
</html>