<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
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
<script
	src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
<style type="text/css">
div#rMenu {
	position:absolute; 
	visibility:hidden; 
	top:0; 
	background-color: #393D49;
	text-align: left;
	padding: 1px;
	border-radius:5px;
}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	color:#FFFFFF;
	list-style: none outside none;
}
div#rMenu ul li:hover{
	color:#0099FF;
}
</style>
</head>
<body style="background: #f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space5"
			style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-body">
						<div class="layui-row layui-col-space10">
							<div class="layui-col-xs4">
								<fieldset class="layui-elem-field layui-field-title"
									style="margin-top: 20px;">
									<legend>组织结构</legend>
								</fieldset>
								<div style="position:absolute; width:95%; margin-left: 15px; overflow:auto" id="orgTreeDiv">
									<div id="orgTree" class="ztree"></div>
								</div>
							</div>
							<div class="layui-col-xs8">
								<fieldset class="layui-elem-field layui-field-title"
									style="margin-top: 20px;">
									<legend>搜索</legend>
								</fieldset>
								<div class="userSearchFrom">
									检索：
									<div class="layui-inline">
										<input class="layui-input" name="searchText" id="searchText"
											autocomplete="off">
									</div>
									<button class="layui-btn" data-type="reload">搜索</button>
								</div>
								<fieldset class="layui-elem-field layui-field-title"
									style="margin-top: 20px;">
									<legend>
										<span id="legTitle"></span>
									</legend>
								</fieldset>
								<table class="layui-hide" id="userList" lay-filter="userTable"></table>
								<script type="text/html" id="userListToolbar">
  									<div class="layui-btn-container">
    									<button class="layui-btn layui-btn-sm" lay-event="createData">新建用户</button>
  									</div>
								</script>
								<script type="text/html" id="userListBar">
									<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-read"></i>详情</a>
  									<a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
  									<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
								</script>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="rMenu">
		<ul>
			<li id="m_add" onclick="addTreeNode();">增加部门</li>
			<li id="m_update" onclick="updateTreeNode();">修改部门</li>
			<li id="m_del" onclick="delTreeNode();">删除部门</li>
		</ul>
	</div>
	<div class="dropdown open" id="treeContextMenu" style="display: none;position: absolute">
	    <ul class="dropdown-menu">
	        <li><a href="javascript:;" id="toUpdateBtn">编辑</a></li>
	        <li><a href="#" id="deleteBtn" data-target="#confirmDialog" data-toggle="modal">删除</a></li>
	    </ul>
	</div>
	
	<script type="text/javascript">
	let softId = '<%=softId%>';
	var rMenu = $("#rMenu");
	(function() {
	    initzTree();
	    initCss();
	})();
	function initCss(){
		//获取Table高度
		var h = $(window).height() - 120;
		document.getElementById("orgTreeDiv").style.height= h + "px";
	}
	
	//初始化树
	function initzTree() {
		var setting = {
			 check: {
			     enable: false
			 },
			 data : {
			 	key:{
			 		name:"orgName"
			 	},
			 	simpleData:{
			 		enable: true,
				 	idKey:"orgId",
				 	pIdKey:"orgParentId"
			 	}
			 	
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
			     onClick: zTreeOnClick,
			     onRightClick: onRightClick
			 },
			 view: {
			      showIcon: true,
			      showLine: true
			 },
			 async: {
			      enable: true,
			      url: "${pageContext.request.contextPath}/sysOrg/findByPid.do",
			      type: "post",
			      autoParam: ["orgId"],
			      dataFilter: filter
			 }
	    };
	    $.ajax({
	        url: "${pageContext.request.contextPath}/sysOrg/getRoot.do",
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
	                for (var i = 0; i < nodes.length; i++) {
	                    zTreeObj.expandNode(nodes[i], true, true, true);
	                }
	                $("#legTitle").html(zNodes[0].name);
	                initUserTable(zNodes[0].orgId);//初始化用户
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
	
    /** 菜单右键事件*/
    function onRightClick(event, treeId, treeNode) {
    	var treeObj = $.fn.zTree.getZTreeObj("orgTree");
    	treeObj.selectNode(treeNode);
    	if(treeNode.isParent||treeNode.id==softId){
    		showRMenu("root", event.clientX, event.clientY);
    	}else{
    		showRMenu("node", event.clientX, event.clientY);
    	}
    }
    
	function showRMenu(type, x, y) {
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_del").hide();
		} else if (type=="node") {
			$("#m_del").show();
		}
		rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
    
    function hideRMenu(){
    	if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
    }
    
    function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
    
    
    /**
     * 鼠标点击事件
     */
    function zTreeOnClick(event, treeId, treeNode) {
    	$("#legTitle").html(treeNode.name);
    	initUserTable(treeNode.orgId);
    }
    
    //添加部门
    function addTreeNode(){
    	hideRMenu();
    	//添加部门
    	var treeObj = $.fn.zTree.getZTreeObj("orgTree");
    	var nodes = treeObj.getSelectedNodes();
    	layer.open({
		  type: 2, 
		  title: '添加部门',
		  area: ['450px', '450px'],
		  content: '${pageContext.request.contextPath}/views/org/orgAdu.jsp?pId='+nodes[0].orgId,
		  btn: ['提交', '取消'], //可以无限个按钮
		  yes: function(index, layero){
		     var myIframe = window[layero.find('iframe')[0]['name']];
	   		 myIframe.saveForm();
	   		 initzTree();
	   		 layer.close(index);
		  },
		  btn2: function(index, layero){
		  	
		  },
		  end:function(){
		  	
		  }
		});
    }
    
    //修改部门信息
    function updateTreeNode(){
    	hideRMenu();
    	var treeObj = $.fn.zTree.getZTreeObj("orgTree");
    	var nodes = treeObj.getSelectedNodes();
    	layer.open({
		  type: 2, 
		  title: '修改部门',
		  area: ['450px', '450px'],
		  content: '${pageContext.request.contextPath}/views/org/orgAdu.jsp?id='+nodes[0].id,
		  btn: ['提交', '取消'], //可以无限个按钮
		  yes: function(index, layero){
		     var myIframe = window[layero.find('iframe')[0]['name']];
	   		 myIframe.saveForm();
	   		 initzTree();
	   		 layer.close(index);
		  },
		  btn2: function(index, layero){
		  	
		  },
		  end:function(){
		  	
		  }
		});
    }
    
    //删除部门
    function delTreeNode(){
    	hideRMenu();
    	var treeObj = $.fn.zTree.getZTreeObj("orgTree");
    	var nodes = treeObj.getSelectedNodes();
    	$.ajax({
	        url: "${pageContext.request.contextPath}/org/delete.do",
	        type: "POST",
	        dataType: "json",
	        data: {
	        	id: nodes[0].id
	        },
	        success: function(result) {
	        	initzTree();
	        	if(result.success){
	        		layer.msg("操作成功！");
	        	}else{
	        		layer.msg(result.msg);
	        	}
	        }
	    });
    }
    
    function initUserTable(orgId){
    	layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#userList',
				url : '${pageContext.request.contextPath}/sysUser/queryByOrgId.do',
				request : {
					pageName : 'pageNumber',
					limitName : 'pageSize'
				},
				toolbar: '#userListToolbar', //开启头部工具栏，并为其绑定左侧模板
				where : {
		            orgId: orgId
				},
				title : '用户列表',
				height: "full-230",
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'userId',
					title : '登录号',
					width : 140
				}, {
					field : 'userName',
					title : '用户名称',
					width : 120
				}, {
					field : 'email',
					width : 140,
					title : '邮箱'
				}, {
					field : 'mobtele',
					width : 140,
					title : '手机'
				}, {
					field : 'state',
					title : '状态',
					templet : function(d) {
						if(d.state==1){
							return '<span style="color: #F581B1;">有效</span>';
						}else if(d.state==0){
							return '<span style="color: #2F4056;">无效</span>';
						}
						
					}
				},{
					title : '操作',
					fixed : 'right',
					width : 230,
					align : 'center',
					toolbar : '#userListBar'
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
			$('.userSearchFrom .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			
			//回车事件绑定
			$('.userSearchFrom .layui-input').bind('keyup', function(event) {
			　　if (event.keyCode == "13") {
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
			
			
			//监听行工具事件
			  table.on('tool(userTable)', function(obj){
			    var data = obj.data;
			    if(obj.event === 'del'){
			      layer.confirm('真的删除行么', function(index){
			         $.ajax({
	    		      url: "${pageContext.request.contextPath}/sysUser/deleteById.do",
				        type: "POST",
				        dataType: "json",
				        data: {
				        	id:data.id
				        },
				        success: function(result) {
				        	if(result.status=="success"){
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
				        		layer.msg("删除成功！");
				        	}else{
				        		layer.msg("系统错误！");
				        	}
				        }
				     });
			      });
			    } else if(obj.event === 'edit'){
			    	var orgId = obj.data.sysUserGroupId;//分组Id
			    	var id = obj.data.id;
			    	layer.open({
			    	  title: '修改用户',
			    	  type: 2,
			    	  area: ['450px', '450px'],
			    	  fixed: false, //固定
			    	  maxmin: false,
			    	  content: '${pageContext.request.contextPath}/views/org/userEdit.jsp?orgId='+orgId+'&id='+id+'&softId='+softId,
			    	  btn: ['提交', '取消'], //可以无限个按钮
					  yes: function(index, layero){
					     var myIframe = window[layero.find('iframe')[0]['name']];
				   		 myIframe.saveUserForm();
					  },
					  btn2: function(index, layero){
					  	 
					  },
					  end:function(){
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
					  },
					  cancel:function(index, layero){
					  	
					  }
			    	});
			    }
			  });
			 //头工具栏事件
			 table.on('toolbar(userTable)', function(obj) {
				if (obj.event == 'createData') { //创建用户
					var orgId = obj.config.where.orgId; //分组Id
					layer.open({
						title : '创建用户',
						type : 2,
						area : [ '450px', '450px' ],
						fixed : false, //固定
						maxmin : false,
						content : '${pageContext.request.contextPath}/views/org/userEdit.jsp?orgId=' + orgId + '&softId=' + softId,
						btn : [ '提交', '取消' ], //可以无限个按钮
						yes : function(index, layero) {
							var myIframe = window[layero.find('iframe')[0]['name']];
							myIframe.saveUserForm();
						},
						btn2 : function(index, layero) {
							
						},
						end : function() {
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
				}
			});
		});
    }
    
	</script>
</body>
</html>