package com.me.comment.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.comment.bean.Ad;
import com.me.comment.bean.Page;
import com.me.comment.dto.AdDto;
import com.me.comment.dto.BusinessDto;
import com.me.comment.dto.BusinessListDto;
import com.me.comment.dto.CommentListDto;
import com.me.comment.dto.OrdersDto;
import com.me.comment.service.AdService;

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
	
	@Autowired
	private AdService adService;
	
	@RequestMapping(value="/homead",method=RequestMethod.GET)
	public List<AdDto> homead() throws JsonParseException, JsonMappingException, IOException{
		AdDto adDto = new AdDto();
		adDto.getPage().setPageNumber(adNumber);
		return adService.searchByPage(adDto);
	}
	
	@RequestMapping(value="/homelist/{city}/{page}",method=RequestMethod.GET)
	public BusinessListDto recommend() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "{\"hasMore\":true,\"data\":[{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201638030-473660627.png\",\"title\":\"汉堡大大\",\"subtitle\":\"叫我汉堡大大，还你多彩口味\",\"price\":\"28\",\"distance\":\"120\",\"mumber\":\"389\",\"id\":\"9423302517065029\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"北京开源饭店\",\"subtitle\":\"[望京]自助晚餐\",\"price\":\"98\",\"distance\":\"140\",\"mumber\":\"689\",\"id\":\"8301701486684985\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201652952-1050532278.png\",\"title\":\"服装定制\",\"subtitle\":\"原价xx元，现价xx元，可修改一次\",\"price\":\"1980\",\"distance\":\"160\",\"mumber\":\"106\",\"id\":\"8294663391241919\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201700186-1351787273.png\",\"title\":\"婚纱摄影\",\"subtitle\":\"免费试穿，拍照留念\",\"price\":\"2899\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"6670096153796652\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201708124-1116595594.png\",\"title\":\"麻辣串串烧\",\"subtitle\":\"双人免费套餐等你抢购\",\"price\":\"0\",\"distance\":\"160\",\"mumber\":\"1426\",\"id\":\"5215631363066944\"}]}";
		return mapper.readValue(data, new TypeReference<BusinessListDto>() {});
	}
	
	@RequestMapping(value="/submitComment",method=RequestMethod.POST)
	public Map<String, Object> submitComment() throws JsonParseException, JsonMappingException, IOException{
		Map<String, Object> map = new HashMap<>();
		map.put("errno", 0);
		map.put("msg", "ok");
		return map;
	}
	
	@RequestMapping(value="/search/{page}/{city}/{category}/{keyword}",method=RequestMethod.GET)
	public BusinessListDto search() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "{\"hasMore\":true,\"data\":[{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145742279-606202974.jpg\",\"title\":\"河束人家\",\"subtitle\":\"南锣鼓巷店\",\"price\":\"150\",\"distance\":\"120\",\"mumber\":\"389\",\"id\":\"7009382062828036\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145750123-1745839503.jpg\",\"title\":\"漫漫火锅\",\"subtitle\":\"[王府井]自助火锅\",\"price\":\"113\",\"distance\":\"140\",\"mumber\":\"689\",\"id\":\"11394416361135318\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145755545-1770557408.jpg\",\"title\":\"北方涮肉\",\"subtitle\":\"什刹海店\",\"price\":\"92\",\"distance\":\"160\",\"mumber\":\"106\",\"id\":\"5027210817931369\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145800732-576947550.jpg\",\"title\":\"姓高火锅\",\"subtitle\":\"知春里店\",\"price\":\"90\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"28071524371750955\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145806201-1193851669.jpg\",\"title\":\"八重牛府\",\"subtitle\":\"最好吃的牛丸\",\"price\":\"85\",\"distance\":\"160\",\"mumber\":\"1426\",\"id\":\"7663391818094032\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022150855185-1659375763.jpg\",\"title\":\"蜀乡涮锅\",\"subtitle\":\"[王府井]自助火锅\",\"price\":\"113\",\"distance\":\"140\",\"mumber\":\"689\",\"id\":\"3961761728157611\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145800732-576947550.jpg\",\"title\":\"满楼福火锅\",\"subtitle\":\"知春路店\",\"price\":\"90\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"563566186111826\"}]}";
		return mapper.readValue(data, new TypeReference<BusinessListDto>() {});
	}
	
	@RequestMapping(value="/search/{page}/{city}/{category}",method=RequestMethod.GET)
	public BusinessListDto search2() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "{\"hasMore\":true,\"data\":[{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145742279-606202974.jpg\",\"title\":\"河束人家\",\"subtitle\":\"南锣鼓巷店\",\"price\":\"150\",\"distance\":\"120\",\"mumber\":\"389\",\"id\":\"7009382062828036\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145750123-1745839503.jpg\",\"title\":\"漫漫火锅\",\"subtitle\":\"[王府井]自助火锅\",\"price\":\"113\",\"distance\":\"140\",\"mumber\":\"689\",\"id\":\"11394416361135318\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145755545-1770557408.jpg\",\"title\":\"北方涮肉\",\"subtitle\":\"什刹海店\",\"price\":\"92\",\"distance\":\"160\",\"mumber\":\"106\",\"id\":\"5027210817931369\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145800732-576947550.jpg\",\"title\":\"姓高火锅\",\"subtitle\":\"知春里店\",\"price\":\"90\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"28071524371750955\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145806201-1193851669.jpg\",\"title\":\"八重牛府\",\"subtitle\":\"最好吃的牛丸\",\"price\":\"85\",\"distance\":\"160\",\"mumber\":\"1426\",\"id\":\"7663391818094032\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022150855185-1659375763.jpg\",\"title\":\"蜀乡涮锅\",\"subtitle\":\"[王府井]自助火锅\",\"price\":\"113\",\"distance\":\"140\",\"mumber\":\"689\",\"id\":\"3961761728157611\"},{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161022145800732-576947550.jpg\",\"title\":\"满楼福火锅\",\"subtitle\":\"知春路店\",\"price\":\"90\",\"distance\":\"160\",\"mumber\":\"58\",\"id\":\"563566186111826\"}]}";
		return mapper.readValue(data, new TypeReference<BusinessListDto>() {});
	}
	
	@RequestMapping(value="/detail/info/{id}",method=RequestMethod.GET)
	public BusinessDto detail() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"天下第一锅\",\"star\":4,\"price\":\"88\",\"subtitle\":\"重庆 & 四川 麻辣火锅\",\"desc\":\"营业时间 11:00 - 21:00 <br> 电话订购 11:00 - 19:00 <br> 网络订购 11:00 - 19:00\"}{\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"天下第一锅\",\"star\":4,\"price\":\"88\",\"subtitle\":\"重庆 & 四川 麻辣火锅\",\"desc\":\"营业时间 11:00 - 21:00 <br> 电话订购 11:00 - 19:00 <br> 网络订购 11:00 - 19:00\"}";
		return mapper.readValue(data, new TypeReference<BusinessDto>() {});
	}
	
	@RequestMapping(value="/detail/comment/{page}/{id}",method=RequestMethod.GET)
	public CommentListDto detailComment() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "{\"hasMore\":true,\"data\":[{\"username\":\"133****3355\",\"comment\":\"非常好吃，棒棒的，下次再来\",\"star\":5},{\"username\":\"135****3452\",\"comment\":\"羊肉够分量，不错\",\"star\":4},{\"username\":\"137****1242\",\"comment\":\"有自助的水果，非常喜欢\",\"star\":4},{\"username\":\"131****3980\",\"comment\":\"桌子环境有点糟糕，不喜欢\",\"star\":2},{\"username\":\"135****3565\",\"comment\":\"基本还可以，中规中矩吧，虽然没有啥惊喜\",\"star\":3},{\"username\":\"130****9879\",\"comment\":\"感觉很棒，服务员态度非常好\",\"star\":5},{\"username\":\"186****7570\",\"comment\":\"要是能多给开点发票就好了，哈哈啊\",\"star\":4}]}";
		return mapper.readValue(data, new TypeReference<CommentListDto >() {});
	}
	
	@RequestMapping(value="/orderlist/{username}",method=RequestMethod.GET)
	public List<OrdersDto> orderlist() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();//强大的json解析工具
		String data = "[{\"id\":1503079062627,\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201638030-473660627.png\",\"title\":\"汉堡大王\",\"count\":3,\"price\":\"167\",\"commentState\":0},{\"id\":1503079062627,\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201708124-1116595594.png\",\"title\":\"麻辣香锅\",\"count\":1,\"price\":\"188\",\"commentState\":0},{\"id\":1503079062627,\"img\":\"http://images2015.cnblogs.com/blog/138012/201610/138012-20161016201645858-1342445625.png\",\"title\":\"好吃自出餐\",\"count\":2,\"price\":\"110\",\"commentState\":2}]";
		return mapper.readValue(data, new TypeReference<List<OrdersDto>>() {});
	}
	
	@RequestMapping(value="/order",method=RequestMethod.POST)
	public Map<String, Object> order() throws JsonParseException, JsonMappingException, IOException{
		Map<String, Object> map = new HashMap<>();
		map.put("errno", 0);
		map.put("msg", "buy ok");
		return map;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Map<String, Object> login() throws JsonParseException, JsonMappingException, IOException{
		Map<String, Object> map = new HashMap<>();
		map.put("errno", 0);
		map.put("msg", "buy ok");
		map.put("token", "asdasasdaslkdaslkndasnlkkdmkfdsa");
		return map;
	}
	
	@RequestMapping(value="/sms",method=RequestMethod.POST)
	public Map<String, Object> sms() throws JsonParseException, JsonMappingException, IOException{
		Map<String, Object> map = new HashMap<>();
		map.put("errno", 0);
		map.put("msg", "ok");
		map.put("code", "md5(123456)");
		return map;
	}
}
