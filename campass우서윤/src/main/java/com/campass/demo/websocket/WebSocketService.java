package com.campass.demo.websocket;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.web.socket.*;

import com.campass.demo.dto.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

@Component
public class WebSocketService {
	private List<WebSocketUser> list = new Vector<>();
	
	// 새로운 세션이 만들어지면 -> list에서 WSUser를 찾아서 존재하지 않으면 WSUser 생성, 존재하면 세션만 추가 
	public void addWebSocketUserOrAddSession(WebSocketSession session) {
		String loginId = session.getPrincipal().getName();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getUsername().equals(loginId)) {
				list.get(i).add(session);
				return;
			}
		}
		WebSocketUser user = new WebSocketUser(loginId, session);
		list.add(user);
	}
	
	public int removeSessionOrRemoveWebSocketUser(WebSocketSession session) {
		String loginId = session.getPrincipal().getName();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getUsername().equals(loginId)) {
				if(list.get(i).getSessionCount()==1) {
					list.remove(i);
					return 1;
				}
				list.get(i).delete(session);
			}
		}
		return -1;
	}
	
	public void sendTo(String sender, String receiver, String content) {
		WebSocketResponse response = new WebSocketResponse(sender, content);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			 json = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(json);
		for(WebSocketUser user:list) {
			if(user.getUsername().equals(receiver)==true) {
				user.sendMessage(json);
				break;
			}
		}
	}

	public void sendAll(String sender, List<String> receivers, String content) {
		WebSocketResponse response = new WebSocketResponse(sender, content);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			 json = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(receivers);
		System.out.println(receivers.contains("SPRING"));
		for(WebSocketUser user:list) {
			if(receivers.contains(user.getUsername())==true) {
				user.sendMessage(json);
				break;
			}
		}
	}
}
