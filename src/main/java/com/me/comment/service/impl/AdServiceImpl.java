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
import com.me.comment.util.FileUtil;

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
		try {
			String filename = FileUtil.save(adDto.getImgFile(), adImageSavePath);
			if(filename == null){
				return false;
			}
			ad.setImgFileName(filename);
			adDao.insert(ad);
			return true;
		} catch (IllegalStateException | IOException e) {
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

	@Override
	public boolean delete(Long id) {
		try {
			adDao.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean modify(AdDto adDto) {
		Ad ad = new Ad();
		String filename  = null;
		BeanUtils.copyProperties(adDto, ad);
		try{
			if(adDto.getImgFile() != null && adDto.getImgFile().getSize() > 0){
				//保存新的图片
				filename = FileUtil.save(adDto.getImgFile(), adImageSavePath);
				ad.setImgFileName(filename);
			}
			if(adDao.update(ad)!=1){
				return false;	
			}
			return true;
		}catch(Exception e){
			//TODO log info
		}
		//最后删除原来的图片,如果图片保存成功
		if(filename != null){
			return FileUtil.delete(adImageSavePath+adDto.getImgFileName());
		}
		return true;
	}

	@Override
	public AdDto get(Long id) {
		Ad ad = adDao.get(id);
		AdDto adDto = new AdDto();
		BeanUtils.copyProperties(ad, adDto);
		adDto.setImg(adImageUrl+ad.getImgFileName());
		return adDto;
	}

}
