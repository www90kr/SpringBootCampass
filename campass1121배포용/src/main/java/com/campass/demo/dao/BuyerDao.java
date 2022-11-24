package com.campass.demo.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.campass.demo.dto.BuyerDto;
import com.campass.demo.entity.Buyer;

@Mapper
public interface BuyerDao {
	//아이디 중복검사
	public Boolean existsBybId(String username); 
	
	//이메일 중복검사
	public Boolean existsBybEmail(String bemail);
	
	//닉네임 중복검사
	public Boolean existsBybNickname(String bnickname);
	
	//연락처 중복검사
	public Boolean existsBybTel(Integer btel);
	
	//DB에 정보 저장 (회원가입)
	public Integer save(Buyer buyer);
	
	//이매일, 이름 입력 -> 아이디찾기
	public Optional<Buyer> findBybId(String bemail, String bname); 
	
	//정보 수정
	public Integer update(Buyer buyer);
	
	//임시비밀번호 수정(업데이트)
	public Integer resetpw(Buyer buyer);
	
	//임시비밀번호 입력받아 비밀번호 변경
	public Integer changepw(Buyer buyer);
	
	// 업데이트를 위한 전체내용 가져오기
	public Optional<Buyer> userdataall(String username);

	//회원 삭제
	public Integer deleteBybId(String username);
	
	//내 정보 읽기
	public Optional<Buyer> read(String username);
								//입력될값
	// 회원 정보 출력(읽기)
	public Optional<BuyerDto.Read> BuyerInforRead(String username);
	
	//주문시 회원정보 가져오기 
	public Buyer buyerInfo(String username);
}
