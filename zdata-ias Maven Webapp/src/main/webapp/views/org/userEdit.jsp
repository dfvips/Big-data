<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String orgId = request.getParameter("orgId");
	String id = request.getParameter("id");
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

<script
	src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
</head>
<body>
	<div class="layui-fluid">
		<div class="layui-row" style="padding: 20px 0px 10px 0px;">
			<div class="layui-col-md12">
				<form class="layui-form" action="" lay-filter="userForm" id="userForm">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="userGroupId" id="userGroupId" value="<%=orgId%>"> 
					<input type="hidden" name="softId" id="softId" value="<%=softId%>">
					<div class="layui-form-item">
						<label class="layui-form-label">登录号</label>
						<div class="layui-input-block">
							<input type="text" name="userId" lay-verify="required" placeholder="请输入用户Id" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">用户名称</label>
						<div class="layui-input-block">
							<input type="text" name="userName" placeholder="请输入用户名称"
								lay-verify="required" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">密码</label>
						<div class="layui-input-block">
							<input type="password" name="userPassword" placeholder="请输入密码"
								lay-reqtext="密码不能为空" lay-verify="required" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">手机</label>
						<div class="layui-input-block">
							<input type="tel" name="mobtele" placeholder="请输入手机"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">邮箱</label>
						<div class="layui-input-block">
							<input type="text" name="email" placeholder="请输入邮箱"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">用户角色</label>
						<div class="layui-input-block">
							 <select name="roleId" id="roleId" lay-verify="required">
							 	<option value="">请选择角色</option>
							 </select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">状态</label>
						<div class="layui-input-block">
							<input type="radio" name="state" value="1" title="有效"
								checked="checked"> <input type="radio" name="state"
								value="0" title="无效">
						</div>
					</div>
					<div class="layui-form-item" style="display:none;">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit lay-filter="*" id="saveUser">立即提交</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let id = <%=id%>;
		let softId = <%=softId%>;
		(function() {
			layui.use('form',function(){
       			var form = layui.form;
       			$.ajax({
			        url: "${pageContext.request.contextPath}/sysRole/findByAll.do",
			        type: "POST",
			        dataType: "json",
			        data: {
			        	softId: softId
			        },
			        success: function(result) {
			        	if(result.status=="success"){
			        		var data = result.rows;
			        		$.each(data,function(index,item){
					    		$('#roleId').append(new Option(item.roleName,item.id));//往下拉菜单里添加元素
					    	});
					    	form.render();
			        	}
			        }
			    });
			    if(id!=null && id!=''){
	       			$.ajax({
				        url: "${pageContext.request.contextPath}/sysUser/loadById.do",
				        type: "POST",
				        dataType: "json",
				        data: {
				        	id: id
				        },
				        success: function(result) {
				        	if(result.status=="success"){
				        		form.val('userForm',result.row);
				        	}
				        }
				    });
			    }
				form.on('submit(*)', function(data){
				    $.ajax({
				        url: "${pageContext.request.contextPath}/sysUser/save.do",
				        type: "POST",
				        dataType: "json",
				        data: data.field,
				        ansyc: false,
				        success: function(result) {
				        	if(result.status=="success"){
				        		parent.layer.closeAll();
				        		parent.layer.msg("保存成功！");
				        	}else if(result.status=="fail"){
				        		parent.layer.closeAll();
				        		parent.layer.msg(result.message);
				        	}
				        }
				  });
				  return false;
				});
       		});
		})();
		
		
		//表单提交
		function saveUserForm(){
			document.getElementById("saveUser").click();
		}
	</script>
</body>
</html>