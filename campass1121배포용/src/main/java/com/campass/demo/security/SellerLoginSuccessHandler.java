package com.campass.demo.security;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.savedrequest.*;
import org.springframework.stereotype.*;

import com.campass.demo.dao.SellerDao;

// 1. 로그인에 성공하면 로그인 실패횟수를 0으로 리셋
// 2. 입력한 비밀번호가 20자리라면 비밀번호 변경 창으로 이동
// 3. 갈 곳이 있다면 그곳으로 이동, 없다면 /로 이동
@Component
public class SellerLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private SellerDao dao;
	
	// 사용자가 가려던 목적지를 저장하는 객체
	private RequestCache cache = new HttpSessionRequestCache();
	// 리다이렉트 해주는 객체
	private RedirectStrategy rs = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	
		// Authentication 객체에 비밀번호가 들었는데 접근이 안된다
		// request에서 꺼내자
		if(request.getParameter("password").length()>=20) {
			HttpSession session = request.getSession();
			session.setAttribute("msg", "임시비밀번호로 로그인하셨습니다. 비밀번호를 변경하세요");
			rs.sendRedirect(request, response, "/seller/changePassword");
		
		} else {
			
			SavedRequest savedRequest = cache.getRequest(request, response);
			if(savedRequest!=null)
				rs.sendRedirect(request, response, savedRequest.getRedirectUrl());
			else
				rs.sendRedirect(request, response, "/seller/campingAll");	
		}
	}
	
	
}