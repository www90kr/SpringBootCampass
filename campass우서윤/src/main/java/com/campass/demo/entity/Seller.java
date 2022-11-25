 package com.campass.demo.entity;

import java.time.LocalDate;

import com.campass.demo.dto.*;
import com.campass.demo.dto.SellerDto.Read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
	private String username; // 사용자 입력
	private String spassword; // 서버에서 암호화해서 추가
	private String semail;
	private String sname;
	private Integer stel;
	private String businessName;
	private Integer businessNo;


	private LocalDate sjoinday; //ㄴ
	private Integer enabled;
	private String role;
	
	public void addJoinInfo(String encodedPassword) {
		this.spassword = encodedPassword;
	}
	
	public Read toDto() {
		return SellerDto.Read.builder().username(username).sname(sname).semail(semail).stel(stel).businessName(businessName).businessNo(businessNo)
				.build();
	}
	public void updateemail(String semail) {
		this.semail = semail;
	}
	
	public void updatetel(Integer stel) {
		this.stel = stel;
	}
	
	public void updatepassword(String spassword) {
		this.spassword= spassword;
	}
	


}
