package com.campass.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor	(access=AccessLevel.PRIVATE)
public class CommentDto {
	
	@Data
	public static class  Read{
		private Integer cno;
		private String cContent;
		private String username;
		@JsonFormat(pattern="yyyy-MM-dd")
		private LocalDateTime cWriteTime;
		
	}
}