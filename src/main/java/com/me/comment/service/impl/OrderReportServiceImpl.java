package com.me.comment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.comment.dao.ReportDao;
import com.me.comment.dto.echarts.Option;
import com.me.comment.dto.echarts.Serie;
import com.me.comment.service.OrderReportService;

@Service
public class OrderReportServiceImpl implements OrderReportService {

	@Autowired
	private ReportDao reportDao;
	
	@Override
	public Option count() {
		Option option = new Option();
		List<Map<String, String>> list = reportDao.countOrder();
		//category, in case of duplication....
		Set<String> categoryNameSet = new TreeSet<>();
		// category+time as key, amount as value
		Map<String, Long> countMap = new HashMap<String, Long>();
		for (Map<String, String> map : list) {
			categoryNameSet.add(map.get("categoryName"));
			countMap.put(map.get("categoryName")+map.get("hour"), Long.valueOf(map.get("count")));
		}
		
		option.getLegend().setData(new ArrayList<String>(categoryNameSet));
		List<String> hours = new ArrayList<>();
		for(int i=0;i<24;i++){
			hours.add(String.format("%02d", i));
		}
		option.getxAxis().setData(hours);
		//设置每条线的名称和数值
		for(String categoryName : categoryNameSet){
			Serie serie = new Serie();
			option.getSeries().add(serie);
			serie.setName(categoryName);
			serie.setType("line");
			for (String hour : hours) {
				serie.getData().add(countMap.get(categoryName+hour) == null ? 0 : countMap.get(categoryName+hour)) ;
			}
		}
		return option;
	}

}
