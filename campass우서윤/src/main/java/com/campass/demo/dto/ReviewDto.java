package com.campass.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.campass.demo.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//dto 를 만들때 dto 안에 또다른 여러 dto객체를 생성할때 주의할점 : List Dto 만큼은 static으로 빼자  static을 안넣을경우 거의 90퍼센트는 오류나는거같다 기억해두기 
public class ReviewDto {

	
	@Data
	public class WriteDto{
		private String cReviewContent;
		private Integer cReviewStar;
		
		public Review toEntity() {
			return Review.builder().cReviewContent(cReviewContent).cReviewStar(cReviewStar).build();
		}
	}
	

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ListDto{
		private Integer cReviewNo;
		private String cReviewContent;
		@DateTimeFormat
		private LocalDate cReviewDate;
		private Integer cReviewStar;
		private Integer cReview_rCode;
		private String cReview_username;
		private Integer cReview_cCode;

	}
	
	@Data
	public class DeleteDto{
		private Integer cReviewNo;
		private String cReview_username;
	
	}
}
