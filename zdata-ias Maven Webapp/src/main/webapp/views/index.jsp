<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>首页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/startic/layui/layuiadmin/style/admin.css">

</head>

<body>
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md8">
				<div class="layui-row layui-col-space15">
					<div class="layui-col-md6">
						<div class="layui-card">
							<div class="layui-card-header">快捷导航</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-shortcut"
									lay-anim="" lay-indicator="inside" lay-arrow="none"
									style="width: 100%; height: 280px;">
									<div carousel-item="">
										<ul class="layui-row layui-col-space10 layui-this">
											<li class="layui-col-xs3"><a
												lay-href="home/homepage1.html"> <i
													class="layui-icon layui-icon-console"></i> <cite>主页一</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="home/homepage2.html"> <i
													class="layui-icon layui-icon-chart"></i> <cite>主页二</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="component/layer/list.html"> <i
													class="layui-icon layui-icon-template-1"></i> <cite>弹层</cite>
											</a></li>
											<li class="layui-col-xs3"><a layadmin-event="im"> <i
													class="layui-icon layui-icon-chat"></i> <cite>聊天</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="component/progress/index.html"> <i
													class="layui-icon layui-icon-find-fill"></i> <cite>进度条</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="app/workorder/list.html"> <i
													class="layui-icon layui-icon-survey"></i> <cite>工单</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="user/user/list.html"> <i
													class="layui-icon layui-icon-user"></i> <cite>用户</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/system/website.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>设置</cite>
											</a></li>
										</ul>
										<ul class="layui-row layui-col-space10">
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
											<li class="layui-col-xs3"><a
												lay-href="set/user/info.html"> <i
													class="layui-icon layui-icon-set"></i> <cite>我的资料</cite>
											</a></li>
										</ul>

									</div>
									<div class="layui-carousel-ind">
										<ul>
											<li class="layui-this"></li>
											<li></li>
										</ul>
									</div>
									<button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
									<button class="layui-icon layui-carousel-arrow" lay-type="add"></button>
								</div>
							</div>
						</div>
					</div>
					<div class="layui-col-md6">
						<div class="layui-card">
							<div class="layui-card-header">待办事项</div>
							<div class="layui-card-body">
								<div class="layui-carousel layadmin-carousel layadmin-backlog"
									lay-anim="" lay-indicator="inside" lay-arrow="none"
									style="width: 100%;height: 280px;">
									<div carousel-item="">
										<ul class="layui-row layui-col-space10 layui-this">
											<li class="layui-col-xs6"><a
												lay-href="app/content/comment.html"
												class="layadmin-backlog-body">
													<h3>全部任务</h3>
													<p>
														<cite id="AllTaskCount">0</cite>
													</p>
											</a></li>
											<li class="layui-col-xs6"><a
												lay-href="app/forum/list.html" class="layadmin-backlog-body">
													<h3>代办任务</h3>
													<p>
														<cite id="MyTaskCount">0</cite>
													</p>
											</a></li>
											<li class="layui-col-xs6"><a
												lay-href="app/forum/list.html" class="layadmin-backlog-body">
													<h3>共享任务</h3>
													<p>
														<cite id="ShareTaskCount">0</cite>
													</p>
											</a></li>
											<li class="layui-col-xs6"><a
												lay-href="app/forum/list.html" class="layadmin-backlog-body">
													<h3>已办任务</h3>
													<p>
														<cite id="MyTaskDoneCount">0</cite>
													</p>
											</a></li>
										</ul>
										<ul class="layui-row layui-col-space10">
											<li class="layui-col-xs6"><a
												lay-href="app/forum/list.html" class="layadmin-backlog-body">
													<h3>流程追踪</h3>
													<p>
														<cite id="MyInvolvedCount">0</cite>
													</p>
											</a></li>
										</ul>
									</div>
									<div class="layui-carousel-ind">
										<ul>
											<li class="layui-this"></li>
											<li></li>
										</ul>
									</div>
									<button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
									<button class="layui-icon layui-carousel-arrow" lay-type="add"></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-col-md4">
				<div class="layui-card">
					<div class="layui-card-header">版本信息</div>
					<div class="layui-card-body" style="height:185px;">
						<table class="layui-table">
			              <colgroup>
			                <col width="100">
			                <col>
			              </colgroup>
			              <tbody>
			                <tr>
			                  <td>当前版本</td>
			                  <td> ${soft.softVersion } <a href="#" style="padding-left: 10px;">日志</a> 
			                    <a href="javascript:;" style="padding-left: 5px;">检查更新</a>
			                  </td>
			                </tr>
			                <tr>
			                  <td>系统名称</td>
			                  <td> ${soft.softName }
			                 </td>
			                </tr>
			                <tr>
			                  <td>系统简介</td>
			                  <td>${soft.softIntro }</td>
			                </tr>
			                <tr>
			                  <td>创建者</td>
			                  <td>
			                    <div class="layui-btn-container">
			                      <a href="#" class="layui-btn">${soft.softCreate }</a>
			                    </div>
			                  </td>
			                </tr>
			              </tbody>
			            </table>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-row layui-col-space15">
			<div class="layui-col-sm6 layui-col-md3">
				<div class="layui-card">
					<div class="layui-card-header">
						访问量 <span class="layui-badge layui-bg-blue layuiadmin-badge">周</span>
					</div>
					<div class="layui-card-body layuiadmin-card-list">
						<p class="layuiadmin-big-font">9,999,666</p>
						<p>
							总计访问量 <span class="layuiadmin-span-color">88万 <i
								class="layui-inline layui-icon layui-icon-flag"></i></span>
						</p>
					</div>
				</div>
			</div>
			<div class="layui-col-sm6 layui-col-md3">
				<div class="layui-card">
					<div class="layui-card-header">
						下载 <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span>
					</div>
					<div class="layui-card-body layuiadmin-card-list">
						<p class="layuiadmin-big-font">33,555</p>
						<p>
							新下载 <span class="layuiadmin-span-color">10% <i
								class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
						</p>
					</div>
				</div>
			</div>
			<div class="layui-col-sm6 layui-col-md3">
				<div class="layui-card">
					<div class="layui-card-header">
						收入 <span class="layui-badge layui-bg-green layuiadmin-badge">年</span>
					</div>
					<div class="layui-card-body layuiadmin-card-list">

						<p class="layuiadmin-big-font">999,666</p>
						<p>
							总收入 <span class="layuiadmin-span-color">*** <i
								class="layui-inline layui-icon layui-icon-dollar"></i></span>
						</p>
					</div>
				</div>
			</div>
			<div class="layui-col-sm6 layui-col-md3">
				<div class="layui-card">
					<div class="layui-card-header">
						活跃用户 <span class="layui-badge layui-bg-orange layuiadmin-badge">月</span>
					</div>
					<div class="layui-card-body layuiadmin-card-list">

						<p class="layuiadmin-big-font">66,666</p>
						<p>
							最近一个月 <span class="layuiadmin-span-color">15% <i
								class="layui-inline layui-icon layui-icon-user"></i></span>
						</p>
					</div>
				</div>
			</div>
			<div class="layui-col-sm12">
				<div class="layui-card">
					<div class="layui-card-header">
						访问量
						<div class="layui-btn-group layuiadmin-btn-group">
							<a href="javascript:;"
								class="layui-btn layui-btn-primary layui-btn-xs">去年</a> <a
								href="javascript:;"
								class="layui-btn layui-btn-primary layui-btn-xs">今年</a>
						</div>
					</div>
					<div class="layui-card-body">
						<div class="layui-row">
							<div class="layui-col-sm8">
								<div class="layui-carousel layadmin-carousel layadmin-dataview"
									data-anim="fade" lay-filter="LAY-index-pagetwo" lay-anim="fade"
									style="width: 100%; height: 280px;">
									<div carousel-item="" id="LAY-index-pagetwo">
										<div class="layui-this" _echarts_instance_="1592807483286"
											style="-webkit-tap-highlight-color: transparent; user-select: none; background-color: rgba(0, 0, 0, 0); cursor: default;">
											<div
												style="position: relative; overflow: hidden; width: 663px; height: 332px;">
												<div data-zr-dom-id="bg" class="zr-element"
													style="position: absolute; left: 0px; top: 0px; width: 663px; height: 332px; user-select: none;"></div>
												<canvas width="663" height="332" data-zr-dom-id="0"
													class="zr-element"
													style="position: absolute; left: 0px; top: 0px; width: 663px; height: 332px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
												<canvas width="663" height="332" data-zr-dom-id="1"
													class="zr-element"
													style="position: absolute; left: 0px; top: 0px; width: 663px; height: 332px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
												<canvas width="663" height="332"
													data-zr-dom-id="_zrender_hover_" class="zr-element"
													style="position: absolute; left: 0px; top: 0px; width: 663px; height: 332px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
												<div class="echarts-tooltip zr-element"
													style="position: absolute; display: block; border-style: solid; white-space: nowrap; transition: left 0.4s ease 0s, top 0.4s ease 0s; background-color: rgba(50, 50, 50, 0.5); border-width: 0px; border-color: rgb(51, 51, 51); border-radius: 4px; color: rgb(255, 255, 255); font-family: 微软雅黑, Arial, Verdana, sans-serif; padding: 5px; left: 237px; top: 155px;">
													4月<br>访问量 : 1,000<br>下载量 : 950<br>平均访问量 :
													950
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-col-sm4">
								<div class="layuiadmin-card-list">
									<p class="layuiadmin-normal-font">月访问数</p>
									<span>同上期增长</span>
									<div class="layui-progress layui-progress-big"
										lay-showpercent="yes">
										<div class="layui-progress-bar" lay-percent="30%"
											style="width: 30%;">
											<span class="layui-progress-text">30%</span>
										</div>
									</div>
								</div>
								<div class="layuiadmin-card-list">
									<p class="layuiadmin-normal-font">月下载数</p>
									<span>同上期增长</span>
									<div class="layui-progress layui-progress-big"
										lay-showpercent="yes">
										<div class="layui-progress-bar" lay-percent="20%"
											style="width: 20%;">
											<span class="layui-progress-text">20%</span>
										</div>
									</div>
								</div>
								<div class="layuiadmin-card-list">
									<p class="layuiadmin-normal-font">月收入</p>
									<span>同上期增长</span>
									<div class="layui-progress layui-progress-big"
										lay-showpercent="yes">
										<div class="layui-progress-bar" lay-percent="25%"
											style="width: 25%;">
											<span class="layui-progress-text">25%</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">

		$(function(){
			initOaInfo();
			initJsAndCss();
		});
		function initJsAndCss(){
			layui.config({
				base : '${pageContext.request.contextPath}/startic/layui/layuiadmin/' //静态资源所在路径
			}).extend({
				index : 'lib/index' //主入口模块
			}).use([ 'index', 'console' ]);
		}
		//审批数据
		function initOaInfo(){
			$.ajax({
				url:'${pageContext.request.contextPath}/task/findTaskCount.do',
				type:'post',
				dataType:'json',
				success:function(result){
					if(result.AllTask.status=="success"){
						$("#AllTaskCount").html(result.AllTask.data);
					}
					if(result.MyInvolved.status=="success"){
						$("#MyInvolvedCount").html(result.MyInvolved.data);
					}
					if(result.MyTask.status=="success"){
						$("#MyTaskCount").html(result.MyTask.data);
					}
					if(result.MyTaskDone.status=="success"){
						$("#MyTaskDoneCount").html(result.MyTaskDone.data);
					}
					if(result.ShareTask.status=="success"){
						$("#ShareTaskCount").html(result.ShareTask.data);
					}
				}
			});
		}
	</script>
</body>
</html>
