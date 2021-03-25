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
    
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/layui/css/layui-theme-custom.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/startic/css/register.css">
	<script src="${pageContext.request.contextPath}/startic/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/startic/layui/layui.js"></script>
  </head>
  <body>
	<div class="wrap">
        <img src="${pageContext.request.contextPath}/startic/css/images/bg.jpg" class="imgStyle">
        <div class="loginForm">
            <form class="layui-form" action="">
               <input type="hidden" name="softId" id="softId" value="<%=softId%>">
               <h2 style="margin-top: 15px;text-align:center;">注册用户</h2>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>手机号:</label>
                    </div>
                    <div class="usernameDiv">
                        <i class="layui-icon layui-icon-username adminIcon"></i>
                        <input id="mobtele" class="layui-input adminInput" type="text" name="mobtele" placeholder="输入手机号" >
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>密码:</label>
                    </div>
                    <div class="passwordDiv">
                        <i class="layui-icon layui-icon-password adminIcon"></i>
                        <input id="userPassword" class="layui-input adminInput" type="password" name="userPassword" placeholder="输入密码">
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>确认:</label>
                    </div>
                    <div class="passwordDiv">
                        <i class="layui-icon layui-icon-password adminIcon"></i>
                        <input id="fmtPassword" class="layui-input adminInput" type="password" name="fmtPassword" placeholder="再次确认密码">
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="usernameLabel">
                        <label>验证码:</label>
                    </div>
                    <div class="cardDiv">
                        <input id="loginCard" class="layui-input cardInput" type="text" name="code" placeholder="输入验证码">
                    </div>
                    <div class="codeDiv">
                        <input id="loginCode" class="layui-input codeInput" value="获取" type="button">
                    </div>
                </div>
                <div class="usernameWrapDiv">
                    <div class="submitLabel">
                        <label>已有账号？<a href="${pageContext.request.contextPath}/">点击登陆</a></label>
                    </div>
                    <div class="submitDiv">
                        <input type="submit" lay-submit="" lay-filter="registerSubmit" class="submit layui-btn layui-btn-primary" value="注册"></input>
                    </div>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        layui.use(['layer'],function () {
            var layer = layui.layer;
        })
        $(function () {
            //点击获取手机验证码
            $('#loginCode').click(function () {
            	var mobtele = $("#mobtele").val();
            	if(!isPoneAvailable(mobtele)){
            		layer.msg("请输入正确的手机号码！");
            		return;
            	}else{
            		$.ajax({
            	        url: "${pageContext.request.contextPath}/register/sendSms.do",
            	        type: "POST",
            	        dataType: "json",
            	        data: {
            	        	phone: mobtele
            	        },
            	        success: function(result) {
            	        	if(result.status=="success"){
            	        		layer.alert("手机验证码已成功发送至您的手机，请注意查收!");
            	        		document.getElementById("loginCode").disabled=true;
            	        	}else{
            	        		layer.msg(result.msg);
            	        	}
            	        }
            	    });
            	}
            });
            
            layui.use(['form'],function () {
            	var form = layui.form;
            	//监听提交
            	  form.on('submit(registerSubmit)', function(data){
            		debugger;
            		var userPassword = $("#userPassword").val();
                  	var fmtPassword = $("#fmtPassword").val();
                  	if(fmtPassword!=userPassword){
                  		layer.msg("两次输入密码不同！");
                  		return;
                  	}else{
                  		$.ajax({
                	        url: "${pageContext.request.contextPath}/register/useRegister.do",
                	        type: "POST",
                	        dataType: "json",
                	        data: data.field,
                	        success: function(result) {
                	        	//注册成功
                	        	if(result.status=="success"){
                	        		var row = result.row;
                	        		layer.open({
                	        			content: result.msg+"您的登陆账户为："+row.userId,
                	        			btn: ['确定'],
                	        			yes: function(index, layero){
                	        				window.location.href="${pageContext.request.contextPath}/";
                	        			},
                	        			cancel: function(){ 
                	        			    //右上角关闭回调
                	        			    //return false 开启该代码可禁止点击该按钮关闭
                	        			}
                	        		});
                	        	}else{
                	        		layer.msg(result.msg);
                	        	}
                	        }
                  		});
                	   
                  	}
            	    return false;
            	  });
            })
            
        });
        
        function isPoneAvailable(mobtele) {
            var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
            if (!myreg.test(mobtele)) {
                return false;
            } else {
                return true;
            }
        }
        
    </script>
	
  </body>
</html>