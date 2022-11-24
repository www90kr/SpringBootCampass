package com.campass.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.campass.demo.dto.SellerDto;
import com.campass.demo.entity.*;
@Mapper
public interface SellerDao {
	//아이디 중복검사
	public Boolean existsBysId(String username); 
	
	//이메일 중복검사
	public Boolean existsBysEmail(String semail);
	
	//연락처 중복검사
	public Boolean existsBysTel(Integer stel);
	
	//사업자번호 중복검사
	public Boolean existsByBusinessNo(Integer businessNo);
	
	//DB에 정보 저장 (회원가입)
	public Integer save(Seller seller);
	
	//이매일, 이름 입력 -> 아이디찾기
	public Optional<Seller> findBysId(String semail, String sname); 
	
	//정보 수정
	public Integer update(Seller seller);
	
	//임시비밀번호 수정(업데이트)
	public Integer resetpw(Seller seller);
	
	//임시비밀번호 입력받아 비밀번호 변경
	public Integer changepw(Seller seller);
	
	// 업데이트를 위한 전체내용 가져오기
	public Optional<Seller> userdataall(String username);

	//회원 삭제
	public Integer deleteBysId(String username);
	
	//내 정보 읽기
	public Optional<Seller> read(String username);
								//입력될값
	// 회원 정보 출력(읽기)
	public Optional<SellerDto.Read> SellerInforRead(String username);
	
	
}
