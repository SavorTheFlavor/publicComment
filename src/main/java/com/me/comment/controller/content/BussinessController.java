package com.me.comment.controller.content;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.comment.bean.Business;
import com.me.comment.bean.Dic;
import com.me.comment.constant.DicTypeConst;
import com.me.comment.constant.PageCodeEnum;
import com.me.comment.dto.BusinessDto;
import com.me.comment.service.BusinessService;
import com.me.comment.service.DicService;

@Controller
@RequestMapping("/businesses")
public class BussinessController {
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private DicService dicService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String search(BusinessDto dto, Model model) {
		model.addAttribute("list", businessService.searchByPage(dto));
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}
	
	@RequestMapping("/addPage")
	public String addInit(Model model) {
		List<Dic> cityList = dicService.getListByType(DicTypeConst.CITY);
		List<Dic> categoryList = dicService.getListByType(DicTypeConst.CATEGORY);
		model.addAttribute("cityList", cityList);
		model.addAttribute("categoryList", categoryList);
		return "/content/businessAdd";
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public String add(BusinessDto businessDto, RedirectAttributes redirectAttributes){
		if(businessService.add(businessDto)){
			redirectAttributes.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
			return "redirect:/businesses";
		}else{
			redirectAttributes.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
			return "redirect:/businesses/addPage";
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String modifyInit(@PathVariable("id")Long id, Model model){
		BusinessDto dto = businessService.findById(id);
		model.addAttribute("modifyObj", dto);
		
		List<Dic> cityList = dicService.getListByType(DicTypeConst.CITY);
		List<Dic> categoryList = dicService.getListByType(DicTypeConst.CATEGORY);
		model.addAttribute("cityList", cityList);
		model.addAttribute("categoryList", categoryList);
		return "/content/businessModify";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String modify(BusinessDto dto, Model m){
		businessService.update(dto);
		return "redirect:/businesses"; //这里返回页面时用的仍然是put
														//jsp页面不支持！
														//可以重定向
														//或者转到其他RequestMapping再返回
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id")Long id){
		businessService.delete(id);
		return "redirect:/businesses"; //这里返回页面时用的仍然是delete
															//jsp页面不支持！
															//可以重定向
															//或者转到其他RequestMapping再返回
	}
	
}
