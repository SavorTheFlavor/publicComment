package com.me.comment.service.impl;

import java.io.File;
import java.io.IOException;

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

}
