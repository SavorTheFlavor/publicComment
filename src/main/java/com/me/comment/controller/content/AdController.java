package com.me.comment.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.me.comment.constant.PageCodeEnum;
import com.me.comment.dto.AdDto;
import com.me.comment.service.AdService;

@Controller
@RequestMapping("/ad")
public class AdController {

	@Autowired
	private AdService adService;
	
	@RequestMapping
	public String init(Model model){
		AdDto adDto = new AdDto();
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	
	@RequestMapping("/addInit")
	public String addInit(){
		return "/content/adAdd";
	}
	
	@RequestMapping("/add")
	public String add(AdDto adDto, Model model){
		if(adService.add(adDto)){
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
		};
		return "/content/adAdd";
	}
	
	public String remove(@RequestParam("id")Long id){		//用封装的对象接收对应属性值可以通过反射获取
																							//但如果只是一个基本类型反射就获取不了了
																						//RequestParam必须要写！
																					//Spring是通过读取class文件里的调试信息
																				//来获取变量名的，如果编译时不生成调试信息的话就挂了
		return "forword:/ad";												
	}
	
	@RequestMapping("/search")
	public String search(Model model, AdDto adDto){
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	
}
