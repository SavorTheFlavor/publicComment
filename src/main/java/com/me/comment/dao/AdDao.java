package com.me.comment.dao;

import java.util.List;

import com.me.comment.bean.Ad;

public interface AdDao {
	int insert(Ad ad);
	List<Ad> selectByPage(Ad condition);
}
