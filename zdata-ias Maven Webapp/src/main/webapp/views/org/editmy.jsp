<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/zTree_v3/css/metroStyle/metroStyle.css">
<script
	src="${pageContext.request.contextPath}/zTree_v3/js/jquery.ztree.all.js"></script>
<div class="main-content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">${modeName }</h3>
					</div>
					<div class="panel-body">
						<div class="layui-row">
							<div class="layui-col-xs4">
								<div id="orgMyzTree" class="ztree"></div>
							</div>
							<div class="layui-col-xs8">
								<form class="layui-form" action="">
									<input type="hidden" name="id" id="id">
									<input type="hidden" name="pId" id="pId">
									<input type="hidden" name="adminUserId" id="adminUserId">
									<div class="layui-form-item">
										<div class="layui-inline">
											<label class="layui-form-label" style="width: 200px;">部门名称</label>
											<div class="layui-input-inline" style="width: 300px;">
												<input type="text" name="name" required id="name"
													lay-verify="required" placeholder="请输入部门名称"
													autocomplete="off" class="layui-input">
											</div>
										</div>
									</div>
									<div class="layui-form-item">
									<div class="layui-inline">
										<label class="layui-form-label" style="width: 200px;">岗位名称</label>
										<div class="layui-input-inline" style="width: 300px;">
											<input type="text" name="position" id="position" autocomplete="off"
												class="layui-input">
										</div>
										</div>
									</div>
									<div class="layui-form-item">
									<div class="layui-inline">
										<label class="layui-form-label" style="width: 200px;">部门地址</label>
										<div class="layui-input-inline" style="width: 300px;">
											<textarea name="wPosition" id="wPosition" placeholder="请输入内容" class="layui-textarea"></textarea>
										</div>
										</div>
									</div>
									<div class="layui-form-item">
										<div class="layui-form-label" style="width: 220px;">
										</div>
										<div class="layui-input-inline">
											<button class="layui-btn" lay-submit lay-filter="submitFrom">立即提交</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
(function() {
    initMyzTree();
})();

//初始化树
function initMyzTree() {
    $.ajax({
        url: "${pageContext.request.contextPath}/org/getRootMy.do",
        type: "POST",
        dataType: "json",
        data: {
            softId: '${IASSoftID }',
            userId: '${IASloginUser}'
        },
        success: function(result) {
            var zNodes = result.orgList;
            if (result) {
                var zTreeObj = $.fn.zTree.init($("#orgMyzTree"), setting, zNodes);
                var nodes = zTreeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    zTreeObj.expandNode(nodes[i], false, true, true);
                }
            }
        }
    });
    var setting = {
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: true,
            removeTitle: "删除部门",
            showRenameBtn: false,
            renameTitle: "修改部门"
        },
        callback: {
        	beforeRemove: beforeRemove,
            onClick: zTreeOnClick
        },
        view: {
            showIcon: true,
            showLine: true,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        },
        async: {
            enable: true,
            url: "${pageContext.request.contextPath}/org/getzTree.do",
            type: "post",
            autoParam: ["id"],
            dataFilter: filter
        }
    };
    //转化
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        var nodes = childNodes.orgList;
        /* for (var i = 0,
        l = nodes.length; i < l; i++) {
            nodes[i].name = nodes[i].name.replace(/\.n/g, '.');
        } */
        return nodes;
    }
    /**添加**/
    function addHoverDom(treeId, treeNode) {
    	
        var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0){
        	return;
        }
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加部门' onfocus='this.blur();'></span>"; //定义添加按钮
        sObj.after(addStr); //加载添加按钮
        var addBtn = $("#addBtn_" + treeNode.tId);
        //绑定添加事件，并定义添加操作
        if (addBtn) addBtn.bind("click",
        function() {
        	$.ajax({
        		url:'${pageContext.request.contextPath}/org/upNode.do',
        		type:'post',
        		dataType:'json',
        		data:{
        			pId:treeNode.id,
        			adminUserId:treeNode.adminUserId
        		},
        		success:function(reData){
        			if(reData.success){
        				layer.msg("操作成功！");
        				initMyzTree();
        			}else{
        				layer.msg(reData.msg);
        				initMyzTree();
        			}
        		}
        	});
        });
    };
    //隐藏按钮
    function removeHoverDom(treeId, treeNode) {
    	$("#addBtn_"+treeNode.tId).unbind().remove();
    };
    
    /**删除**/
    function beforeRemove(treeId, treeNode) {
    	if(treeNode.pid==0 || treeNode.parentTId == null){
    		layer.msg("无法删除根节点!");
    		return false;
    	}
        if (treeNode.isParent) { //判断是否为父节点
            layer.msg("请先删除子项");
            return false;
        } else {
            var flag = false;
            layer.confirm('确定删除部门吗？', {
                btn: ['确定', '取消']
            },
            function() {
                $.post("${pageContext.request.contextPath}/org/delete.do", {
                    id: treeNode.id
                },
                function(reData) {
                	var dataJson = JSON.parse(reData);
                	if (dataJson.success) {
                		layer.msg("删除成功!");
                		flag = true;
                		initMyzTree();
                	} else {
                		layer.msg(dataJson.msg);
                		flag = false;
                	}
                });
            },
            function() {
                flag = false;
            });
            return flag;
        }
    }
    /**
     * 鼠标点击事件
     */
    function zTreeOnClick(event, treeId, treeNode) {
    	$("#id").val(treeNode.id);//主键
    	$("#name").val(treeNode.name);
    	$("#position").val(treeNode.position);//岗位
    	$("#wPosition").val(treeNode.wPosition);//地址
    	$("#adminUserId").val(treeNode.adminUserId);//操作用户id
    	$("#pId").val(treeNode.pId);//父节点
    };
}

layui.use('form', function(){
	  var form = layui.form;
	  form.on('submit(submitFrom)', function(data){
		  var id = $("#id").val();
		  if(id!=""){
			  $.post('${pageContext.request.contextPath}/org/save.do',data.field,function(res){
				  if(res.success){
					  layer.msg("操作成功！");
					  initMyzTree();
				  }else{
					  layer.msg(res.msg);
				  }
			  },'json');
		  }else{
			  layer.msg("请选择部门");
		  }
	      return false;
	  });
});
</script>
