package com.me.comment.service;

import com.me.comment.dto.echarts.Option;

public interface OrderReportService {
	/**
	 * 把查出来的数据按照echart模板进行格式化
	 * @return
	 */
	Option count();
}
