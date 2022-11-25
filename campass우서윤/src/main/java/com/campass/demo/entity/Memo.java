package com.campass.demo.entity;

import java.time.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
	private Integer mno;
	private String title;
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private LocalDateTime writeTime;
	private String sender;
	private String receiver;
	private Boolean isRead;
	private Boolean disabledBySender;
	private Boolean disabledByReceiver;
	
	public Memo(Integer mno, String title, String content, String sender, String receiver) {
		this.mno = mno;
		this.title = title;
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
	}
}
