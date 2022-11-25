package com.campass.demo.controller.memoview;

import org.springframework.security.access.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

//@Secured("ROLE_SELLER")
@Controller
public class MemoSellerViewController {
	@GetMapping("/seller/list")
	public String 예약리스트() {
		return "seller/list";
	}
	
	@GetMapping("/seller/send_all")
	public String 전체메시지() {
		return "seller/send_all";
	}
	@GetMapping("/seller/memoindex")
	public String 루트페이지() {
		return "memoindex";
	}
	// 받은 메모함
	@GetMapping("/seller/memo/receive")
	public String 수신메모() {
		return "memo/receive";
	}
	
	// 보낸 메모함
	@GetMapping("/seller/memo/send")
	public String 송신메모() {
		return "memo/send";
	}
	
	@GetMapping("/seller/memo/read")
	public String 메모읽기() {
		return "memo/read";
	}
}
