package com.campass.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.campass.demo.dto.MyCampingDto;
import com.campass.demo.dto.MyReservationDto;
import com.campass.demo.entity.Reservation;


@Mapper
public interface ReservationDao {
	
	
	//예약된룸 오늘되면 예약테이블에서 풀기 dao  
	//예약한룸 insert dao
	//예약한룸 취소하기
	//예약테이블 데이터를 판매자 마이페이지와 구매자 마이페이지에띄워줘야한다 (테이블 조인시켜서 username으로  )
	//예약상태변경하기 
	
	public int insertReservation(Reservation reservation);
	
	public Reservation RRfindById(Integer rCode);
	
	public List<MyReservationDto> RRmyReservation(String username);//일반회원  내역리스트 username으로 진행 
	
	public List<MyCampingDto> RRmyCamping(String loginId);//판매회원   파라미터 일단 username으로 진행 
	
	public Integer updateRStatus(Integer rCode); //예약대기상태>확정상태 dao
	
	public Integer cancelReservation(Integer rCode); //  취소삭제
	
	public Optional<String> RRfindWriterById(Integer rCode); //예약 변경 삭제전에 Id를 확인하기위한 절차 
	
	public Optional<String> RRfindCName(Integer re_cCode);//예약테이블의 cCode로 캠핑장이름가져오기
	
	public String RRfindHostById(Integer rCode); //예약번호로 캠핑장주인조회 환불진행

}
	