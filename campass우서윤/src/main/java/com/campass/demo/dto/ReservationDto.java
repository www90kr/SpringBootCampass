package com.campass.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.campass.demo.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;;

public class ReservationDto {
	//입력용
	@ToString
	@AllArgsConstructor
	@Data
	public  class RWriteDto{
		private String	rName		;
		private String	rbankno		;
		private String	rbank		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckIn;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckOut;
		private String	rTel		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rDate;
		
		public Reservation toRentity() {
			
			return Reservation.builder().rName(rName).rCheckIn(rCheckIn).rCheckOut(rCheckOut).rbank(rbank).rbankno(rbankno).rTel(rTel).rDate(rDate).build();
		}
		}
	
	
	//입력용
	@Data
	public class RUpdateDto{
		private Integer	rCode		;
		private String	re_username		;
		private String	rName		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckIn;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckOut;
		private String	rTel		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDateTime rDate;
		

		}
	

	
	//출력용
	@Data
	public class RReadDto{
		private String	rName		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckIn;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckOut;
		private String	rTel		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rDate;
		private String rStatus ;
		}
	
	//출력용
	@Data
	public class RListDto{
		private String	rName		;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckIn;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rCheckOut;
		private String	rTel		;
		private String rStatus ;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate rDate;
		}

	
	//딜리트 삭제 
	//24시간 이내에만 가능하게 하기 
	public class RCancelDto{
		private String	rCode	;
		private String	re_username		;
	}
	
}
