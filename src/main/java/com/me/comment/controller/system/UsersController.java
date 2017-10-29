package com.me.comment.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.comment.dto.UserDto;
import com.me.comment.service.UserService;

/*
 * 给权限管理模块用的，所有的response都是json
 * */
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService;
	
	public List<UserDto> getList() {
		return userService.getList();
	}
	
	
	
}
