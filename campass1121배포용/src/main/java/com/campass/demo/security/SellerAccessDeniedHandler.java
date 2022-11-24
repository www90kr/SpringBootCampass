package com.campass.demo.security;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

// 403를 처리하는 스프링 시큐리티 객체
@Component
public class SellerAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 그런데....403이 발생했을 때
		// MVC라면 에러페이지로 이동, REST면 에러메시지를 쏴준다
		
		// ajax라면...AJAX는 XMLHttpRequest라는 요청 객체를 사용한다
		// 그런데 우리는 위 객체를 한번도 못봤어 -> jQuery에서 감췄다
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			// ajax 요청인 경우 response객체를 이용해 상태 코드와 응답 메시지를 전송
			response.setStatus(HttpServletResponse.SC_CONFLICT);
	        response.setContentType("text/plain;charset=utf-8");
	        response.getWriter().print("권한없음 - 작업을 수행할 권한이 없습니다");
		} else {
			// mvc라면 redirect -> 세션에 오류 메시지를 저장하고 /로 이동시키자
			HttpSession session = request.getSession();
			session.setAttribute("msg", "작업을 수행할 권한이 없습니다");
			response.sendRedirect("/");
		}
	}
}
