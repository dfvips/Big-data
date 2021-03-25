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
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>用户id</th>
									<th width="20%">用户名</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${userList }" varStatus="status">
									<tr>
										
										<td>${user.sysUserId }</td>
										<td>${user.sysUserName }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<nav>
							<ul class="pagination">${pageCode }
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
