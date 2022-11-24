package com.campass.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.campass.demo.dto.BuyerDto;
import com.campass.demo.dto.RestResponse;
import com.campass.demo.service.BuyerService;

@Controller
@Validated
public class BuyerController {

	@Autowired
	private BuyerService service;
	
// job1. 아이디 중복 확인 - 예외상황을 if문 처리
	@GetMapping(path = "/buyer/check/id", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> checkId(String username) {
		if (service.idAvailable(username) == true) {
			return ResponseEntity.ok(new RestResponse("FAIL", "중복된 아이디입니다", null));
		}
		return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 아이디입니다", null));
	}

// job2. 이메일 중복 확인
	@GetMapping(path = "/buyer/check/email", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> checkemail(String bemail) {

		if (service.emailAvailable(bemail) == true) {
			return ResponseEntity.ok(new RestResponse("FAIL", "중복된 이메일입니다", null));
		}
		return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 이메일입니다", null));
	}

	// job3. 닉네임 중복 확인
	@GetMapping(path = "/buyer/check/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> checkNickname(@Valid String bnickname) {

		if (service.nicknameAvailable(bnickname) == true) {
			return ResponseEntity.ok(new RestResponse("FAIL", "중복된 닉네임입니다", null));
		}
		return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 닉네임입니다", null));
	}

	// job3. 연락처 중복 확인
	@GetMapping(path = "/buyer/check/tel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> checkTel(Integer btel) {

		if (service.telAvailable(btel) == true) {
			return ResponseEntity.ok(new RestResponse("FAIL", "이미 등록된 전화번호입니다", null));
		}
		return ResponseEntity.ok(new RestResponse("OK", "사용 가능한 전화번호입니다", null));
	}

	// job 4. 아이디찾기 페이지로 이동
	@GetMapping(path = "/buyer/findBybId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> findBybId(@Valid BuyerDto.FindBybId dto, BindingResult bindingResult) {
		service.findBybId(dto);
		return ResponseEntity.ok(new RestResponse("OK", dto, "/buyer/findBybId"));
	}

	// job 5. 이메일, 이름을 입력받아 아이디 출력
	@GetMapping(path = "/buyer/find/username", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> findusername(@Valid BuyerDto.FindBybId dto, BindingResult bindingResult) {
		service.findBybId(dto);
		String username = service.findBybId(dto).getUsername();
		return ResponseEntity.ok(new RestResponse("OK", username, null));
	}

	// job 7. 아이디와 이메일을 입력받아 임시비밀번호를 이메일로 보낸다
	@PatchMapping(path = "/buyer/reset/password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> resetPassword(@Valid BuyerDto.ResetPassword dto, BindingResult bindingResult) {
		System.out.println(dto);
		service.resetPassword(dto);
		return ResponseEntity.ok(new RestResponse("OK", "이메일로 임시비밀번호가 전송되었습니다", null));
	}

	// job 8. 비밀번호 변경
	@PatchMapping(path = "/buyer/new/password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> changePassword(@Valid BuyerDto.ChangePassword dto, BindingResult bindingResult,
			Principal principal) {
		service.changePassword(dto, principal.getName());
		return ResponseEntity.ok(new RestResponse("OK", "비밀번호가 변경되었습니다", null));
	}

	// 회원정보 출력
	@GetMapping(value = "/buyer/readbuyer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> readBuyer(Principal principal) {
		BuyerDto.Read dto = service.readBuyer(principal.getName()).get();
		return ResponseEntity.ok(new RestResponse("계정 정보", dto, null));
	}

	// 정보 수정
	@PutMapping(value = "/buyer/update")
	public ResponseEntity<RestResponse> update(BuyerDto.Update dto, Principal principal) {
		System.out.println(dto);
		service.updateBuyer(dto, principal.getName());
		return ResponseEntity.ok(new RestResponse("계정 정보 수정", service.readBuyer(principal.getName()).get(), null));
	}

//회원가입
	@PostMapping(value = "/buyer/new", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> join(BuyerDto.Join dto) {
		service.join(dto);
		return ResponseEntity.ok(new RestResponse("OK", "가입 성공", "/buyer/login"));
	}

//내정보 읽기
	@GetMapping(value = "/buyer/readinfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> read(String username) {
		BuyerDto.Read dto = service.read(username);
		return ResponseEntity.ok(new RestResponse("OK", dto, "/buyer/read"));
	}

// job 10. 회원 탈퇴
// 스프링 시큐리티로 로그아웃을 시킨다 - 이때 Authentication 객체 필요
// Authentication - 인증 정보를 담은 객체
// Principal - 로그인 아이디를 담고 있는 객체
//           - 비로그인일 때 Authentication은 anonymousUser란 아이디를 가진다
	@DeleteMapping(path = "/buyer/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> resign(SecurityContextLogoutHandler handler, HttpServletRequest req,
			HttpServletResponse res, Authentication authentication) {
		service.resign(authentication.getName());
		handler.logout(req, res, authentication);
		return ResponseEntity.ok(new RestResponse("OK", "회원 정보를 삭제했습니다", "/buyer/home"));
	}

}