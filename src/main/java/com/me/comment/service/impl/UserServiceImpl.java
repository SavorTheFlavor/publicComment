package com.me.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.comment.bean.User;
import com.me.comment.dao.UserDao;
import com.me.comment.dto.UserDto;
import com.me.comment.service.UserService;
import com.me.comment.util.CommonUtil;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean validate(UserDto userDto) {
		if(userDto != null && !CommonUtil.isEmpty(userDto.getName()) && !CommonUtil.isEmpty(userDto.getPassword())){
			User user = new User();
			BeanUtils.copyProperties(userDto, user);
			List<User> users = userDao.select(user);
			if(users.size() == 1){
				BeanUtils.copyProperties(users.get(0), userDto);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<UserDto> getList() {
		List<UserDto> result = new ArrayList<UserDto>();
		List<User> users = userDao.select(new User());
		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDto.setpId(0);//zTree根节点的id
		}
		return result;
	}

}
