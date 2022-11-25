package com.campass.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.campass.demo.dto.ReviewDto;
import com.campass.demo.entity.Review;


@Mapper
public interface ReviewDao {
	
		
	public int insertReview(Review review); //리뷰 쓰기
	
	public List<ReviewDto.ListDto> listReviewBy(Integer re_cCode); //리뷰 리스트 기본정렬 번호순
	
	public ReviewDto.ListDto listReviewByStar(Integer re_cCode);// 리뷰 리스트 평점순 cCode로 리뷰리스트를 상세페이지로 가져와서 부른다 
	
	public int deleteReview(Integer cReviewNo);//리뷰 삭제
	
	public String getUserbyId(Integer cReviewNo);//리뷰번호를 작성한 글쓴이 가져오기 
	
	public Review getReviewbyId(Integer cReviewNo); //리뷰번호로 리뷰조회


}
