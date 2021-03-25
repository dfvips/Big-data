<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/startic/zTree_v3/css/metroStyle/metroStyle.css">
</head>
<body style="background: #f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row" style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md12">
				<div class="layui-card" id="orgTreeDiv">
					<div class="layui-card-header">
						<span id="orgName"></span>（组织架构）
					</div>
					<div class="layui-card-body">
						<div class="layui-row">
							<div class="layui-col-xs4">
								<div id="orgTree" class="ztree"></div>
							</div>
							<div class="layui-col-xs8">
								<div class="userSearchFrom">
									检索：
									<div class="layui-inline">
										<input class="layui-input" name="searchText" id="searchText"
											autocomplete="off">
									</div>
									<button class="layui-btn" data-type="reload">搜索</button>
								</div>
								<table class="layui-hide" id="userList" lay-filter="userTable"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/html" id="userToolbar">
  		<div class="layui-btn-container">
   		 	<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="setUsers">确认</button>
  		</div>
	</script>
	<script
		src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
	var roleId = '<%=roleId%>';
	var softId = '<%=softId%>';
	(function() {
    	initzTree();//部门树
    	setCardHeight();
	})();
	
	function setCardHeight(){
		var orgTreeDiv = document.getElementById("orgTreeDiv");
		var orgTreeHeight = $(window).height() - 20;
		orgTreeDiv.style.height = orgTreeHeight+'px';
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
			      url: "${pageContext.request.contextPath}/org/findByPid.do",
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
	                $("#funcName").html(zNodes[0].name);
	                initUserTable(zNodes[0].id);//初始化用户
	            }
	        }
        });
	}
	
	//转化
    function filter(treeId, parentNode, result) {
        if(result.status=="success"){
    		return result.rows;
    	}else{
    		return null;
    	}
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
				url : '${pageContext.request.contextPath}/sysUser/queryByOrgId.do',
				request : {
					pageName : 'pageNumber',
					limitName : 'pageSize'
				},
				where : {
		            orgId: orgId
				},
				toolbar : '#userToolbar',
				height : 'full-150',
				title : '用户列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'userId',
					title : '登录号'
				}, {
					field : 'userName',
					title : '用户名称'
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
			      case 'setUsers':
			      if(data.length==0){
			      	layer.msg("请选择一个用户");
			      }else{
			      	var users = new Array();
					for(var i=0;i<data.length;i++){
						users.push(data[i].userId);
					}
					$.ajax({
				      url: "${pageContext.request.contextPath}/sysRoleInfo/saveUsers.do",
				        type: "POST",
				        dataType: "json",
				        data: {
				        	users:users,
				        	roleId:roleId
				        },
				        success: function(result) {
				        	if(result.status=="success"){
				        		//关闭弹出层
				        		parent.layer.msg("操作成功！");
				        		var index = parent.layer.getFrameIndex(window.name);
				        		parent.layer.close(index);
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