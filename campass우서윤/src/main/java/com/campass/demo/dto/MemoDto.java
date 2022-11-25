package com.campass.demo.dto;

import java.util.*;

import com.campass.demo.entity.*;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemoDto {
	@Data
	public static class Disable {
		private List<Integer> list;
	}

	@Data
	public static class Write {
		private String title;
		private String content;
		private String receiver;
		
		public Memo toEntity(String loginId) {
			return Memo.builder().title(title).content(content).receiver(receiver).sender(loginId).build();
		}
	}

}
