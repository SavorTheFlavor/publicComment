package com.me.comment.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.me.comment.bean.Ad;
import com.me.comment.dao.AdDao;
import com.me.comment.dto.AdDto;
import com.me.comment.service.AdService;

@Service
public class AdServiceImpl implements AdService {

	@Autowired
	private AdDao adDao;
	
	@Value("${adImageSavePath}")
	private String adImageSavePath;

	@Value("${adImageUrl}")
	private String adImageUrl;
	
	@Override
	//TODO 可以提供更详细的返回信息
	public boolean add(AdDto adDto) {
		Ad ad = new Ad();
		ad.setTitle(adDto.getTitle());
		ad.setLink(adDto.getLink());
		ad.setWeight(adDto.getWeight());
		if(adDto.getImgFile() != null && adDto.getImgFile().getSize() > 0){
			String filename = System.currentTimeMillis() + "_" + adDto.getImgFile().getOriginalFilename();
			File folder = new File(adImageSavePath);
			System.out.println("adImageSavePath----------------------------->"+adImageSavePath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			File file = new File(adImageSavePath+filename);
			try {
				adDto.getImgFile().transferTo(file);
				ad.setImgFileName(filename);
				adDao.insert(ad);
				return true;
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public List<AdDto> searchByPage(AdDto adDto) {
		Ad condition = new Ad();
		BeanUtils.copyProperties(adDto, condition);//把名称相同的属性复制过去
		
		List<AdDto> result = new ArrayList<>();
		List<Ad> adList = adDao.selectByPage(condition);
		for (Ad ad : adList) {
			AdDto adDto2 = new AdDto();
			result.add(adDto2);
			BeanUtils.copyProperties(ad, adDto2);
			adDto2.setImg(adImageUrl+ad.getImgFileName());
		}
		return result;
	}

}
