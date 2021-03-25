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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/startic/zTree_v3/css/metroStyle/metroStyle.css">

  </head>
  
  <body>
	<div class="layui-fluid">
		<div class="layui-row">
			<div class="layui-col-md12" style="margin-top: 20px;">
				<div id="funcTree" class="ztree"></div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
	<script type="text/javascript">
		let basePath = '<%=basePath%>';
		let serverPath = '<%=serverPath%>';
		var softId = ${softId};
		var treeObj;
		(function() {
			initFunczTree();//菜单树
			setSelectNodes();//设置选中节点
		})();
		
		function initFunczTree(){
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
					onClick: zTreeOnClick
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
		        async: false,
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
		
		function zTreeOnClick(e, treeId, treeNode, clickFlag) { 
			var zTree = $.fn.zTree.getZTreeObj("funcTree");
			zTree.checkNode(treeNode, !treeNode.checked, true); 
		};
		
		function setSelectNodes(){
			$.ajax({
		        url: "${pageContext.request.contextPath}/sysSoftFunc/findBySoftId.do",
		        type: "POST",
		        dataType: "json",
		        data: {
		        	softId: softId
		        },
		        async: false,
		        success: function(result) {
		        	if(result.status=="success"){
		        		var zTree = $.fn.zTree.getZTreeObj("funcTree");
		        		for(var i=0;i<result.rows.length;i++){
		        			var node = zTree.getNodeByParam("id",result.rows[i].funcId);
		        			zTree.checkNode(node, true, false);
		        		}
		        	}
		        }
		    });
		}
		
		function submitForm(){
			var zTree = $.fn.zTree.getZTreeObj("funcTree");
			var nodes = zTree.getChangeCheckedNodes();
			var funcIds = [];
			for(var i=0;i<nodes.length;i++){
				var row = nodes[i];
				funcIds[i]=row.id;
			}
			$.ajax({
		        url: "${pageContext.request.contextPath}/sysSoftFunc/saveBySoftId.do",
		        type: "POST",
		        dataType: "json",
		        data: {
		        	softId: softId,
		        	funcIds:funcIds
		        },
		        async: false,
		        success: function(result) {
		        	if(result.status=="success"){
		        		parent.layer.closeAll();
		        		parent.layer.msg("操作成功！");
		        	}else{
		        		parent.layer.closeAll();
		        		parent.layer.msg("操作失败！");
		        	}
		        }
		    });
		}
	</script>
  </body>
</html>
