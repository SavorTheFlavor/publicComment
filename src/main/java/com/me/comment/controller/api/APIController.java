package com.me.comment.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.comment.bean.Ad;
import com.me.comment.bean.Page;
import com.me.comment.constant.ApiCodeEnum;
import com.me.comment.dao.CommentService;
import com.me.comment.dto.AdDto;
import com.me.comment.dto.ApiCodeDto;
import com.me.comment.dto.BusinessDto;
import com.me.comment.dto.BusinessListDto;
import com.me.comment.dto.CommentForSubmitDto;
import com.me.comment.dto.CommentListDto;
import com.me.comment.dto.OrderForBuyDto;
import com.me.comment.dto.OrdersDto;
import com.me.comment.service.AdService;
import com.me.comment.service.BusinessService;
import com.me.comment.service.MemberService;
import com.me.comment.service.OrdersService;
import com.me.comment.util.CommonUtil;

/**
 *  提供api接口测试数据
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api")
public class APIController {

	@Value("${ad.number}")
	private int adNumber;
	
	@Value("${businessHome.number}")
	private int businessHomeNumber;
	
	@Value("${businessSearch.number}")
	private int businessSearchNumber;
	
	@Autowired
	private AdService adService;

	@Resource
	private BusinessService businessService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private CommentService commentService;
	

	/**
	 * 首页 —— 广告（超值特惠）
	 */
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead() throws JsonParseException, JsonMappingException, IOException{
		AdDto adDto = new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		return adService.searchByPage(adDto);
	}
	
	/**
	 * 首页 —— 推荐列表（猜你喜欢）
	 */
	@RequestMapping(value = "/homelist/{city}/{page.currentPage}", method = RequestMethod.GET)
	public BusinessListDto homelist(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessHomeNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 三个参数
	 */
	@RequestMapping(value = "/search/{page.currentPage}/{city}/{category}/{keyword}", method = RequestMethod.GET)
	public BusinessListDto searchByKeyword(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	
	/**
	 * 搜索结果页 - 搜索结果 - 两个参数
	 */
	@RequestMapping(value = "/search/{page.currentPage}/{city}/{category}", method = RequestMethod.GET)
	public BusinessListDto search(BusinessDto businessDto) {
		businessDto.getPage().setPageNumber(businessSearchNumber);
		return businessService.searchByPageForApi(businessDto);
	}
	

	/**
	 * 详情页 - 商户信息
	 */
	@RequestMapping(value = "/detail/info/{id}", method = RequestMethod.GET)
	public BusinessDto detail(@PathVariable("id") Long id) {
		return businessService.findById(id);
	}
	
	/**
	 * 买单接口
	 * @param orderForBuyDto
	 * @return
	 */
	@RequestMapping(value="/order",method = RequestMethod.POST)
	public ApiCodeDto Order(OrderForBuyDto orderForBuyDto){
		ApiCodeDto apiCodeDto;
		// 1、校验token是否有效（缓存中是否存在这样一个token，并且对应存放的会员信息（这里指的是手机号）与提交上来的信息一致）
		Long phone = memberService.getPhone(orderForBuyDto.getToken());
		if(phone!= null && phone.equals(orderForBuyDto.getUsername())){
			Long memberId = memberService.getIdByPhone(phone);
			if(memberId == null){
				return new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
			}
			
			OrdersDto ordersDto = new OrdersDto();
			ordersDto.setNum(orderForBuyDto.getNum());
			ordersDto.setPrice(orderForBuyDto.getPrice());
			ordersDto.setBusinessId(orderForBuyDto.getId());
			ordersDto.setMemberId(memberId);
			orderService.add(ordersDto);
			
			apiCodeDto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
		}else {
			apiCodeDto = new ApiCodeDto(ApiCodeEnum.NOT_LOGIN);
		}
		return apiCodeDto;
	}
	
	
	
	/**
	 * 根据手机号下发短信验证码
	 */
	@RequestMapping(value = "/sms", method = RequestMethod.POST)
	public ApiCodeDto sms(@RequestParam("username") Long username) {
		ApiCodeDto dto;
		// 1、验证用户手机号是否存在（是否注册过）
		if (memberService.exists(username)) {
			// 2、生成6位随机数
			String code = String.valueOf(CommonUtil.random(6));
			// 3、保存手机号与对应的md5(6位随机数)（一般保存1分钟，1分钟后失效）
			if (memberService.saveCode(username, code)) {
				// 4、调用短信通道，将明文6位随机数发送到对应的手机上。
				if (memberService.sendCode(username, code)) {
					dto = new ApiCodeDto(ApiCodeEnum.SUCCESS.getErrno(), code);
				} else {
					dto = new ApiCodeDto(ApiCodeEnum.SEND_FAIL);
				}
			} else {
				dto = new ApiCodeDto(ApiCodeEnum.REPEAT_REQUEST);
			}
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.USER_NOT_EXISTS);
		}
		return dto;
	}
	
	/**
	 * 会员登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiCodeDto login(@RequestParam("username") Long username, @RequestParam("code") String code) {
		ApiCodeDto dto;
		// 1、用手机号取出保存的md5(6位随机数)，能取到，并且与提交上来的code值相同为校验通过
		String saveCode = memberService.getCode(username);
		if (saveCode != null) {
			if (saveCode.equals(code)) {
				// 2、如果校验通过，生成一个32位的token
				String token = CommonUtil.getUUID();
				// 3、保存手机号与对应的token（一般这个手机号中途没有与服务端交互的情况下，保持10分钟）
				memberService.saveToken(token, username);
				// 4、将这个token返回给前端
				dto = new ApiCodeDto(ApiCodeEnum.SUCCESS);
				dto.setToken(token);
			} else {
				dto = new ApiCodeDto(ApiCodeEnum.CODE_ERROR);
			}
		} else {
			dto = new ApiCodeDto(ApiCodeEnum.CODE_INVALID);
		}
		return dto;
	}
	
	/**
	 * 提交评论
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/submitComment", method=RequestMethod.POST)
	public ApiCodeDto submitComment(CommentForSubmitDto dto){
		ApiCodeDto result;
		// TODO 需要完成的步骤：
				// 1、校验登录信息：token、手机号
				Long phone = memberService.getPhone(dto.getToken());
				if(phone != null && phone.equals(dto.getUsername())){
					// 2、根据手机号取出会员ID
					Long memberId = memberService.getIdByPhone(phone);
					// 3、根据提交上来的订单ID获取对应的会员ID，校验与当前登录的会员是否一致
					OrdersDto order = orderService.getById(dto.getId());
					if(order != null && order.getMemberId().equals(memberId)){
						// 4、保存评论
						commentService.add(dto);
						result = new ApiCodeDto(ApiCodeEnum.SUCCESS);
						// TODO
						// 5、还有一件重要的事未做
					}else{
						result = new ApiCodeDto(ApiCodeEnum.NO_AUTH);
					}
				}else {
					result = new ApiCodeDto(ApiCodeEnum.NOT_LOGIN);
				}
			return result;
	}
	
//	@RequestMapping(value="/detail/comment/{page}/{id}",method=RequestMethod.GET)
//	public CommentListDto detailComment() throws JsonParseException, JsonMappingException, IOException{
//		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
//		String data = "{\"hasMore\":true,\"data\":[{\"username\":\"133****3355\",\"comment\":\"非常好吃，棒棒的，下次再来\",\"star\":5},{\"username\":\"135****3452\",\"comment\":\"羊肉够分量，不错\",\"star\":4},{\"username\":\"137****1242\",\"comment\":\"有自助的水果，非常喜欢\",\"star\":4},{\"username\":\"131****3980\",\"comment\":\"桌子环境有点糟糕，不喜欢\",\"star\":2},{\"username\":\"135****3565\",\"comment\":\"基本还可以，中规中矩吧，虽然没有啥惊喜\",\"star\":3},{\"username\":\"130****9879\",\"comment\":\"感觉很棒，服务员态度非常好\",\"star\":5},{\"username\":\"186****7570\",\"comment\":\"要是能多给开点发票就好了，哈哈啊\",\"star\":4}]}";
//		return mapper.readValue(data, new TypeReference<CommentListDto >() {});
//	}
}
