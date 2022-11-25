package com.campass.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.campass.demo.dao.SellerDao;
import com.campass.demo.entity.Seller;


// 로그인에 성공했다면 SS가 Authentication을 생성...but
// 로그인 실패 처리 - 아이디를 request에서 꺼낸다
// 로그인 실패 횟수 증가
// 5회이상 실패면 계정 블록
// 오류메시지를 세션에 담은 다음 이동
@Component
public class SellerLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private SellerDao dao;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String username = request.getParameter("username");
		// member를 읽어오는 이유는 로그인 실패 횟수가 필요해서다
		// ??? 아이디가 틀리면 member가 없잔아?? 그래서 없으면 null로 바꾼다
		Seller seller= dao.userdataall(username).orElse(null);
		HttpSession session = request.getSession();
		
		// AuthenticationException의 구체적인 이유를 확인
		// BadCredentialsException - 아이디 또는 비밀번호가 틀렸다
		// DisabledException - 계정이 블록되었다
		if(exception instanceof BadCredentialsException) {
			String a ="로그인에 실패했습니다";
			session.setAttribute("msg", a);
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, "/seller/login");
	}		
}   








