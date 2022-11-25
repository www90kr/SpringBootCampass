package com.campass.demo.controller.memo;

import java.security.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.campass.demo.dto.*;
import com.campass.demo.service.*;


//@PreAuthorize("isAuthenticated()")
@RestController
public class MemoSellerController {
	@Autowired
	private MemoService service;
	
	// 메모 보내기
	@PostMapping("/seller/memos/new")
	public ResponseEntity<Void> send(@Valid MemoDto.Write dto, BindingResult bindingResult, Principal principal) {
		service.send(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 전체 메모 보내기 -> 메모를 보낸 다음 보낸 메모 페이지로 이동
	@PostMapping("/seller/memos/all") 
	public ResponseEntity<?> sendAll(@Valid MemoDto.Write dto, BindingResult bindingResult, Principal principal) {
		service.sendAll(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 전체 메모 보내기 -> 메모를 보낸 다음 보낸 메모 페이지로 이동
	@PostMapping("/seller/memos/allcheck") 
	public ResponseEntity<?> sendAllCheck(@Valid MemoDto.Write dto, BindingResult bindingResult, Principal principal) {
		service.sendAllCheck(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 보낸 메모함
	@GetMapping("/seller/memos/send") 
	public ResponseEntity<?> sendMemos(Principal principal) {
		return ResponseEntity.ok(service.sendMemos(principal.getName()));
	}
	
	// 받은 메모함
	@GetMapping("/seller/memos/receive") 
	public ResponseEntity<?> receiveMemos(Principal principal) {
		return ResponseEntity.ok(service.receiveMemos(principal.getName()));
	}
	
	// 메모 읽기
	@GetMapping("/seller/memos") 
	public ResponseEntity<?> readMemo(Integer mno, Principal principal) {
		return ResponseEntity.ok(service.read(mno, principal.getName()));
	}
	
	// 보낸 쪽에서 메모 삭제
	@DeleteMapping("/seller/seller/memos/send") 
	public ResponseEntity<?> disabledBySender(MemoDto.Disable dto, Principal principal) {
		service.disabledBySender(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 받은 쪽에서 메모 삭제
	@DeleteMapping("/seller/memos/receive") 
	public ResponseEntity<?> disabledByReceiver(MemoDto.Disable dto, Principal principal) {
		service.disabledByReceiver(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 읽지 않은 메모의 개수를 받아온다
	@GetMapping("/seller/memos/state") 
	public ResponseEntity<?> getState(Principal principal) {
		return ResponseEntity.ok(service.getState(principal.getName()));
	}
}

