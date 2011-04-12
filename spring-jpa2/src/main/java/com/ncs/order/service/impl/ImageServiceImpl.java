package com.ncs.order.service.impl;

import com.ncs.order.dao.ImageDao;
import com.ncs.order.service.ImageService;
import com.ncs.order.to.ImageTo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageTo, String> implements ImageService{

    @Resource
	public void setBaseDao(ImageDao imageDao) {
		super.setBaseDao(imageDao);
	}

}
