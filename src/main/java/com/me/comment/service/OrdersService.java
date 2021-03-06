package com.me.comment.service;

import java.util.List;

import com.me.comment.dto.OrdersDto;

public interface OrdersService {

	/**
	 * 新增订单
	 * @param ordersDto
	 * @return 是否新增成功：true：新增成功，false：新增失败
	 */
	boolean add(OrdersDto ordersDto);
	
	/**
     * 根据主键获取订单的Dto对象
     * @param id 订单表主键值
     * @return 订单的Dto对象
     */
	OrdersDto getById(Long id);
	
	/**
	 * 根据会员ID获取该会员的全部订单dto列表
	 * @param memberId 会员ID
	 * @return 会员的订单dto列表
	 */
	List<OrdersDto> getListByMemberId(Long memberId);
	/**
	 * 获取所有订单
	 * @return
	 */
	List<OrdersDto> searchByPage(OrdersDto ordersDto);

	int deleteById(Long id);

}
