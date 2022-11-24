package com.campass.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import com.campass.demo.dao.*;
import com.campass.demo.dto.*;
import com.campass.demo.entity.*;
import com.campass.demo.exception.*;
import com.campass.demo.websocket.*;

@Service
public class MemoService {
	@Autowired
	private MemoDao memoDao;
	@Autowired
	private WebSocketService webSocketService;
	
	public void send(MemoDto.Write dto, String loginId) {
		Memo memo = dto.toEntity(loginId);
		memoDao.save(memo);
		webSocketService.sendTo(loginId, memo.getReceiver(), "새로운 메시지가 도착했습니다");
	}

	public Integer unreadMemoCount(String receiver) {
		return memoDao.countByReceiver(receiver);
	}
//seller -> buyer 에게 쪽지
	public void sendAll(MemoDto.Write dto, String loginId) {
		List<String> receivers = Arrays.asList("tttt");
		List<Memo> memos = new ArrayList<>();
		
		Integer startMno = memoDao.getMno();
		for(String receiver:receivers) {
			memos.add(new Memo(startMno++, dto.getTitle(), dto.getContent(), loginId, receiver));
		}
		memoDao.saveAll(memos);
		webSocketService.sendAll(loginId, receivers, "새로운 메시지가 도착했습니다");
	}
//buyer -> seller 에게 쪽지
	public void sendAllCheck(MemoDto.Write dto, String loginId) {
		// 현재 예약한 사용자가 3명이 있다고 하자
		List<String> receivers = Arrays.asList("SELLER");
		List<Memo> memos = new ArrayList<>();
		
		Integer startMno = memoDao.getMno();
		for(String receiver:receivers) {
			memos.add(new Memo(startMno++, dto.getTitle(), dto.getContent(), loginId, receiver));
		}
		memoDao.saveAll(memos);
		webSocketService.sendAll(loginId, receivers, "새로운 메시지가 도착했습니다");
	}
	

	public List<Memo> sendMemos(String loginId) {
		return memoDao.findBySender(loginId);
	}

	public List<Memo> receiveMemos(String loginId) {
		return memoDao.findByReceiver(loginId);
	}
	
	public Memo read(Integer mno, String loginId) {
		Memo memo = memoDao.findById(mno).orElseThrow(MemoNotFoundException::new);
		if(memo.getIsRead()==false && memo.getReceiver().equals(loginId))
			memoDao.setToRead(mno);
		return memo;
	}

	public void disabledBySender(MemoDto.Disable dto, String loginId) {
		memoDao.disabledBySender(dto.getList(), loginId);
	}

	public void disabledByReceiver(MemoDto.Disable dto, String loginId) {
		System.out.println(dto.getList());
		memoDao.disabledByReceiver(dto.getList(), loginId);
	}

	@Scheduled(cron="0 0 4 ? * THU")
	public void deleteByDisabled() {
		memoDao.deleteByDisabled();
	}

	public Integer getState(String loginId) {
		return memoDao.countByReceiver(loginId);
	}
}
