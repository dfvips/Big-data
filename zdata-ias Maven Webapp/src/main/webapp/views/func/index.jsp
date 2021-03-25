<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/startic/zTree_v3/css/metroStyle/metroStyle.css">
	<script src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
  </head>
  
  <body class="layui-bg-gray">
	<div class="layui-fluid" style="margin-top:20px;">
		<div class="layui-row layui-col-space10">
			<div class="layui-col-xs4">
				<div class="layui-card">
				  <div class="layui-card-header">菜单</div>
				  <div class="layui-card-body" id="funcTreeDiv" style="overflow:auto">
					    <div id="funcTree" class="ztree"></div>
				  </div>
				</div>
			</div>
			<div class="layui-col-xs8">
				<div class="layui-card">
				  <div class="layui-card-header">菜单名称</div>
				  <div class="layui-card-body" id="funcDiv" style="overflow:auto">
						<form class="layui-form" action="" lay-filter="funcForm" id="funcForm">
							<input type="hidden" name="id" id="id">
							<input type="hidden" name="funcParentId" id="funcParentId">
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">菜单Id</label>
									<div class="layui-input-inline">
										<input type="text" name="funcId" required readonly
											lay-verify="required"  lay-error="请选择菜单" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">菜单名称</label>
									<div class="layui-input-inline">
										<input type="text" name="funcName" required
											lay-verify="required" placeholder="请输入菜单名称"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">URL</label>
									<div class="layui-input-inline">
										<input type="text" name="url" required lay-verify="required"
											placeholder="请输入菜单URL" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">基本URL</label>
									<div class="layui-input-inline">
										<input type="text" name="urlBasic" required
											lay-verify="required" placeholder="请输入基本菜单URL"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">菜单图标</label>
									<div class="layui-input-inline">
										<input type="text" name="funcImg" placeholder="请输入菜单图标" value="layui-icon-list"
											autocomplete="off" class="layui-input">
									</div>
								</div>

								<div class="layui-inline">
									<label class="layui-form-label">软件param</label>
									<div class="layui-input-inline">
										<select name="softParam" lay-verify="required">
											<option value="softId">softId</option>
											<option value="IASSoftID">IASSoftID</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">用户param</label>
									<div class="layui-input-inline">
										<select name="userParam" lay-verify="required">
											<option value="userId">userId</option>
											<option value="IASloginUser">IASloginUser</option>
										</select>
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">功能param</label>
									<div class="layui-input-inline">
										<select name="funcParam" lay-verify="required">
											<option value="funcId">funcId</option>
											<option value="IASFuncID">IASFuncID</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">排序</label>
									<div class="layui-input-inline">
										<input type="text" name="funcSort" placeholder="请输入排序" value=""
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">显示</label>
									<div class="layui-input-block">
										<input type="radio" name="isView" value="1" title="是"
											checked> <input type="radio" name="isView"
											value="0" title="否">
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">类型</label>
								<div class="layui-input-block">
									<input type="radio" name="funcType" value="1" title="全URL显示">
									<input type="radio" name="funcType" value="2" title="基本URL显示"
										checked>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">加密</label>
								<div class="layui-input-block">
									<input type="radio" name="isEncrypt" value="0" title="不加密">
									<input type="radio" name="isEncrypt" value="1"
										title="用户ID加密" checked>
								</div>
							</div>
							<div class="layui-form-item layui-form-text">
								<label class="layui-form-label">菜单描述</label>
								<div class="layui-input-block">
									<textarea name="funcDes" placeholder="请输入菜单描述"
										class="layui-textarea"></textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit lay-filter="funcSubmit">立即提交</button>
									<button type="reset" class="layui-btn layui-btn-primary">重置</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		let basePath = '<%=basePath%>';
		var softId = ${softId};
		(function() {
			initFuncTree(softId);
			initForm();
			initCssAndJs();
		})();
		
		function initCssAndJs(){
			var h = $(window).height() - 120;
			document.getElementById("funcTreeDiv").style.height= h + "px";
			document.getElementById("funcDiv").style.height= h + "px";
		}
		
		function initFuncTree(softId){
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
			         showRemoveBtn: true,
			         removeTitle: "删除菜单",
			         showRenameBtn: false,
			         renameTitle: "修改菜单"
				 },
				 callback: {
					onClick: zTreeOnClick,
					beforeRemove: beforeRemove,
					beforeClick: zTreeBeforeClick
				 },
				 view: {
				      showIcon: true,
				      showLine: true,
				      dblClickExpand: false,
				      addHoverDom: addHoverDom,
				      removeHoverDom: removeHoverDom
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
		        		 //全部展开
		        		 zTreeObj.expandAll(true);
		        	}
		        }
	        });
		}
		
		function initForm(){
			layui.use('form', function(){
			  var form = layui.form;
			  //监听提交
			  form.on('submit(funcSubmit)', function(data){
			    var index = layer.load(1, {shade: [0.1,'#fff']});
			    setTimeout(function(){ 
			    	$.ajax({
			    		url:'${pageContext.request.contextPath}/sysFunc/save.do',
			    		type:'post',
			    		dataType:'json',
			    		async:false,
			    		data:data.field,
			    		success:function(reData){
			    			if(reData.status=="success"){
			    				layer.msg("操作成功");
			    				layer.close(index);
			    				initFuncTree(softId);
			    				$('#funcForm')[0].reset();
			    			}
			    		}
			    	});
			    });
			    
			    return false;
			  });
			});
		}
		
		function zTreeBeforeClick(treeId, treeNode) {
			return true;
		};
		
		//选中功能菜单
	    function zTreeOnClick(event, treeId, treeNode){
	    	var index = layer.load(1, {shade: [0.1,'#fff']});
	    	document.getElementById("funcForm").reset();
	    	setTimeout(function(){ 
	    		$.ajax({
		    		url:'${pageContext.request.contextPath}/sysFunc/findByFuncId.do',
		    		type:'post',
		    		dataType:'json',
		    		data:{
		    			funcId:treeNode.id
		    		},
		    		success:function(reData){
		    			if(reData.status=="success"){
		    			    var data = reData.row;
		    				layui.use('form', function(){
			  					var form = layui.form;
			  					form.render();
			  					form.val('funcForm',data);
			  					layer.close(index);
			  				});
		    			}
		    		}
		    	});
	    	});
	    }
	    
	    function addHoverDom(treeId, treeNode) {
		    var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
		    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0){
		    	return;
		    }
		    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' onclick='addFunc("+treeNode.id+")' title='添加菜单'></span>"; //定义添加按钮
		    sObj.after(addStr); //加载添加按钮
		}
		
		function addFunc(funcParentId){
			var index = layer.load();
			var treeObj = $.fn.zTree.getZTreeObj("funcTree");
			var selectedNode=treeObj.getNodeByParam("id", funcParentId);
			if(selectedNode!=null){
				var funcId = funcParentId + "001";
				if(selectedNode.isParent){
					var childrenLength = selectedNode.children.length+1;
					var node = (Array(3).join(0) + childrenLength).slice(-3);
					funcId = selectedNode.id + node;
				}
				
				$.ajax({
		    		url:'${pageContext.request.contextPath}/sysFunc/save.do',
		    		type:'post',
		    		dataType:'json',
		    		async:false,
		    		data:{
		    			funcId:funcId,
		    			funcParentId:funcParentId,
		    			funcName:"新菜单"
		    		},
		    		success:function(reData){
		    			if(reData.status == "success"){
		    				layer.close(index);
		    				layer.msg("操作成功！");
		    				var newNode = { 
								name: "新菜单",
								id:funcId,
								pId:funcParentId,
								children:[]
							};
		    				treeObj.addNodes(selectedNode, newNode);
		    			}else{
		    				layer.close(index);
		    				layer.msg(reData.msg);
		    			}
		    		}
		    	});
			}
		}
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
	    
		function beforeRemove(treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("funcTree");
			if(treeNode.id==softId+""){
				layer.msg("无法删除根节点!");
				return false;
			} else {
		    	layer.confirm('确认删除吗？', {
		    	  icon: 3, title:'提示',
				  btn: ['确认','取消'] //按钮
				}, function(){
					$.post("${pageContext.request.contextPath}/sysFunc/deleteByFuncId.do", {
		                funcId: treeNode.id
		            },
		            function(reData) {
		            	var dataJson = JSON.parse(reData);
		            	if (dataJson.status=="success") {
		            		layer.msg("删除成功!");
		            		treeObj.removeNode(treeNode);  
		            	} else {
		            		layer.msg(dataJson.msg);
		            	}
		            });
				}, function(){
					
				});
				return false;
		    }
		}
		
	</script>
  </body>
</html>
