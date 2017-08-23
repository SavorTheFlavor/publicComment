package com.me.comment.dao;

import java.util.List;

import com.me.comment.base.BaseDao;
import com.me.comment.bean.Ad;

public interface AdDao {
	int insert(Ad ad);
	List<Ad> selectByPage(Ad condition);
	int delete(Long id);
	int update(Ad ad);
	Ad get(Long id);
}
