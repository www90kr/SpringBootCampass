package com.campass.demo.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CZone {
	private Integer czCode		;
	private String czName		;
	private Integer czPrice	;
	private String czPhoto		;
	private String czFileName		;
	private String cz_username		;
	private Integer cz_cCode		;
	
	public CZone czAdd(String loginId, Integer cCode) {
		this.cz_username= loginId;
		this.cz_cCode = cCode;
		return this;
		
	}
}
