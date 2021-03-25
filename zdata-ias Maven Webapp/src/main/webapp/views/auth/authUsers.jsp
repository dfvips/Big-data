<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String softId = request.getParameter("softId");

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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/zTree_v3/css/metroStyle/metroStyle.css">


  </head>
  
  <body style="background:#f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space5" style="padding:10px 0px 10px 0px;">
			<div class="layui-col-md9">
				<div class="layui-card">
					<div class="layui-card-header">
						<span id="orgName"></span>（组织架构）
					</div>
					<div class="layui-card-body" id="orgDiv">
						<div class="layui-row">
							<div class="layui-col-xs4">
								<div id="orgTree" class="ztree"></div>
							</div>
							<div class="layui-col-xs8">
								<div class="userSearchFrom">
									检索：
									<div class="layui-inline">
										<input class="layui-input" name="searchText" id="searchText" autocomplete="off">
									</div>
									<button class="layui-btn" data-type="reload">搜索</button>
								</div>
								<table class="layui-hide" id="userList" lay-filter="userTable"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="layui-card">
					<div class="layui-card-header">
						已选用户
					</div>
					<div class="layui-card-body" id="selectDiv">
						<form class="layui-form" action="">
							<input type="hidden" name="sysUsers" id="sysUsers">
							<input type="hidden" name="sysUsersName" id="sysUsersName">
						    <div id="selectedUsers">
						    </div>
						    <div class="layui-form-item">
							    <div class="layui-input-label">
							      <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="setUsers">保存</button>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/html" id="userToolbar">
  		<div class="layui-btn-container">
   		 	<button class="layui-btn layui-btn-sm" lay-event="selectToUser">选中</button>
  		</div>
	</script>
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/zTree_v3/js/jquery.ztree.all.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
		var softId='<%=softId%>';
		(function() {
	    	initzTree();//部门树
	    	initCardHeight();
		})();
		function initCardHeight(){
			var cardHeigth = $(window).height() - 100;
			var selectDiv = document.getElementById("selectDiv");
			var orgDiv = document.getElementById("orgDiv");
			selectDiv.style.height = cardHeigth+'px';
			orgDiv.style.height = cardHeigth+'px';
			selectDiv.style.overflow='auto';
		}
		
		function initzTree(){
			var setting = {
				 check: {
				     enable: false
				 },
				 edit: {
				     drag: {
				         isMove: false,
				         isCopy: false
				     },
				     enable: true,
				     editNameSelectAll: true,
				     showRemoveBtn: false,
				     showRenameBtn: false,
				 },
				 callback: {
				     onClick: zTreeOnClick 
				 },
				 view: {
				      showIcon: true,
				      showLine: true
				 },
				 async: {
				      enable: true,
				      url: "${pageContext.request.contextPath}/org/getOnzTree.do",
				      type: "post",
				      autoParam: ["id"],
				      dataFilter: filter
				 }
		    };
			$.ajax({
		        url: "${pageContext.request.contextPath}/org/getRoot.do",
		        type: "POST",
		        dataType: "json",
		        data: {
		        	softId: softId
		        },
		        success: function(result) {
		        	var zNodes = result.orgList;
		            if (result) {
		            	var zTreeObj = $.fn.zTree.init($("#orgTree"), setting, zNodes);
		                var nodes = zTreeObj.getNodes();
		                for (var i = 0; i < nodes.length; i++) { //设置节点展开
		                    zTreeObj.expandNode(nodes[i], true, true, true);
		                }
		                $("#orgName").html(zNodes[0].name);
		                initUserTable(zNodes[0].id);//初始化用户
		            }
		        }
	        });
		}
		
		//转化
	    function filter(treeId, parentNode, childNodes) {
	        if (!childNodes) return null;
	        var nodes = childNodes.orgList;
	        return nodes;
	    }
		
		function zTreeOnClick(event, treeId, treeNode){
	    	initUserTable(treeNode.id);
	    }
		
		function initUserTable(orgId){
	    	layui.use(['table','form'], function() {
				var table = layui.table;
				var form = layui.form;
				table.render({
					elem : '#userList',
					url : '${pageContext.request.contextPath}/user/queryByOrgId.do',
					request : {
						pageName : 'pageNumber',
						limitName : 'pageSize'
					},
					where : {
			            orgId: orgId
					},
					toolbar : '#userToolbar',
					height : 'full-155',
					title : '用户列表',
					cols : [ [ {
						type : 'numbers',
						title : '序号'
					}, {
						field : 'sysUserId',
						title : '登录号'
					}, {
						field : 'sysUserName',
						title : '用户名称'
					}, {
						field : 'sysUserGroupName',
						title : '部门'
					}, {
						type:'checkbox'
					} ] ],
					id: 'userReload',
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
								//解析数据列表
						};
					}
				});
				 var $ = layui.$, active = {
					reload : function() {
						var searchText = $('#searchText').val();
						//执行重载
						table.reload('userReload', {
							page : {
								curr : 1
								//重新从第 1 页开始
							},
							where : {
								searchText : searchText
							}
						}, 'data');
					}
				};
				
				$("body").keydown(function () {
		            if (event.keyCode == "13") { //keyCode=13是回车键
		            	var searchText = $('#searchText').val();
						//执行重载
						table.reload('userReload', {
							page : {
								curr : 1
								//重新从第 1 页开始
							},
							where : {
								searchText : searchText
							}
						}, 'data');
		            }
		        });
		        
				$('.userSearchFrom .layui-btn').on('click', function() {
					var type = $(this).data('type');
					active[type] ? active[type].call(this) : '';
				});
				
				  //监听行工具事件
				  table.on('toolbar(userTable)', function(obj){
				    var checkStatus = table.checkStatus(obj.config.id);
				    var data = checkStatus.data;
				    switch(obj.event){
				      case 'selectToUser':
				      if(data.length==0){
				      	layer.msg("请选择用户");
				      }else{
				      	var resultHtml = '<table class="layui-table">';
				      	resultHtml+='<thead><tr><th>登录号</th><th>用户名称</th></tr></thead>';
				      	var sysUsers = '';
				      	var sysUsersName = '';
				      	for(var i=0;i<data.length;i++){
				      		resultHtml+='<tr><td>'+data[i].sysUserId+'</td><td>'+data[i].sysUserName+'</td></tr>';
				      		if(i+1==data.length){
				      			sysUsers+=data[i].sysUserId;
			      				sysUsersName+=data[i].sysUserName;
				      		}else{
				      			sysUsers+=data[i].sysUserId+',';
			      				sysUsersName+=data[i].sysUserName+',';
				      		}
			      			
			      		}
			      		resultHtml+='</table>';
			      		$("#selectedUsers").html(resultHtml);
			      		$("#sysUsers").val(sysUsers);
			      		$("#sysUsersName").val(sysUsersName);
				      }
				    }
				  });
				  form.on('submit(setUsers)', function(data){
				  	if(data.field.sysUsers==""){
				  		layer.msg("请选择用户！");
				  	}else{
				  		var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
				  		parent.setSelectUsers(data.field,layerIndex);
				  	}
				  	return false;
				  });
			});
		}
	</script>
  </body>
</html>
