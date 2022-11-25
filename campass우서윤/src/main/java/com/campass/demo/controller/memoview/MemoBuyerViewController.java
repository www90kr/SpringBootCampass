package com.campass.demo.controller.memoview;

import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_BUYER")
@Controller
public class MemoBuyerViewController {
	@GetMapping("/buyer/list")
	public String 예약리스트() {
		return "buyer/list";
	}
	
	@GetMapping("/buyer/send_allcheck")
	public String 전체메시지() {
		return "buyer/send_allcheck";
	}
	@GetMapping("/buyer/memoindex")
	public String 루트페이지() {
		return "memoindex";
	}
	// 받은 메모함
	@GetMapping("/buyer/memo/receive")
	public String 수신메모() {
		return "memo/receive";
	}
	
	// 보낸 메모함
	@GetMapping("/buyer/memo/send")
	public String 송신메모() {
		return "memo/send";
	}
	
	@GetMapping("/buyer/memo/read")
	public String 메모읽기() {
		return "memo/read";
	}
}
