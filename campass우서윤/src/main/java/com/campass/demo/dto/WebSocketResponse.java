package com.campass.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class WebSocketResponse {
	private String sender;
	private String content;
}
