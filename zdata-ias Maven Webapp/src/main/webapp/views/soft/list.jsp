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

  </head>
  
  <body>
	<div class="layui-fluid">
		<div class="layui-row">
			<div class="layui-col-md12" style="margin-top: 20px;">
				<form class="layui-form" action="" id="softSearchFrom">
   					<label class="layui-form-label">软件Id</label>
					<div class="layui-inline">
						<input class="layui-input" name="softId" id="softId"
							autocomplete="off">
					</div>
					<button class="layui-btn" lay-submit lay-filter="*">搜索</button>
				</form>
			</div>
			<div class="layui-col-md12">
				<script type="text/html" id="softToolbar">
  					<div class="layui-btn-container">
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="createData">创建软件</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="updateData">修改软件</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="deleteData">删除软件</button>
    					<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="menuData">功能菜单</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="authData">权限预设</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="orgData">组织机构</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="roleData">角色设置</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="logData">操作日志</button>
						<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="copyData">功能复用</button>  					
					</div>
				</script>
				<table class="layui-hide" id="softList" lay-filter="softTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let basePath = '<%=basePath%>';
		(function() {
			initSoftTable();
		})();
		
		function initSoftTable(){
			layui.use(['table','form'], function() {
				var table = layui.table;
				var form = layui.form;
				table.render({
					elem : '#softList',
					url : '${pageContext.request.contextPath}/sysSoft/findByAll.do',
					request: {
					    pageName: 'page',
					    limitName: 'rows' //每页数据量的参数名，默认：limit
					},
					height: "full-90",
					toolbar: '#softToolbar', //开启头部工具栏，并为其绑定左侧模板
					where : {
						
					},
					title : '软件系统列表',
					cols : [ [  {
						type: 'radio', 
						fixed: 'left'
					}, {
						type : 'numbers',
						title : '序号'
					}, {
						field : 'softId',
						title : '软件Id',
						width : 80
					}, {
						field : 'softName',
						title : '软件名称'
					}, {
						field : 'softIntro',
						title : '软件简介'
					}, {
						field : 'softCreate',
						title : '创建者',
						
					}, {
						field : 'softVersion',
						title : '版本',
						width : 100
					}, {
						field : 'createTime',
						title : '时间',
						templet : function(d){
							return timestampToTime(d.createTime);
						}
					}, {
						field : 'webUrl',
						title : '网站首页'
					}, {
						field : 'mainUrl',
						title : '系统首页'
					} ] ],
					page : true,
					response : {
						statusCode : 200
					},
					id: 'softReload',
					parseData : function(res) { //将原始数据解析成 table 组件所规定的数据
						if(res.status=="success"){
							return {
								'code':200,
								'msg':"",
								'count':res.total,
								'data':res.rows
							}
						}
					}
				});
				form.on('submit(*)', function(data) {
						table.reload('softReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data.field
						}, 'data');
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
				});
				
				//头工具栏事件
				  table.on('toolbar(softTable)', function(obj){
				    var checkStatus = table.checkStatus(obj.config.id);
				    var data = checkStatus.data;
				    switch(obj.event){
				      case 'createData':
						layer.open({
			      			type:2,
			      			area: ['800px', '370px'],//宽高
			      			title:'创建软件',
			      			maxmin:false,
			      			content:'${pageContext.request.contextPath}/soft/adu.do',
			      			btn: ['确定', '取消'],
			      			yes: function(index, layero){
			      				var myIframe = window[layero.find('iframe')[0]['name']];
								myIframe.submitForm();
				            },
				            btn2: function(){
				              
				            }
			      		});
					  break;
				      case 'menuData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['1200px', '600px'], //宽高
				      			title:data[0].softName,
				      			maxmin:false,
				      			shadeClose:true,
				      			content:'${pageContext.request.contextPath}/sysFunc/index.do?softId='+data[0].softId
				      		});
				      	}
				      break;
				      case 'roleData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['1000px', '600px'], //宽高
				      			title: '角色设置',
				      			maxmin: false,
				      			content:'${pageContext.request.contextPath}/views/role/role.jsp?softId='+data[0].softId
				      		});
				      	}
				      break;
				      case 'logData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['1000px', '600px'], //宽高
				      			title: '操作日志',
				      			maxmin: false,
				      			content:'${pageContext.request.contextPath}/views/log/log.jsp?softId='+data[0].softId
				      		});
				      	}
				      break;
				      case 'authData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['300px', '500px'], //宽高
				      			title: '预设权限',
				      			maxmin: false,
				      			shadeClose:true,
				      			content:'${pageContext.request.contextPath}/sysSoftFunc/authPreInstall.do?softId='+data[0].softId,
				      			btn: ['确定', '取消'],
				      			yes: function(index, layero){
				      				var myIframe = window[layero.find('iframe')[0]['name']];
									myIframe.submitForm();
					            },
					            btn2: function(){
					              
					            }
				      		});
				      	}
				      break;
				      case 'orgData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['1000px', '600px'], //宽高
				      			title: '组织机构',
				      			maxmin: false,
				      			content:'${pageContext.request.contextPath}/views/org/org.jsp?softId='+data[0].softId
				      		});
				      	}
				      break;
				      case 'deleteData':
				      //删除软件
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		
				      	}
				      break;
				      case 'copyData':
				      	if(data.length==0){
				      		layer.msg("请选择一条记录！");
				      	}else{
				      		layer.open({
				      			type:2,
				      			area: ['800px', '600px'], //宽高
				      			title:data[0].softName,
				      			maxmin:false,
				      			shadeClose:true,
				      			content:'${pageContext.request.contextPath}/sysFunc/copyFunc.do?softId='+data[0].softId,
				      			btn: ['确定', '取消'],
				      			yes: function(index, layero){
				      				var myIframe = window[layero.find('iframe')[0]['name']];
									myIframe.submitForm();
					            },
					            btn2: function(){
					              
					            }
				      		});
				      	}
				      break;
				    };
				  });
			});
		}
	</script>
  </body>
</html>
