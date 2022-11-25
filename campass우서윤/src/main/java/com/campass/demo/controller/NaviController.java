package com.campass.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NaviController {
	


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		if(session==null) {
			req.setAttribute("home",true);
			return "/index";
		}else {
			return "/index";
		}
		
	}
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String home() {
		return "/index";
	}
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		return "info";
	}
	@RequestMapping(value = "/booking", method = RequestMethod.GET)
	public String booking() {
		return "booking";
	}
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage() {
		return "mypage";
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomecall() {
		return "welcome";
	}
}