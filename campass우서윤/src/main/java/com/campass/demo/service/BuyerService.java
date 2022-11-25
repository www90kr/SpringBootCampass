package com.campass.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.campass.demo.dao.*;
import com.campass.demo.dto.*;
import com.campass.demo.dto.BuyerDto.FindBybId;
import com.campass.demo.dto.BuyerDto.Read;
import com.campass.demo.dto.BuyerDto.Update;
import com.campass.demo.entity.*;
import com.campass.demo.exception.*;
import com.campass.demo.service.*;
import com.campass.demo.util.BuyerMailUtil;

import lombok.Data;

@Data
@Service
public class BuyerService {
	@Autowired
	private BuyerDao dao;
	@Autowired
	private BuyerMailUtil mailUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean idAvailable(String username) {
		return (dao.existsBybId(username));
	}

	// 이메일 사용여부 확인
	public Boolean emailAvailable(String bemail) {
		return (dao.existsBybEmail(bemail));
	}

	// 닉네임 사용여부 확인
	public Boolean nicknameAvailable(String bnickname) {
		return (dao.existsBybNickname(bnickname));
	}

	// 연락처 사용여부 확인
	public Boolean telAvailable(Integer btel) {
		return (dao.existsBybTel(btel));
	}

	// 회원 가입
	public void join(BuyerDto.Join dto) {
		if (dao.existsBybId(dto.getUsername()))
			throw new JobFailException("사용중인 아이디입니다");
		if (dao.existsBybEmail(dto.getBemail()))
			throw new JobFailException("사용중인 이메일입니다");
		if (dao.existsBybNickname(dto.getBnickname()))
			throw new JobFailException("사용중인 닉네임입니다");

		Buyer buyer = dto.toEntity();
		// MultipartFile profile = dto.getProfile();

//		String profileName = "default.jpg";
//		// 프로필 사진이 있으면 저장하고 변경
//		if (profile != null && profile.isEmpty() == false) {
//			// 폴더명, 파일명으로 빈 파일을 생성한다
//			File file = new File(profileFolder, profile.getOriginalFilename());
//			try {
//				profile.transferTo(file); // ?
//				profileName = profile.getOriginalFilename();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(buyer.getBpassword());
		buyer.addJoinInfo(encodedPassword);
		dao.save(buyer);
	}

	// 아이디 찾기
	public Buyer findBybId(BuyerDto.FindBybId dto) {
		Buyer buyer = dao.findBybId(dto.getBemail(), dto.getBname()).orElseThrow(() -> new MemberNotFoundException());
		return buyer;
		// mailUtil.sendFindIdMail("admin@icia.com", dto.getEmail(), member.getId());

	}

	// -> 아이디찾기 메일로 전송?
	// 이메일, 아이디 입력 -> 임시비밀번호 전송
	// 비밀번호 리셋
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 이메일을 확인 -> 틀리면 MemberNotFoundException
	// 20글자 임시비밀번호 생성 -> 암호화 -> 비번 변경 -> 메일 발송
	public void resetPassword(BuyerDto.ResetPassword dto) {
		Buyer buyer = dao.read(dto.getUsername()).orElseThrow(() -> new MemberNotFoundException());
		if (buyer.getBemail().equals(dto.getBemail()) == false)
			throw new MemberNotFoundException();
		String newPassword = RandomStringUtils.randomAlphanumeric(20);
		dao.resetpw(Buyer.builder().username(dto.getUsername()).bpassword(passwordEncoder.encode(newPassword)).build());
		mailUtil.sendResetPasswordMail("admin@icia.com", dto.getBemail(), newPassword);

	}

	// 비밀번호 변경
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 비밀번호를 맞춰본다 -> passwordEncoder.matches() -> 실패하면 예외처리
	// 새비밀번호를 암호화 후 저장
	public void changePassword(BuyerDto.ChangePassword dto, String username) {
		Buyer buyer = dao.read(username).orElseThrow(() -> new MemberNotFoundException());
		String encodedPassword = buyer.getBpassword();
		if (passwordEncoder.matches(dto.getBpassword(), encodedPassword) == false)
			throw new JobFailException("비밀번호를 변경하지 못했습니다");
		dao.changepw(Buyer.builder().username(username).bpassword(passwordEncoder.encode(dto.getNewpassword())).build());
	}

