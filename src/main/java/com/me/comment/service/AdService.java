package com.me.comment.service;

import com.me.comment.dto.AdDto;

public interface AdService {
	/**
	 * 新增广告
	 * @param adDto
	 * @return
	 */
	boolean add(AdDto adDto);
}
