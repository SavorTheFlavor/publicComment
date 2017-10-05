package com.me.comment.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.me.comment.dto.OrdersDto;
import com.me.comment.service.OrdersService;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	@RequestMapping
	public String init(Model model) {
		OrdersDto ordersDto = new OrdersDto();//page对象默认	    this.currentPage = 1; this.pageNumber = 5;
		List<OrdersDto> ordersDtos = ordersService.searchByPage(ordersDto);
		System.out.println(ordersDtos.get(0).getMember());
		model.addAttribute("ordersList", ordersDtos);
		return "/content/orderList";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(OrdersDto dto, Model model) {
		List<OrdersDto> ordersDtos = ordersService.searchByPage(dto);
		model.addAttribute("ordersList", ordersDtos);
		return "/content/orderList";
	}
	
	@RequestMapping("/remove")
	public String remove(@RequestParam("id") Long id) {
		int res = ordersService.deleteById(id);
		return "forward:/orders";
	}
}