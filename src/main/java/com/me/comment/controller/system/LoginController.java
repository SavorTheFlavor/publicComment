package com.me.comment.controller.system;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.comment.constant.PageCodeEnum;
import com.me.comment.constant.SessionKeyConst;
import com.me.comment.dto.GroupDto;
import com.me.comment.dto.UserDto;
import com.me.comment.service.GroupService;
import com.me.comment.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private HttpSession session;
	
	@RequestMapping()
	public String index(){
		return "/system/login";
	}

	@RequestMapping("/sessionTimeout")
	public String sessionTimeout(RedirectAttributes attr){
		attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.SESSION_TIMEOUT);
		return "redirect:/login";
	}
	
	@RequestMapping("/validate")
	public String validate(UserDto userDto, RedirectAttributes attr,@RequestParam(value="remember",defaultValue="off")String remember){
		//System.out.println(remember); checkbox	//"on" or ""
		if (userService.validate(userDto)) {
			session.setAttribute(SessionKeyConst.USER_INFO, userDto);
			GroupDto groupDto = groupService.getByIdWithMenuAction(userDto.getGroupId());
			session.setAttribute(SessionKeyConst.MENU_INFO,groupDto.getMenuDtoList());
			session.setAttribute(SessionKeyConst.ACTION_INFO, groupDto.getActionDtoList());
			return "redirect:/index";//如果有初始化操作的话
		}
		//Redirect with params using flash session........
		attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
		return "redirect:/login";
	}
	
}
