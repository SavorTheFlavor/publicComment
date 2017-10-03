package com.me.comment.dao;

import java.util.Date;
import java.util.List;

import com.me.comment.bean.Business;

public interface BusinessDao {
	int insert(Business business);
	List<Business> selectByPage(Business business);
	Business selectById(Long id);
	int update(Business business);
	int deleteById(Long id);
	/**
	 * 
	 * @param lastTime
	 * @param endTime
	 * @return
	 */
	int updateNumber(Date lastTime, Date endTime);
}
