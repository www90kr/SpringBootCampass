package com.campass.demo.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.campass.demo.entity.*;

@Mapper
public interface MemoDao {
	public void save(Memo memo);
	
	public Integer getMno();
	
	public void saveAll(List<Memo> memos);

	public Integer countByReceiver(String receiver);

	public List<Memo> findBySender(String sender);
	
	public List<Memo> findByReceiver(String receiver);
	
	public Integer setToRead(Integer mno);
	
	public Integer disabledBySender(List<Integer> mnos, String sender);
	
	public Integer disabledByReceiver(List<Integer> mnos, String receiver);
	
	public Integer deleteByDisabled();

	public Optional<Memo> findById(Integer mno);

}
