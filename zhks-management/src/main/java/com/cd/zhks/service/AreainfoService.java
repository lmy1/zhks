package com.cd.zhks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cd.zhks.dao.AreainfoDao;
import com.cd.zhks.entity.Areainfo;
import com.cd.zhks.service.exception.InspectionException;

@Service
public class AreainfoService {

	@Autowired
	private AreainfoDao areainfoDao;
    

	private Logger log = LoggerFactory.getLogger(AreainfoService.class);


    @Transactional
    public Areainfo getAreainfo(Long id) throws InspectionException {
        Areainfo areainfo= areainfoDao.findOne(id);

		return areainfo;
	}

    

    
}