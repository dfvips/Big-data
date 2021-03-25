package com.zdata.websocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.model.SysRemind;
import com.zdata.model.SysUser;
import com.zdata.service.SysRemindService;

@Component
@Service
public class IasWebSockedHandler implements WebSocketHandler{

	private static final Logger LOG = LoggerFactory.getLogger(IasWebSockedHandler.class);
	
	private static final Map<String, WebSocketSession> userSocketSessionMap;
	static{
		userSocketSessionMap = new HashMap<String, WebSocketSession>();
	}
	
	@Autowired
	private SysRemindService sysRemindService;
	
	/**
	 * 关闭连接
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
			throws Exception {
		Iterator<Map.Entry<String, WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
		String sessionId = session.getId();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = iterator.next();
			if(entry.getKey().toString().equals(sessionId)){
				iterator.remove();
			}
		}
		LOG.info("WebSocket连接关闭！移除握手会话！");
	}

	/**
	 * 连接成功后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		LOG.info("连接成功后，从WebSocketSession中获取用户信息！");
		Map<String, Object> map = session.getHandshakeAttributes();
		String userId = (String) map.get(Constant.Current_User_Id.getValue());
		SysUser curUser = (SysUser) map.get(Constant.Current_User.getValue()+"_"+userId);
		if(curUser!=null){
			SysRemind record = new SysRemind(curUser.getUserId(), Integer.parseInt(curUser.getSoftId()));
			//更新sysRemind信息
			this.sysRemindService.updateByUserId(curUser.getUserId());
			Map<String, Object> msgMap = this.sysRemindService.findByAll(record,curUser);
			session.sendMessage(new TextMessage(JSON.toJSONString(msgMap)));
		}
		userSocketSessionMap.put(session.getId(), session);
	}

	/**
	 * 处理信息
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
			throws Exception {
		LOG.info("连接成功后，WebSocket处理信息！");
		LOG.info(session+ "---->" + message + ":"+ message.getPayload().toString());
	}

	/**
	 * 传输异常
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable)
			throws Exception {
		
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
