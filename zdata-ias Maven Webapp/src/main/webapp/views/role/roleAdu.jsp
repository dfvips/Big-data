<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String softId = request.getParameter("softId");
	String id = request.getParameter("id");
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
			<form class="layui-form" action="" lay-filter="roleForm">
			    <input type="hidden" name="id" id="id">
				<input type="hidden" name="softId" id="softId" value="<%=softId%>">
				<div class="layui-form-item">
					<label class="layui-form-label">角色编号</label>
					<div class="layui-input-inline">
					<input type="text" name="roleId" lay-verify="required" autocomplete="off" placeholder="请输入角色编号" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">角色名称</label>
					<div class="layui-input-inline">
					<input type="text" name="roleName" lay-verify="required" autocomplete="off" placeholder="请输入角色名称" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item" style="display:none;">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" id="saveRole" lay-filter="*">立即提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
		let id = '<%=id%>';
		let softId= '<%=softId%>';
		(function() {
			layui.use('form', function(){
				  var form = layui.form;
				  if(id!="null"){
					  $.ajax({
					        url: "${pageContext.request.contextPath}/sysRole/loadById.do",
					        type: "POST",
					        dataType: "json",
					        data: {
					        	id:id
					        },
					        success: function(result) {
					        	if(result.status=="success"){
					        		var row = result.row;
					        		form.val('roleForm',{
										"roleName": row.roleName,
										"roleId": row.roleId,
										"id": row.id,
										"isPreinstall": row.isPreinstall+""
									});
					        	}
					        }
					  });
					  
				  }
				  
				  //监听提交
				  form.on('submit(*)', function(data){
					  $.ajax({
					        url: "${pageContext.request.contextPath}/sysRole/save.do",
					        type: "POST",
					        dataType: "json",
					        data: data.field,
					        success: function(result) {
					        	if(result.status=="success"){
					        		parent.layer.closeAll();
					        		parent.initRole();
					        		parent.layer.msg("操作成功！");
					        		
					        	}
				        	}
				        });
					  return false;
				  });
			});
		})();
		
		//表单提交
		function saveForm(){
			document.getElementById("saveRole").click();
		}
	</script>
</body>
</html>