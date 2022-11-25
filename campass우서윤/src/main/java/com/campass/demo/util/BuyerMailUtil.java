package com.campass.demo.util;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;

import com.campass.demo.dto.Mail;

@Component
public class BuyerMailUtil {
	@Autowired
	private JavaMailSender javaMailSender;
	
	// JavaMailSender를 이용해 메일을 설정하고 보내는 메소드
	// text/html과 이메일 형식을 MIME 형식이라고 한다
	// 이 MIME을 웹에서는 ContentType이라고도 한다 -> 스프링에서는 MediaType
	private void sendMail(Mail mail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(mail.getFrom());
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setText(mail.getText(), true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// 메일 전송 실패 예외 - gmail 서버에서 발생. 우리가 처리 불가능
			e.printStackTrace();
		}	
	}
	
	

	// 임시 비밀번호 보내는 메소드
	public void sendResetPasswordMail(String from, String to, String bpassword) {
		Mail mail = Mail.builder().from(from).to(to).subject("임시비밀번호").build();
		String message = new StringBuffer("<p>임시비밀번호를 발급했습니다</p>").append("<p>임시비밀번호 :").append(bpassword).append("</p>").toString();
		sendMail(mail.setText(message));
		
	}

//	// 체크코드를 받아서 링크를 만든 다음 메일로 보내는 함수
//	// <a href='http://localhost:8087/member/check/join?checkcode=?">);
//	public void sendCheckMail(String from, String to, String checkcode) {
//		Mail mail = Mail.builder().from(from).to(to).subject("가입 확인 메일").build();
//		StringBuffer buf = new StringBuffer("<p>회원가입을 위한 안내 메일입니다</p>");
//		buf.append("<p>가입 확인을 위해 아래 링크를 클릭하세요</p>");
//		buf.append("<p>가입 확인 링크 :");
//		buf.append("<a href='http://localhost:8087/member/check/join?checkcode=");
//		buf.append(checkcode);
//		buf.append("'>클릭하세요</a></p>");
//		mail.setText(buf.toString());
//		sendMail(mail);
//	}
}