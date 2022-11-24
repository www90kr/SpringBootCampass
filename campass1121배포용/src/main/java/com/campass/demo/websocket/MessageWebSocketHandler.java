package com.campass.demo.websocket;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {
	@Autowired
	private WebSocketService service;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		service.addWebSocketUserOrAddSession(session);
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		service.removeSessionOrRemoveWebSocketUser(session);
	}
}
