package com.me.comment.dao;

import java.util.List;

import com.me.comment.bean.Comment;

public interface CommentDao {

	void insert(Comment comment);

	List<Comment> selectByPage(Comment comment);

}
