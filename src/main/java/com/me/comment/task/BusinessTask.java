package com.me.comment.task;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.me.comment.bean.SysParam;
import com.me.comment.constant.SysParamKeyConst;
import com.me.comment.dao.BusinessDao;
import com.me.comment.dao.SysParamDao;


@Component("businessTask")
public class BusinessTask {
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessTask.class);//logback
	
	@Autowired
	private BusinessDao businessDao;
	
	@Autowired
	private SysParamDao sysParamDao;
	
	/**
	 * 更新商品已售数量
	 */
	public void synNum(){
		SysParam sysParam = sysParamDao.selectByKey(SysParamKeyConst.LAST_SYNC_NUMBER_TIME);
		Date lastTime = null;
		if(sysParam.getParamValue() == null){//如果是第一次更新....
			lastTime = new Date(111);//1970....
		}else {
			lastTime = sysParam.getParamValue();
		}
		Date endTime = new Date();
		businessDao.updateNumber(lastTime,endTime);//更新从上一次同步后到现在的时间段的商品已售数量
		sysParam.setParamValue(endTime);
		sysParamDao.updateByKey(sysParam);
		logger.info("商品销售数量已同步！！！");
	}
	
	public void synStar(){
		logger.info("已同步！！！");
	}
	
}
