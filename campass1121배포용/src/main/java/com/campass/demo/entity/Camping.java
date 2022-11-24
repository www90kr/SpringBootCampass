package com.campass.demo.entity;

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
public class Camping {

	private Integer cCode		;
	private String cTel		;
	private String cName		;
	private String cContent		;
	private String cAddr		;
	private String cSite		;
	private String cPhoto		;
	private String cFileName		;
	private Integer cLikeCnt		;
	private Integer cReviewStarSum		;
	private Integer cReviewCnt		;
	private Integer c_ctCode		;
	private Integer c_caCode		;
	private Integer c_cOptionCode		;
	private String c_username	;	
	private String ImagePath;
	
	public Camping cAdd(String loginId) {
		this.c_username=loginId;
		return this;
	}
}