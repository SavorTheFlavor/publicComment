package com.me.comment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.comment.bean.Dic;
import com.me.comment.dao.DicDao;
import com.me.comment.service.DicService;

@Service
public class DicServiceImpl implements DicService {

	@Autowired
	private DicDao dicDao;
	
	@Override
	public List<Dic> getListByType(String type) {
		Dic dic = new Dic();
		dic.setType(type);
		return dicDao.select(dic);
	}

}
