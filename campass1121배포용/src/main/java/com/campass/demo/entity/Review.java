package com.campass.demo.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Review {
	private Integer cReviewNo; //리뷰글번호
	private String cReviewContent;
	@DateTimeFormat
	private LocalDate cReviewDate;
	private Integer cReviewStar;
	private Integer cReview_rCode;	//예약번호
	private String cReview_username;
	private Integer cReview_cCode;

	
	public Review addInfo(String loginId, Integer rCode, Integer cCode) {
		
		this.cReview_rCode=rCode;
		this.cReview_username=loginId;
		this.cReview_cCode = cCode;
		
		return this;
	}
}


//읽기는 캠핑장페이지에서
//쓰기는 마이페이지에서
//읽기는 캠핑장 리드에서 가능하도록 구현해야하는데 이걸 타임리프 fragment로 가야하는건지 아니면 캠핑장 readDto 에 꽂아야하는건지 모르겠다
