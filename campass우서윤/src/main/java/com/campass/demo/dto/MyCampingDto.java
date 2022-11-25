package com.campass.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCampingDto {
	private Integer	rCode		;
	private String	rName		;
	private String	rTel		;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate rCheckIn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate rCheckOut;
	private LocalDate rDate;
	private Integer	rPrice		;
	private String rStatus ;
	private Integer	re_czCode		;
	private Integer	re_cCode	;
	private String	re_username		;
	private String rbankno;
	private String rbank;
	private String cName;	//캠핑테이블에서 바로조인
	private String cPhoto;	//캠핑테이블에서 바로조인
	private String c_username;		//캠핑테이블에서 바로조인
}
