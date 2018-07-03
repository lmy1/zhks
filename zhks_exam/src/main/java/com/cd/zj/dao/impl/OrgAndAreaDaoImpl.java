package com.cd.zj.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.bean.AreaInfo;
import com.cd.zj.dao.OrgAndAreaDao;
import com.cd.zj.mapper.OrgAndAreaMapper;
@Component
public class OrgAndAreaDaoImpl implements OrgAndAreaDao {

	@Autowired
	private OrgAndAreaMapper orgAndAreaMapper;

	@Override
	public List<Map<String, Object>> selectOrg() {
		return orgAndAreaMapper.selectOrg();
	}

	@Override
	public List<AreaInfo> selectRegion() {
		return orgAndAreaMapper.selectRegion();
	}

	@Override
	public List<Map<String, Object>> relateExam() {
		return orgAndAreaMapper.relateExam();
	}
}
