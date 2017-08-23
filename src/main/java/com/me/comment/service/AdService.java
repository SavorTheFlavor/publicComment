package com.me.comment.service;

import java.util.List;

import com.me.comment.dto.AdDto;

public interface AdService {
	/**
	 * 新增广告
	 * @param adDto
	 * @return
	 */
	boolean add(AdDto adDto);
	/**
	 * 分页搜索广告列表
	 * @param adDto
	 */
	List<AdDto> searchByPage(AdDto adDto);
	
	/**
	 * 删除广告
	 */
	boolean delete(Long id);
	/**
	 * 修改广告
	 */
	boolean modify(AdDto adDto);
	/**
	 * 查询广告
	 * @param id
	 * @return
	 */
	AdDto get(Long id);
}
