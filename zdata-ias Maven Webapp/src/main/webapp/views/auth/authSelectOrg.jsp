<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String softId = request.getParameter("softId");
	String type = request.getParameter("type");
	
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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/zTree_v3/css/metroStyle/metroStyle.css">
  </head>
  <body>
  	<div id="orgTree" class="ztree"></div>
    <script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/zTree_v3/js/jquery.ztree.all.js"></script>
  	
  	<script type="text/javascript">
  		var softId='<%=softId%>';
  		var type='<%=type%>';
  		(function() {
	    	initOrgzTree();//部门树
		})();
		
		function initOrgzTree(){
			var setting = {
				 check: {
				     enable: true,
				     chkStyle: "radio",
				     radioType: "all"
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
				     onCheck: orgOnCheck
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
				      dataFilter: orgFilter
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
		            }
		        }
	        });
		}
		function orgFilter(treeId, parentNode, childNodes){
			if (!childNodes) return null;
	        var nodes = childNodes.orgList;
	        return nodes;
		}
		
		//选中事件
		function orgOnCheck(event, treeId, treeNode){
			var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.getOrgSelect(treeNode,layerIndex,type);
		}
  	</script>
  </body>
</html>