	// 정보읽기
	public Read read(String username) {
		// 메소드 참조
		Buyer buyer = dao.read(username).get(); // 내정보 찾아봐야되니까 아이디에 맞는 정보 가져옴
		// .orElseThrow(MemberNotFoundException::new);
		BuyerDto.Read dto = buyer.toDto();
		// Long days = ChronoUnit.DAYS.between(dto.getJoinday(), LocalDate.now());//
		// 가입일, 오늘날짜 사이값을 days라고 하고 dto에 극 값을
		// 집어넣음 (날짜값-날짜값 계산)
		// dto.setDays(days);
		// dto.setProfile(profilePath + dto.getProfile());// 파일+파일이름
		return dto;
	}

	// 정보 변경 - 이메일, 프사 변경
	// 이메일과 프사가 모두 비었으면 작업 중지
	// 프사만 비었으면 이메일만 변경
	// 이메일만 비었으면 프사만 변경
	// 이메일, 프사 모두 들었으면 모두 변경

	// 1. 이메일, 프사 모두 비었으면 작업 중지
	// 2. 아이디로 검색 -> 없으면 MemberNotFoundException
	// 3. String newEmail -> 이메일을 변경하면 저장, 안하면 기존 이메일 저장
	// 4. 프사 삭제 저장 후 저장
//	public Integer update(Update dto, String bname, String bemail) {
//		String newEmail = dto.getBemail();
//		if (newEmail == null)
//			throw new JobFailException("변경할 값이 없습니다");
//		Buyer buyer = dao.findBybId(bemail, bname).orElseThrow(MemberNotFoundException::new);
//		if (newEmail == null)
//			newEmail = buyer.getBemail();
//		return dao.update(Buyer.builder().bemail(bemail).bname(bname).build());
//	}

	public BuyerDto.Read updateBuyer(BuyerDto.Update dto, String username) {

		if (dto.getBemail() != null && dto.getBadress() == null && dto.getBnickname() == null && dto.getBtel() == null
				&& dto.getBpassword() == null) {

			Buyer bdata = dao.userdataall(username).get();

			bdata.updateemail(dto.getBemail());

			dao.update(bdata);
			return dao.BuyerInforRead(username).get();
		}

		if (dto.getBadress() != null && dto.getBemail() == null && dto.getBnickname() == null && dto.getBtel() == null
				&& dto.getBpassword() == null) {

			Buyer bdata = dao.userdataall(username).get();

			bdata.updateadress(dto.getBadress());

			dao.update(bdata);
			return dao.BuyerInforRead(username).get();
		}

		if (dto.getBnickname() != null && dto.getBadress() == null && dto.getBemail() == null && dto.getBtel() == null
				&& dto.getBpassword() == null) {

			Buyer bdata = dao.userdataall(username).get();

			bdata.updatenickname(dto.getBnickname());
			System.out.println(bdata);
			dao.update(bdata);
			return dao.BuyerInforRead(username).get();
		}

		if (dto.getBtel() != null && dto.getBnickname() == null && dto.getBadress() == null && dto.getBemail() == null
				&& dto.getBpassword() == null) {

			Buyer bdata = dao.userdataall(username).get();

			bdata.updatetel(dto.getBtel());

			dao.update(bdata);
			return dao.BuyerInforRead(username).get();
		}

		if (dto.getBpassword() != null && dto.getBtel() == null && dto.getBnickname() == null
				&& dto.getBadress() == null && dto.getBemail() == null) {

			Buyer bdata = dao.userdataall(username).get();

			String newpassword = passwordEncoder.encode(dto.getBpassword());

			bdata.updatepassword(newpassword);

			dao.update(bdata);
			return dao.BuyerInforRead(username).get();
		}
		return dao.BuyerInforRead(username).get();
	}

	// 회원정보 출력
	public Optional<BuyerDto.Read> readBuyer(String username) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Optional<BuyerDto.Read> dto = dao.BuyerInforRead(username);
		String nickname = dto.get().getBnickname();
		return dao.BuyerInforRead(username);
	}

	// 회원 탈퇴
	// findById -> 없으면 MemberNotFoundException해도 되는데...
	// existsById -> resultType이 true/false인 예제
	public Integer resign(String username) {
		if (dao.existsBybId(username) == false)
			throw new MemberNotFoundException();
		return dao.deleteBybId(username);
	}
	
	   //주문시 회원정보 가져오기 
	   public Buyer buyerInfo(String username) {
	      return dao.buyerInfo(username);
	   }
}
