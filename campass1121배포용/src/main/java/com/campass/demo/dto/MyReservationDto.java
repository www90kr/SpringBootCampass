package com.campass.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.campass.demo.entity.Reservation;

import lombok.Data;


//예약내역 Dto 를 예약테이블에서 dto로 따로 팠음 
//서브쿼리로 만든 임시컬럼(화면에서 조건으로 쓸 cntOfReview) 추가 
//캠핑이름과 캠핑사진을 띄우기 위해 캠핑테이블과 조인함 쿼리문 정독하기 
@Data
public class MyReservationDto {
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
	Integer cntOfReview;
	private String cName		; //캠핑테이블에서 바로조인
	private String cPhoto	;		//캠핑테이블에서 바로조인
}
