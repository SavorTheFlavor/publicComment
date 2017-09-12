package com.me.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.me.comment.bean.Business;
import com.me.comment.dao.BusinessDao;
import com.me.comment.dto.BusinessDto;
import com.me.comment.dto.BusinessListDto;
import com.me.comment.service.BusinessService;
import com.me.comment.util.FileUtil;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private BusinessDao businessDao;
	
	@Value("${businessImage.savePath}")
	private String savePath;
	
	@Value("${businessImage.url}")
	private String url;
	
	@Override
	public boolean add(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		try {
			if(businessDto.getImgFile() != null && businessDto.getImgFile().getSize() > 0){
				String filename = FileUtil.save(businessDto.getImgFile(), savePath);
				business.setImgFileName(filename);
				business.setCommentTotalNum(0L);
				business.setNumber(0);
				business.setStarTotalNum(0L);
				businessDao.insert(business);
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public BusinessListDto searchByPageForApi(BusinessDto businessDto) {
		//TODO
		return null;
	}

	@Override
	public List<BusinessDto> searchByPage(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		List<Business> list = businessDao.selectByPage(business);
		List<BusinessDto> list2 = new ArrayList<>();
		for (Business b : list) {
			BusinessDto dto = new BusinessDto();
			BeanUtils.copyProperties(b, dto);
			dto.setImg(url+dto.getImgFileName());
			dto.setStar(this.getStar(b));
			list2.add(dto);
		}
		return list2;
	}
	
	private int getStar(Business business) {
		if(business.getStarTotalNum() != null && business.getCommentTotalNum() != null && business.getCommentTotalNum() != 0) {
			return (int)(business.getStarTotalNum() / business.getCommentTotalNum());
		} else {
			return 0;
		}
	}

	@Override
	public BusinessDto findById(Long id) {
		Business business = businessDao.selectById(id);
		if(business == null){
			return null;
		}
		BusinessDto dto = new BusinessDto();
		BeanUtils.copyProperties(business, dto);
		dto.setImg(url+business.getImgFileName());
		return dto;
	}

	@Override
	public boolean update(BusinessDto dto) {
		try {
			Business business = new Business();
			BeanUtils.copyProperties(dto, business);
			
			if(dto.getImgFile()!=null){
				String filename = FileUtil.save(dto.getImgFile(), savePath);
				business.setImgFileName(filename);
				//TODO	删除图片操作
			}
			
			int effRow  = businessDao.update(business);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Long id) {
		businessDao.deleteById(id);
		return false;
	}


}
