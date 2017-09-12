package com.me.comment.service;

import java.util.List;

import com.me.comment.bean.Dic;

public interface DicService {
	List<Dic> getListByType(String type);
}
