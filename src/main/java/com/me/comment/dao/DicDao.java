package com.me.comment.dao;

import java.util.List;

import com.me.comment.bean.Dic;

public interface DicDao {
	List<Dic> select(Dic dic);
}
