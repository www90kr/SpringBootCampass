package com.campass.demo.dto;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchDto {
	private Integer ch_ctCode;
	private Integer ch_caCode;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ch_checkIn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ch_checkOut;
}
