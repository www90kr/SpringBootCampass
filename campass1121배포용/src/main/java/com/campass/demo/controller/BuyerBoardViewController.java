package com.campass.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/buyer")
public class BuyerBoardViewController {
	// 게시판 리스트
	  	@GetMapping("/board/list")
		public ModelAndView boardlist() {
	  		return new ModelAndView("/buyer/board/list");
		}
	  	

}
