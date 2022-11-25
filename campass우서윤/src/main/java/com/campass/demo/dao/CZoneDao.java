package com.campass.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.campass.demo.dto.CZoneDto;
import com.campass.demo.dto.SearchDto;
import com.campass.demo.dto.CZoneDto.CZListDto;
import com.campass.demo.entity.CZone;

@Mapper
public interface CZoneDao {
	
	//czone은  read 와 search 가 없음 페이징없음  검색은 camping을 통해서만 띄울예정  camping read했을때 campingZone 리스트가 나와야됨 
	// 리스트는 cz_sId 또는 cz_cCode 같은것끼리만 나타나게처리   일단 czFindAll 을 구현 한 다음 처리하기 
	
	//캠핑존 저장
	public int cZoneSave(CZone cZone);
	
	//캠핑존 수정
	public int cZoneUpdate(CZoneDto.CZUpdateDto dto);
	
	//캠핑존 수정 삭제 인증을위한 아이디꺼내기
	public Optional<String> czFindWriterById(Integer czCode);
	
	//기본정렬 캠핑장내에서만  (캠핑존리스트) >>> 이게  campingRead 페이지 아래에 들어가야함 
	public List<CZListDto> czFindAll(Integer cCode);
	
	//캠핑존 삭제	
	public int cZoneDel(Integer czCode);
	
	//캠핑존 검색결과 나열하기 (캠핑장별로)  파라미터는 SearchDto 가 될예정임 
	public List<CZoneDto.CZListDto> czDetail(Map map);
	
	//czCode로 캠핑존 꺼내오기
	public CZone findbyCzCode(Integer czCode);





}
