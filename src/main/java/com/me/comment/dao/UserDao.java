package com.me.comment.dao;

import java.util.List;

import com.me.comment.bean.User;

public interface UserDao {
	List<User> select(User user);
}
