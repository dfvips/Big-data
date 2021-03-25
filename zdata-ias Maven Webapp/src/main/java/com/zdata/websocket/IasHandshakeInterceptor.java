package com.zdata.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.zdata.constant.Constant;
import com.zdata.model.SysUser;

public class IasHandshakeInterceptor implements HandshakeInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(IasHandshakeInterceptor.class);
	
	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> map) throws Exception {
		ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
		String userId = serverHttpRequest.getServletRequest().getParameter("userId");
		HttpSession session = serverHttpRequest.getServletRequest().getSession(false);
		SysUser curUser = (SysUser) session.getAttribute(Constant.Current_User.getValue());
		//当前用户有效，继续握手，返回true
		if(curUser!=null){
			LOG.info("与当前用户Id:【"+userId+"】握手成功！");
			map.put(Constant.Current_User_Id.getValue(), userId);
			map.put(Constant.Current_User.getValue()+"_"+userId, curUser);
			return true;
		}else {
			return false;
		}
	}

}
