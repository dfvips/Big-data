<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/startic/zTree_v3/css/metroStyle/metroStyle.css">
</head>
<body style="background: #f2f2f2">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space5"
			style="padding: 10px 0px 10px 0px;">
			<div class="layui-col-md8">
				<div class="layui-card" id="roleDiv">
					<div class="layui-card-header">系统角色</div>
					<div class="layui-card-body">
						<table class="layui-hide" id="roleList" lay-filter="roleTable"></table>
					</div>
				</div>
			</div>
			<div class="layui-col-md4">
				<div class="layui-card" id="funcDiv">
					<div class="layui-card-header">授权（<span id="roleName"></span>）
					</div>
					<div class="layui-card-body">
						<div id="funcTree" class="ztree">
						</div>
						<form action="" class="layui-form">
							<input type="hidden" name="roleId" id="roleId">
							<button lay-submit="" lay-filter="saveAuth" class="layui-btn layui-btn-fluid">确定</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/html" id="roleToolbar">
  		<div class="layui-btn-container">
			<button class="layui-btn layui-btn-sm" lay-event="addRole"><i class="layui-icon layui-icon-add-1"></i>添加</button>
			<button class="layui-btn layui-btn-normal layui-btn-sm" lay-event="upRole"><i class="layui-icon layui-icon-edit"></i>修改</button>
			<button type="button" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="delRole"><i class="layui-icon layui-icon-edit"></i>删除</button>
   		 	<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="showInfo"><i class="layui-icon layui-icon-user"></i>详情</button>
    		<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="setAuth"><i class="layui-icon layui-icon-set"></i>授权</button>
  		</div>
	</script>
	<script
		src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
		let softId = '<%=softId%>';
		(function() {
	    	initRole();//角色
	    	initFuncTree();//菜单树
	    	setCardHeight();
	    	saveAuth();
		})();
		
		function saveAuth(){
			layui.use('form', function(){
	    		var form = layui.form;
	    		form.on('submit(saveAuth)', function(data){
	    			if(data.field.roleId==""){
	    				layer.msg("未选中授权角色！");
	    			}else{
	    				var id = data.field.roleId;
	    				var treeObj = $.fn.zTree.getZTreeObj("funcTree");
	    				var nodes = treeObj.getCheckedNodes(true);
	    				var funcIds = new Array();
	    				for(var i=0;i<nodes.length;i++){
	    					funcIds.push(nodes[i].id);
	    				}
	    				$.ajax({
	    				      url: "${pageContext.request.contextPath}/sysRole/setAuth.do",
	    				        type: "POST",
	    				        dataType: "json",
	    				        data: {
	    				        	funcIds:funcIds,
	    				        	id:id
	    				        },
	    				        success: function(result) {
	    				        	if(result.status=="success"){
	    				        		layer.msg("操作成功！");
	    				        		setTimeout(function(){ 
	    				        			$("#roleName").html("");
		    				        		$("#roleId").val("");
		    				        		var treeObj = $.fn.zTree.getZTreeObj("funcTree"); //获得树
							        		treeObj.checkAllNodes(false);
	    				        		}, 500);
	    				        	}
	    				        }
	    				});
	    			}
	    		    return false;
	    		});
	    	});
		}
		
		function setCardHeight(){
			var funcDiv = document.getElementById("funcDiv");
			var roleDiv = document.getElementById("roleDiv");
			var funcTree = document.getElementById("funcTree");
			var h = $(window).height() - 30;
			funcDiv.style.height = h+'px';
			roleDiv.style.height = h+'px';
			var treeHeight = $(window).height() - 140;
			funcTree.style.maxHeight = treeHeight+'px';
			funcTree.style.overflow = 'auto';
		}
		function initFuncTree(){
			var setting = {
				 check: {
				     enable: true,
				     chkStyle: "checkbox"
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
					 onClick:ztreeOnClick,
					 onAsyncSuccess:onAsyncSuccess
				 },
				 view: {
				      showIcon: true,
				      showLine: true
				 }
		    };
		    $.ajax({
		        url: "${pageContext.request.contextPath}/sysFunc/findBySoftId.do",
		        type: "POST",
		        dataType: "json",
		        data: {
		        	softId: softId
		        },
		        success: function(result) {
		        	if(result.status=="success"){
		        		 var zNodes = result.rows;
		        		 var zTreeObj = $.fn.zTree.init($("#funcTree"), setting, zNodes);
		                 var nodes = zTreeObj.getNodes();
		                 for (var i = 0; i < nodes.length; i++) { //设置节点展开
		                     zTreeObj.expandNode(nodes[i], true, true, true);
		                 }
		        	}
		        }
	        });
		}
		
		
		//单击选中前面复选框 
		function ztreeOnClick(event, treeId, treeNode){
			var treeObj = $.fn.zTree.getZTreeObj("funcTree");
			treeObj.checkNode(treeNode, !treeNode.checked, true);
		}
		
		function funcFilter(treeId, parentNode, childNodes){
			if (!childNodes) return null;
	        var nodes = childNodes.rows;
	        return nodes;
		}
		
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			var treeObj = $.fn.zTree.getZTreeObj("funcTree");
			var childNodes = treeNode.children;
			for(var i=0;i<childNodes.length; i++){
				if(childNodes[i].isParent){
					treeObj.expandNode(childNodes[i], true, true, true);
				}
	        }
		}
		
		function initRole(){
			layui.use('table', function() {
				var table = layui.table;
				table.render({
					elem : '#roleList',
					url : '${pageContext.request.contextPath}/sysRole/findByAll.do',
					request : {
						pageName : 'page',
						limitName : 'rows'
					},
					where : {
						softId: softId
					},
					toolbar : '#roleToolbar',
					height : 'full-120',
					title : '角色列表',
					cols : [ [ {
						type : 'numbers',
						title : '序号'
					}, {
						field : 'roleId',
						title : '角色ID'
					}, {
						field : 'roleName',
						title : '角色名称'
					}, {
						type:'radio'
					} ] ],
					id: 'roleReload',
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
			   //监听行工具事件
			  table.on('toolbar(roleTable)', function(obj){
			    var checkStatus = table.checkStatus(obj.config.id);
			    var data = checkStatus.data;
			    switch(obj.event){
			      case 'addRole':
			    	  layer.open({
						  type: 2, 
						  title: '添加角色',
						  area: ['450px', '250px'],
						  fixed: false, //固定
						  maxmin: false,
						  content: '${pageContext.request.contextPath}/views/role/roleAdu.jsp?softId='+softId,
						  btn: ['提交', '取消'], //可以无限个按钮
						  yes: function(index, layero){
						     var myIframe = window[layero.find('iframe')[0]['name']];
					   		 myIframe.saveForm();
						  },
						  btn2: function(index, layero){
						  	
						  },
						  end:function(){
						  	
						  }
						});
			      break;
			      case 'upRole':
			    	  if(data.length==0){
					      layer.msg("请选择角色");
					  }else{
						  layer.open({
							  type: 2, 
							  title: '修改角色',
							  area: ['450px', '250px'],
							  fixed: false, //固定
							  maxmin: false,
							  content: '${pageContext.request.contextPath}/views/role/roleAdu.jsp?softId='+softId+'&id='+data[0].id,
							  btn: ['提交', '取消'], //可以无限个按钮
							  yes: function(index, layero){
							     var myIframe = window[layero.find('iframe')[0]['name']];
						   		 myIframe.saveForm();
							  },
							  btn2: function(index, layero){
							  	
							  },
							  end:function(){
							  	
							  }
						   });
					  }
			      break;
			      case 'showInfo':
			      if(data.length==0){
			      	layer.msg("请选择角色");
			      }else{
			    	  layer.open({
						  type: 2, 
						  title: '角色详情',
						  area: ['450px', '540px'],
						  fixed: false, //固定
						  maxmin: false,
						  content: '${pageContext.request.contextPath}/views/role/roleInfo.jsp?roleId='+data[0].id+'&softId='+data[0].softId
					  });
			      }
			      break;
			      case 'setAuth':
			      if(data.length==0){
			      	layer.msg("请选择角色");
			      }else{
			    	  $("#roleId").val(data[0].id);
			    	  $("#roleName").html(data[0].roleName);
			    	  var treeObj = $.fn.zTree.getZTreeObj("funcTree"); //获得树
			    	  treeObj.checkAllNodes(false);
			    	  $.ajax({
		    		      url: "${pageContext.request.contextPath}/sysRole/loadById.do",
					        type: "POST",
					        dataType: "json",
					        data: {
					        	id:data[0].id
					        },
					        success: function(result) {
					        	if(result.status=="success"){
					        		if(result.row.roleFuncList!=null){
					        			var rows = result.row.roleFuncList;
						        		for(var i=0;i<rows.length;i++){
						        			var node = treeObj.getNodeByParam("id",rows[i].funcId);
						        			if(node!=null){
						        				node.checked = true; 
						        				treeObj.updateNode(node);
						        				//注：设置checked属性之后，一定要更新该节点，否则会出现只有鼠标滑过的时候节点才被选中的情况
						        			}
						        		}
					        		}
						        	
					        	}
					        }
			    	  });
			      }
			      break;
			      case 'delRole':
		    	  if(data.length==0){
			      	layer.msg("请选择角色");
			      }else{
			    	layer.confirm('确定删除角色吗？', {
			    		  btn: ['确定', '取消'] //可以无限个按钮
		    		}, function(index, layero){
		    			$.ajax({
		    		      url: "${pageContext.request.contextPath}/sysRole/delete.do",
					        type: "POST",
					        dataType: "json",
					        data: {
					        	id:data[0].id
					        },
					        success: function(result) {
					        	if(result.status=="success"){
					        		layer.msg("操作成功！");
					        		  table.reload('roleReload', {
								        page: {
								          curr: 1 //重新从第 1 页开始
								        }
								        ,where: {
								        	
								        }
								      }, 'data');
					        	}
				        	}
				        });
		    		}, function(index){
		    			
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