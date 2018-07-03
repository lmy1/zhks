package com.cd.zhks.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cd.zhks.bean.query.*;
import com.cd.zhks.dao.*;
import com.cd.zhks.entity.*;
import com.cd.zhks.service.exception.InspectionException;

@Service
public class OrgService {

	@Autowired
	private OrgDao orgDao;
    

	private Logger log = LoggerFactory.getLogger(OrgService.class);

    @Transactional
    public Long addOrg(Org org) throws InspectionException {
        return orgDao.save(org).getId();
	}

    @Transactional
    public void updateOrg(Long id, Org org) throws InspectionException {

        org.setId(id);
        orgDao.save(org);
	}

    @Transactional
    public void deleteOrgs(List<Long> ids) throws InspectionException {
        List<Org> orgs = orgDao.findAll(ids);
        orgDao.deleteInBatch(orgs);
	}

    @Transactional
    public Org getOrg(Long id) throws InspectionException {
        Org org= orgDao.findOne(id);
        org.getUsers().size();

		return org;
	}

    

    
}