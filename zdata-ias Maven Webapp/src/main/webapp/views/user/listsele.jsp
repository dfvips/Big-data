<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="main-content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">${modeName }</h3>
					</div>
					<div class="panel-body">
						<form action="${pageContext.request.contextPath}/user/listsele.do"
							method="post">
							<input type="hidden" name="IASSoftID" value="${IASSoftID }">
							<input type="hidden" name="IASloginUser" value="${IASloginUser }">
							<input type="hidden" name="IASFuncID" value="${IASFuncID }">
							<input type="hidden" name="formUrl" value="${formUrl }">
							<input type="hidden" name="mBusId" value="${mBusId }">
							<div class="row">
								<div class="col-md-2"></div>
								<div class="col-md-3">
									<input type="text" value="${s_User.sysUserId }"
										name="sysUserId" id="sysUserId" class="form-control"
										placeholder="请输入用户登录id">
								</div>
								<div class="col-md-3">
									<input type="text" value="${s_User.sysUserName }"
										name="sysUserName" id="sysUserName" class="form-control"
										placeholder="请输入用户名称">
								</div>
								<div class="col-md-2">
									<button type="submit" class="btn btn-default">查询</button>
								</div>
								<div class="col-md-2"></div>
							</div>
							<br>
						</form>
						<form
							action="${formUrl }"
							method="post">
							<input type="hidden" name="IASSoftID" value="${IASSoftID }">
							<input type="hidden" name="IASloginUser" value="${IASloginUser }">
							<input type="hidden" name="IASFuncID" value="${IASFuncID }">
							<input type="hidden" name="mBusId" value="${mBusId }">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="5%">序号</th>
										<th width="20%">用户id</th>
										<th>用户名</th>
										<th width="5%"><label class="fancy-checkbox"> <input
												type="checkbox" id="all"> <span></span>
										</label></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${userList }" varStatus="status">
										<tr>
											<td>${status.index+1 }</td>
											<td>${user.sysUserId }</td>
											<td>${user.sysUserName }</td>
											<td><label class="fancy-checkbox"> <input
													type="checkbox" name="sysUserId" id="sysUserId" value="${user.sysUserId }">
													<span></span>
											</label></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<br>
							<div class="row">
								<div class="col-md-2"></div>
								<div class="col-md-8">
									<button class="btn btn-primary btn-block" type="submit">确定</button>
								</div>
								<div class="col-md-2"></div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script
	src="${pageContext.request.contextPath}/Mod1/assets/vendor/jquery/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		//给全选的复选框添加事件
		$("#all").click(function() {
			// this 全选的复选框
			var userids = this.checked;
			//获取name=box的复选框 遍历输出复选框
			$("input[name=sysUserId]").each(function() {
				this.checked = userids;
			});
		});
		//给name=box的复选框绑定单击事件
		$("input[name=rowId]").click(function() {
			//获取选中复选框长度
			var length = $("input[name=sysUserId]:checked").length;
			//未选中的长度
			var len = $("input[name=sysUserId]").length;
			if (length == len) {
				$("#all").get(0).checked = true;
			} else {
				$("#all").get(0).checked = false;
			}
		});
	});
</script>
