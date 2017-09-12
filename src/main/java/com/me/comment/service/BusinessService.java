package com.me.comment.service;

import java.util.List;

import com.me.comment.bean.Business;
import com.me.comment.dto.BusinessDto;
import com.me.comment.dto.BusinessListDto;

public interface BusinessService {
	boolean add(BusinessDto businessDto);
    /**
     * 分页搜索商户列表
     * @param businessDto 查询条件(包含分页对象)
     * @return 商户列表
     */
    List<BusinessDto> searchByPage(BusinessDto businessDto);
	/**
     * 分页搜索商户列表(接口专用)
     * @param businessDto 查询条件(包含分页对象)
     * @return 商户列表Dto对象
     */
    BusinessListDto searchByPageForApi(BusinessDto businessDto);
	BusinessDto findById(Long id);
	boolean update(BusinessDto dto);
	boolean delete(Long id);
}
