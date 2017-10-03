package com.me.comment.service;

import com.me.comment.dto.UserDto;

public interface UserService {
	/**
	 * 校验用户名/密码是否正确
	 * @param userDto 待校验dto对象
	 * @return true：用户名/密码正确，如果正确，将dto对象里其他属性补齐
	 *                false：用户名/密码错误
	 */
	boolean validate(UserDto userDto);
}
