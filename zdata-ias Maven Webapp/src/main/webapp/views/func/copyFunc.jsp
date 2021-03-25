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
	<div class="layui-fluid" style="margin-top:10px;">
		<div class="layui-row">
			<div class="layui-col-xs12">
				<div class="layui-card">
					<div class="layui-card-header">复用操作</div>
					<div class="layui-card-body">
						<form class="layui-form" action="">
							<input type="hidden" name="selectFuncId" id="selectFuncId"> 
							<input type="hidden" name="objectFuncId" id="objectFuncId"> 
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label"> 将功能：</label>
									<div class="layui-input-inline">
										<input type="text" name="selectFuncName" id="selectFuncName" lay-verify="required"
											autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">
									<label class="layui-form-label">复制到：</label>
									<div class="layui-input-inline">
										<input type="text" name="objectFuncName" id="objectFuncName" lay-verify="required"
											autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-form-item" style="display:none">
							    <div class="layui-input-block">
							      <button type="submit" class="layui-btn" lay-submit="" lay-filter="save" id="save">立即提交</button>
							      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-row layui-col-space10">
			<div class="layui-col-xs6">
				<div class="layui-card">
					<div class="layui-card-header">系统软件</div>
					<div class="layui-card-body" id="preTreeDiv" style="overflow:auto">
						<form class="layui-form" action="">
						  <div class="layui-form-item">
						    <label class="layui-form-label">选择软件</label>
						    <div class="layui-input-block">
						       <select name="selectSoft" id="selectSoft" lay-filter="selectSoft" lay-search="">
						          <option value="">直接选择或搜索选择</option>
						       </select>
						    </div>
						  </div>
						</form>
						<div id="selectTree" class="ztree" style="margin-left:20px;"></div>
					</div>
				</div>
			</div>
			<div class="layui-col-xs6">
				<div class="layui-card">
					<div class="layui-card-header">目标软件</div>
					<div class="layui-card-body" id="afterTreeDiv" style="overflow:auto">
						 <div id="funcTree" class="ztree"></div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<script type="text/javascript">
		var softId = ${softId};
		(function() {
			initCssAndJs();
			initSysTree();
			initSoftData();
		})();
		function initCssAndJs(){
			var h = $(window).height() - 220;
			document.getElementById("preTreeDiv").style.height= h + "px";
			document.getElementById("afterTreeDiv").style.height= h + "px";
		}
		function initSoftData(){
			layui.use('form', function(){
			  var form = layui.form;
			  //监听提交
			  form.on('submit(save)', function(data){
			  	var index = layer.load(1);
			  	setTimeout(function(){
			  		$.ajax({
				        url: "${pageContext.request.contextPath}/sysFunc/saveCopy.do",
				        type: "POST",
				        dataType: "json",
				        data: data.field,
				        async: false,
				        success: function(result) {
				        	if(result.status=="success"){
				        		layer.close(index);
				        		layer.msg("操作成功！");
				        		initSysTree();
				        	}else{
				        		layer.close(index);
				        		layer.msg("系统错误！");
				        		initSysTree();
				        	}
				        }
			        });
			  	});
			    return false;
			  });
			  $.ajax({
		        url: "${pageContext.request.contextPath}/sysSoft/findAll.do",
		        type: "POST",
		        dataType: "json",
		        data: {
		        
		        },
		        success: function(result) {
		        	if(result.status=="success"){
		        		var rows = result.rows;
		        		for(var i=0;i<rows.length;i++){
		        			if(rows[i].softId!=softId){
		        				$("#selectSoft").append('<option value="'+rows[i].softId+'">'+rows[i].softId+':'+rows[i].softName+'</option>');
		        			}
		        		}
		        		form.render('select');
		        		form.on('select(selectSoft)', function(data){
		        			initSelectTree(data.value);
						}); 
		        	}
		        }
		      });
			});
		}
		
		function leftOnClick(event, treeId, treeNode) {
			$("#selectFuncName").val(treeNode.name);
			$("#selectFuncId").val(treeNode.id);
		};
		
		function initSelectTree(softId){
			var setting = {
				 check: {
				     enable: false
				 },
				 edit: {
				     enable: false
				 },
				 callback: {
				 	onClick: leftOnClick
				 },
				 view: {
				      showIcon: true,
				      showLine: true
				 }
		    };
		    var index = layer.load(1);
		    setTimeout(function(){
		    	$.ajax({
			        url: "${pageContext.request.contextPath}/sysFunc/findBySoftId.do",
			        type: "POST",
			        dataType: "json",
			        data: {
			        	softId: softId
			        },
			        success: function(result) {
			        	layer.close(index);
			     		var zNodes = result.rows;
			        	var zTreeObj = $.fn.zTree.init($("#selectTree"), setting, zNodes);
			        	zTreeObj.expandAll(true);
			     	}
			     });
		    });
		     
		}
		
		function rightOnClick(event, treeId, treeNode) {
			$("#objectFuncName").val(treeNode.name);
			$("#objectFuncId").val(treeNode.id);
		}
		
		function submitForm(){
			 $("#save").click();
		}
		
		function initSysTree(){
			var setting = {
				 check: {
				     enable: false
				 },
				 edit: {
				     enable: false
				 },
				 callback: {
				 	 onClick: rightOnClick
				 },
				 view: {
				      showIcon: true,
				      showLine: true,
				      dblClickExpand: false
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
	</script>
</body>
</html>
