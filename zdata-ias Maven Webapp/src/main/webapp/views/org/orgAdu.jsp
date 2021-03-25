<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"";
	String id = request.getParameter("id");
	String pId = request.getParameter("pId");
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
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
	<script
		src="${pageContext.request.contextPath}/startic/js/common.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>

  </head>
  
  <body>
	<div class="layui-fluid">
		<div class="layui-row"
			style="padding: 20px 0px 10px 0px;">
			<div class="layui-col-md12">
				<form class="layui-form" action="" lay-filter="orgForm" id="orgForm">
					<input type="hidden" name="orgId" id="orgId"> 
					<input type="hidden" name="orgParentId" id="orgParentId" value="<%=pId%>"> 
					<input type="hidden" name="userId" id="userId">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">部门名称</label>
							<div class="layui-input-block" style="width: 300px;">
								<input type="text" name="orgName" required id="orgName"
									lay-verify="required" placeholder="请输入部门名称" autocomplete="off"
									class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">岗位名称</label>
							<div class="layui-input-block" style="width: 300px;">
								<input type="text" name="position" id="position"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">地址</label>
							<div class="layui-input-block" style="width: 300px;">
								<textarea placeholder="请输入内容" name="address" id="address" class="layui-textarea"></textarea>
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">排序</label>
							<div class="layui-input-block" style="width: 300px;">
								<input type="text" name="orgSort" id="orgSort"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">编号</label>
							<div class="layui-input-block" style="width: 300px;">
								<input type="text" name="cardId" id="cardId"
									autocomplete="off" class="layui-input">
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">是否显示</label>
						<div class="layui-input-block">
							<input type="radio" name="isShow" value="t" title="是" checked="">
      						<input type="radio" name="isShow" value="f" title="否">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let basePath = '<%=basePath%>';
		let serverPath = '<%=serverPath%>';
		var id = <%=id%>;
		(function() {
			initForm();
		})();
		//初始化表单
		function initForm(){
			layui.use('form', function(){
			  var form = layui.form;
			  if(id!=null){
			  	$.ajax({
			        url: "${pageContext.request.contextPath}/org/loadByOrgId.do",
			        type: "POST",
			        dataType: "json",
			        data: {
			        	orgId: id
			        },
			        success: function(result) {
			        	if(result.status=="success"){
			        		form.val('orgForm',result.row);
			        	}
			        }
			    });
			  }
			});
		}
		
		//表单保存
		function saveForm(){
			var data = $("#orgForm").serializeObject();
			$.ajax({
				url:'${pageContext.request.contextPath}/sysOrg/save.do',
				type : 'post',
				async : false,
				dataType: 'json',
				data : data,
				success : function(result){
					if(result.code==200){
						layer.msg("操作成功");
					}else if(result.code==400){
						layer.msg(result.msg);
					}
				}
			});
		}
	</script>
  </body>
</html>
