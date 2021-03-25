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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/startic/zTree_v3/css/metroStyle/metroStyle.css">
	<style type="text/css">
		.layui-card-func-body {
		    position: relative;
		    padding: 10px 15px;
		    max-height: 650px;
		    line-height: 24px;
		    overflow: auto; /*  新加的 */
		}
	</style>
  </head>
  <body style="background:#f2f2f2">
	<div class="layui-fluid" >
		<div class="layui-row layui-col-space5" style="padding:10px 0px 10px 0px;">
			<div class="layui-col-md5">
				<div class="layui-card" id="orgTreeDiv">
				  <div class="layui-card-header"><span id="orgName"></span>（组织架构）</div>
				  <div class="layui-card-body">
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
				<div class="layui-card" id="funcTreeDiv">
				  <div class="layui-card-header"><span id="funcName"></span>（功能菜单）</div>
				  <div class="layui-card-body">
				  		<div id="funcTree" class="ztree"></div>
				  </div>
				</div>
			</div>
			<div class="layui-col-md4">
				<div class="layui-card" id="authFormDiv">
					<div class="layui-card-header">数据权限设置</div>
					<div class="layui-card-body">
						<div class="layui-row">
							<div class="layui-col-xs12">
								<blockquote class="layui-elem-quote">数据权限选择</blockquote>
								<blockquote class="layui-elem-quote layui-quote-nm">
									<form class="layui-form" action="">
										<input type="hidden" name="sysUsers" id="sysUsers">
										<input type="hidden" name="sysOrgs" id="sysOrgs">
										<input type="hidden" name="orgId" id="orgId">
										<input type="hidden" name="authUserIds" id="authUserIds">
										<div class="layui-form-item">
										    <label class="layui-form-label">已选用户</label>
										    <div class="layui-input-block" id="selectedUsers">
										    </div>
										</div>
										<div class="layui-form-item">
										    <label class="layui-form-label">已选功能</label>
										    <div class="layui-input-block" id="selectedFuncs">
										    </div>
										</div>
										<div class="layui-form-item">
										    <label class="layui-form-label">可见数据</label>
										    <div class="layui-input-block" id="showAuthInfo">
										    </div>
										</div>
										<div class="layui-form-item">
											<label class="layui-form-label">权限类型</label>
										    <div class="layui-input-block">
										        <input type="radio" name="authType" lay-filter="authType" value="0" title="本人">
										        <input type="radio" name="authType" lay-filter="authType" value="1" title="全部">
										        <input type="radio" name="authType" lay-filter="authType" value="2" title="本部门">
										        <input type="radio" name="authType" lay-filter="authType" value="3" title="本部门（含子部）">
										        <input type="radio" name="authType" lay-filter="authType" value="4" title="指定部门">
										        <input type="radio" name="authType" lay-filter="authType" value="5" title="指定部门（含子部）">
										        <input type="radio" name="authType" lay-filter="authType" value="6" title="指定用户群">
										    </div>
									    </div>
									    <div class="layui-form-item">
										    <div class="layui-input-block">
										      <button class="layui-btn" lay-submit lay-filter="sysAuthSubmit">立即提交</button>
										      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
										    </div>
										</div>
								    </form>
								</blockquote>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/html" id="userToolbar">
  		<div class="layui-btn-container">
   		 	<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="showAuthData">查看权限</button>
    		<button class="layui-btn layui-btn-primary layui-btn-sm" lay-event="setAuthData">设置权限</button>
  		</div>
	</script>
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/zTree_v3/js/jquery.ztree.all.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
	<script type="text/javascript">
		var softId='<%=softId%>';
		(function() {
	    	initzTree();//部门树
	    	initAuthForm();
	    	initFuncTree();//菜单树
	    	setCardHeight();
		})();
		
		function setCardHeight(){
			var orgDiv = document.getElementById("orgTreeDiv");
			var funcDiv = document.getElementById("funcTreeDiv");
			var funcTree = document.getElementById("funcTree");
			var formDiv = document.getElementById("authFormDiv");
			var h = $(window).height() - 30;
			orgDiv.style.height = h+'px';
			funcDiv.style.height = h+'px';
			formDiv.style.height = h+'px';
			var treeHeight = $(window).height() - 100;
			funcTree.style.maxHeight = treeHeight+'px';
			funcTree.style.overflow = 'auto';
			
		}
		
		function initAuthForm(){
			layui.use('form', function(){
			  var form = layui.form;
			  //监听提交
			  form.on('submit(sysAuthSubmit)', function(data){
			  	//序列号数据
			  	if(data.field.sysUsers==""){
			  		layer.msg("请选择授权用户！");
			  		return false;
			  	}
			  	if(data.field.funcId==undefined){
			  		layer.msg("请选择功能菜单！");
			  		return false;
			  	}
			  	if(data.field.authType==undefined){
			  		layer.msg("请选择权限类型！");
			  		return false;
			  	}
			  	$.ajax({
			        url: "${pageContext.request.contextPath}/sysAuth/save.do",
			        type: "POST",
			        dataType: "json",
			        data: data.field,
			        success: function(result) {
			        	if(result.status=="success"){
			        		layer.msg("操作成功！");
			        		setTimeout(function(){
			        			location.reload();
			        		},500);
			        	}
		        	}
		        });
			    return false;
			  });
			  form.on('radio(authType)', function (data) {
			  	if(data.value=="0"){
					var resultHtml='<input type="checkbox" title="本人数据" checked>';
					$("#showAuthInfo").html(resultHtml);
					form.render();
			  	}else if(data.value=="1"){
			  		var resultHtml='<input type="checkbox" title="全部数据" checked>';
					$("#showAuthInfo").html(resultHtml);
					form.render();
			  	}else if(data.value=="2"){
			  		var resultHtml='<input type="checkbox" title="本人所属部门数据" checked>';
					$("#showAuthInfo").html(resultHtml);
					form.render();
			  	}else if(data.value=="3"){
			  		var resultHtml='<input type="checkbox" title="本人所属部门(含子部门)数据" checked>';
					$("#showAuthInfo").html(resultHtml);
					form.render();
			  	}else if(data.value=="4"){
			  		layer.open({
					  type: 2, 
					  title: '选中指定部门',
					  area: ['300px', '500px'],
					  fixed: false, //固定
					  maxmin: false,
					  //type=0不包含子部门
					  content: '${pageContext.request.contextPath}/views/auth/authSelectOrg.jsp?softId='+softId+'&type=0' 
					});
			  	}else if(data.value=="5"){
			  		layer.open({
					  type: 2, 
					  title: '选中指定部门(含子部)',
					  area: ['300px', '500px'],
					  fixed: false, //固定
					  maxmin: false,
					  //type=0不包含子部门
					  content: '${pageContext.request.contextPath}/views/auth/authSelectOrg.jsp?softId='+softId+'&type=1' 
					});
			  	}else if(data.value=="6"){
			  		layer.open({
			  			type:2,
			  			title: '选择可见用户数据',
			  			area: ['1000px', '500px'],
			  			fixed: false, //固定
			  			maxmin: true,
			  			content: '${pageContext.request.contextPath}/views/auth/authUsers.jsp?softId='+softId
			  		});
			  	}
	          });
			});
		}
		function initFuncTree(){
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
					onClick: funcTreeOnClick
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
		        		 //全部展开
		        		 zTreeObj.expandAll(true);
		        	}
		        }
	        });
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
		                for (var i = 0; i < nodes.length; i++) {
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
	    
	    function setSelectUsers(field,layerIndex){
	    	layui.use('form', function() {
	    		var form = layui.form;
		    	layer.close(layerIndex);
		    	var resultHtml='';
		    	var selectUsersName = field.sysUsersName.split(",");
		    	for(var i=0;i<selectUsersName.length;i++){
		    		resultHtml += '<input type="checkbox" title="'+selectUsersName[i]+'" checked>';
		    	}
		    	$("#showAuthInfo").html(resultHtml);
		    	$("#authUserIds").val(field.sysUsers);
				form.render();
	    	});
	    }
	    
	    //弹出层回显示
	    function getOrgSelect(treeNode,layerIndex,type){
	    	layui.use('form', function() {
	    		var form = layui.form;
		    	layer.close(layerIndex);
		    	var resultHtml='';
		    	if(type=="0"){
		    		resultHtml='<input type="checkbox" title="'+treeNode.name+'" checked>';
		    	}else if(type=="1"){
		    		resultHtml='<input type="checkbox" title="'+treeNode.name+'(含子部)" checked>';
		    	}
				$("#showAuthInfo").html(resultHtml);
				$("#orgId").val(treeNode.id);
				form.render();
	    	});
	    }
	    
	    //选中功能菜单
	    function funcTreeOnClick(event, treeId, treeNode){
	    	layui.use('form', function() {
				var form = layui.form;
				if(!treeNode.isParent){
					var resultHtml='<input type="radio" name="funcId" value="'+treeNode.id+'" title="'+treeNode.name+'" checked>';
					var selectedFuncs = document.getElementById("selectedFuncs");
					selectedFuncs.innerHTML=resultHtml;
					form.render();
				}
			});
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
					height : 'full-155',
					title : '用户列表',
					cols : [ [ {
						type : 'numbers',
						title : '序号'
					}, {
						field : 'userId',
						title : '登录号',
						width : 140
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
				      case 'showAuthData':
				      if(data.length==0||data.length>1){
				      	layer.msg("请选择一个用户");
				      }else{
				      	
				      }
				      case 'setAuthData':
				      if(data.length==0){
				      	layer.msg("请选择用户");
				      }else{
				      	var resultHtml = '';
				      	var sysUsers = '';
				      	var sysOrgs = '';
			      		for(var i=0;i<data.length;i++){
			      			resultHtml+='<input type="checkbox" value="'+data[i].userId+'" title="'+data[i].userName+'" checked>';
			      			sysUsers+=data[i].userId+',';
			      			sysOrgs+=data[i].userGroupId+',';
			      		}
			      		$("#sysUsers").val(sysUsers);
			      		$("#sysOrgs").val(sysOrgs);
			      		$("#selectedUsers").html(resultHtml);
			      		form.render();
				      }
				    }
				  });
			});
		}
	</script>
  </body>
</html>
