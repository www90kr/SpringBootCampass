package com.campass.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.campass.demo.dto.CommentDto;

@Mapper
public interface CommentDao {

	// 글번호로 댓글 출력
	@Select("select cno,cContent,username,cWriteTime from bcomment where bno=#{bno}")
	public List<CommentDto.Read> findByBno(Integer bno); 
}
