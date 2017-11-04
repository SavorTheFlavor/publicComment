package com.me.comment.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
	/**
	 * 按类别分时间段统计前一天的商品已售数量
	 * @return
	 */
	List<Map<String, String>> countOrder();
}
