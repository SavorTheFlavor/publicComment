package com.me.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.me.comment.bean.Orders;
import com.me.comment.constant.CommentStateConst;
import com.me.comment.dao.OrdersDao;
import com.me.comment.dto.OrdersDto;
import com.me.comment.service.OrdersService;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Value("${businessImage.url}")
    private String businessImageUrl;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Override
	public boolean add(OrdersDto ordersDto) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersDto, orders);
		orders.setCommentState(CommentStateConst.NOT_COMMENT);
		int row = ordersDao.insert(orders);
		if(row != 1){
			return false;
		}
		return true;
	}

	@Override
	public OrdersDto getById(Long id) {
		OrdersDto result = new OrdersDto();
		Orders orders = ordersDao.selectById(id);
		BeanUtils.copyProperties(orders, result);
		return result;
	}

	@Override
	public List<OrdersDto> getListByMemberId(Long memberId) {
		List<OrdersDto> result = new ArrayList<OrdersDto>();
		Orders ordersForSelect = new Orders();
		ordersForSelect.setMemberId(memberId);
		List<Orders>  ordersList = ordersDao.select(ordersForSelect);
		for(Orders orders : ordersList) {
			OrdersDto ordersDto = new OrdersDto();
			result.add(ordersDto);
			BeanUtils.copyProperties(orders, ordersDto);
			ordersDto.setImg(businessImageUrl + orders.getBusiness().getImgFileName());
			ordersDto.setTitle(orders.getBusiness().getTitle());
			ordersDto.setCount(orders.getBusiness().getNumber());
		}
		return result;
	}

	@Override
	public List<OrdersDto> searchByPage(OrdersDto ordersDto) {
		List<OrdersDto> result = new ArrayList<OrdersDto>();
		Orders ordersForSelect = new Orders();
		BeanUtils.copyProperties(ordersDto, ordersForSelect);
		List<Orders>  ordersList = ordersDao.selectByPage(ordersForSelect);
		for(Orders orders : ordersList) {
			OrdersDto ordersDto2 = new OrdersDto();
			result.add(ordersDto2);
			BeanUtils.copyProperties(orders, ordersDto2);
			ordersDto2.setImg(businessImageUrl + orders.getBusiness().getImgFileName());
			ordersDto2.setTitle(orders.getBusiness().getTitle());
			ordersDto2.setCount(orders.getBusiness().getNumber());
		}
		return result;
	}

	@Override
	public int deleteById(Long id) {
		return ordersDao.deleteById(id);
	}

}
