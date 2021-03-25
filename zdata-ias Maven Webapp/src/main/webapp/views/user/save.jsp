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
						<form class="form-horizontal"
							action="${pageContext.request.contextPath}/user/save.do"
							method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">账户前缀：</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="preName"
										name="preName" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">数量：</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="Num"
										name="Num" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">主操作文件名：</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" id="mainf"
										name="mainf" value="mainf3.jsp">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-12">
									<input type="hidden" id="IASSoftID" name="IASSoftID" value="${IASSoftID }" />
									<button type="submit" class="btn btn-primary">保存</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>