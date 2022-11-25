package com.campass.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.campass.demo.entity.Heart;


@Mapper
public interface HeartDao {
	// 찜하기 ( 추가 )
	public int insertHeart(Heart heart);
	// 찜 목록 확인
	public List<Heart> getHeartListByUserid(String userid);
	// 찜 목록 1개 삭제
	public Integer delete(Integer cCode);
	// 이미 찜한 게시물인지 조회 아래처럼파라미터를 골뱅이찍어주면 뭐가더좋나 ? 더 확실하게 인식시켜주는거같다 얘.라.고 스프링아 ~ 이런 너낌? 
	public Heart getByCCodeWithId(@Param("heart")Heart heart);
}
