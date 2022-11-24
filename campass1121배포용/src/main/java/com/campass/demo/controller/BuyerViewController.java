package com.campass.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuyerViewController {
	
	@GetMapping("/buyer/join")//폴더이름- .html 파일이름
	public void join() {
	}
	
	/*
	@GetMapping("/apple")
	public String jjoin() {
		return "/memeber/join";
	}
	*/
	
//	@GetMapping("member/find")
//	public void find() {
//	}
	
	// ModelAndView -> String login(Model model)
	@GetMapping("/buyer/login")
	public void login(HttpSession session, Model model) {
		if(session.getAttribute("msg")!=null) {
			// 세션에 있는 데이터는 사라지지않음 , 모델에있는 데이터는 사라짐
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
	}
	
	
	

	@GetMapping("/buyer/home")
	public void home() {
	}
	
	@GetMapping("/buyer/loginsuc")
	public void loginsuc() {
		
	}
	
	@GetMapping("/buyer/read")
	public void read() {
		
	}
	
	@GetMapping("com/home")
	public void home2() {
		
	}
	
	
	@GetMapping("/buyer/findBybId")
	public void findBybId() {
		
	}
	
	@GetMapping("/buyer/resetPassword")
	public void resetPassword() {
		
	}
	
	@GetMapping("/buyer/changePassword")
	public ModelAndView changepassword(HttpSession session) {
		ModelAndView mav = new ModelAndView("/buyer/changePassword");
		if(session.getAttribute("msg")!=null) {
			mav.addObject("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		return mav;
	}
	@GetMapping("/buyer/resign")
	public void resign() {
		
	}


}
