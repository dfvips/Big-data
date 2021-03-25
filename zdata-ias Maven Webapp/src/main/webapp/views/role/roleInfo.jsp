<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String roleId = request.getParameter("roleId");
	String softId = request.getParameter("softId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">

</head>
<body>
	<div class="layui-fluid">
		<div class="layui-row" style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-body">
						<table class="layui-hide" id="roleInfoList"
							lay-filter="roleInfoTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/html" id="roleInfoToolbar">
  		<div class="layui-btn-container">
			<button class="layui-btn layui-btn-sm" lay-event="addRoleInfo">添加</button>
			<button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="delRoleInfo">删除</button>
  		</div>
	</script>
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
	let roleId = '<%=roleId%>';
	let softId = '<%=softId%>';
	(function() {
    	initRoleInfo();//角色
	})();
	function initRoleInfo(){
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#roleInfoList',
				url : '${pageContext.request.contextPath}/sysRoleInfo/findByRoleId.do',
				request : {
					pageName : 'page',
					limitName : 'rows'
				},
				where : {
					roleId: roleId
				},
				toolbar : '#roleInfoToolbar',
				height : 'full-60',
				title : '角色详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'userId',
					title : '账号'
				}, {
					field : 'userName',
					title : '用户名'
				}, {
					type:'checkbox'
				} ] ],
				id: 'roleInfoReload',
				page : true,
				response : {
					statusCode : 200//重新规定成功的状态码为 200，table 组件默认为 0
				},
				parseData : function(res) { //将原始数据解析成 table 组件所规定的数据
					return {
						"code" : 200, //解析接口状态
						"msg" : "", //解析提示文本
						"count" : res.total, //解析数据长度
						"data" : res.rows
					};
				}
			});
			table.on('toolbar(roleInfoTable)', function(obj){
			    var checkStatus = table.checkStatus(obj.config.id);
			    var data = checkStatus.data;
			    switch(obj.event){
			    	case 'addRoleInfo':
			    		debugger;
			    		parent.layer.open({
		    			  type: 2, 
		    			  title: '选择用户',
		    			  area: ['800px', '540px'],
		    			  fixed: false, //固定
		    			  maxmin: false,
		    			  moveOut: true,
		    			  content: '${pageContext.request.contextPath}/views/role/roleUser.jsp?softId='+softId+'&roleId='+roleId,
		    			  end:function() {
								table.reload('roleInfoReload', {
									page : {
										curr : 1 //重新从第 1 页开始
									},
									where : {
	
									}
								}, 'data');
							}
			    		});
			    	break;
			    	case 'delRoleInfo':
			    		if(data.length==0){
			    			layer.msg("请选择数据");
			    		}else{
			    			var ids = new Array();
			    			var userIds = new Array();
			    			for(var i=0;i<data.length;i++){
			    				ids.push(data[i].id);
			    				userIds.push(data[i].userId);
			    			}
			    			$.ajax({
			    		      url: "${pageContext.request.contextPath}/sysRoleInfo/delete.do",
						        type: "POST",
						        dataType: "json",
						        data: {
						        	roleId:roleId,
						        	ids:ids,
						        	userIds:userIds
						        },
						        success: function(result) {
									if (result.status == "success") {
										layer.msg("操作成功！");
										table.reload('roleInfoReload', {
											page : {
												curr : 1 //重新从第 1 页开始
											},
											where : {

											}
										}, 'data');
									}
								}
					        });
			    		}
			    		
			    	break;
			    }
			});
		});
	}
	</script>
</body>
</html>