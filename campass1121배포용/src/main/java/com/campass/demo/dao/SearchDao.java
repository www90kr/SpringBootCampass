package com.campass.demo.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.campass.demo.entity.*;


@Mapper
public interface SearchDao {
	public String getCaName(Integer caCode);
	
	public String getCtName(Integer ctcode);
	
}









