package com.campass.demo.dto;

import java.util.List;

import org.apache.tomcat.jni.Library;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDto {
	private Integer prev;
	private List<Integer> pagenos;
	private Integer next;
	private Integer pageno;
	private List<CampingDto.CListDto> list;
}