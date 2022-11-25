package com.campass.demo.websocket;

import java.io.*;
import java.util.*;

import org.springframework.web.socket.*;

import lombok.*;

// 사용자 아이디, WebSocketSession의 리스트
@Data
public class WebSocketUser {
	private String username;
	private List<WebSocketSession> list = new Vector<>();
	public WebSocketUser(String username, WebSocketSession session) {
		this.username = username;
		this.list.add(session);
	}
	
	public void add(WebSocketSession session) {
		this.list.add(session);
	}
	
	public void delete(WebSocketSession session) {
		this.list.remove(session);
	}
	
	public void sendMessage(String msg) {
		TextMessage message = new TextMessage(msg);
		System.out.println(message);
		list.forEach(session->{
			try {
				session.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public int getSessionCount() {
		return list.size();
	}
}