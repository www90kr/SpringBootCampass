package com.campass.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/com")
public class ComBoardViewController {
	// 게시판 리스트
	  	@GetMapping("/board/list")
		public void boardlist() {
		}
	  	

}
