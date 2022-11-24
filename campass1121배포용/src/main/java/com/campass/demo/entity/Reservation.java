package com.campass.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

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
	public Reservation rAdd(String loginId, Integer cCode, Integer czCode,Integer rPrice) {
		this.re_username = loginId;
		this.re_cCode =  cCode;
		this.re_czCode = czCode;
		this.rPrice = rPrice;
		return this;
	}
}
