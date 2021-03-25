package com.zdata.websocket;

@Component
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsHandler(), "/ws").addInterceptors(new IasHandshakeInterceptor());
	}
	
	@Bean
	public WebSocketHandler wsHandler(){
		return new IasWebSockedHandler();
	}

}
