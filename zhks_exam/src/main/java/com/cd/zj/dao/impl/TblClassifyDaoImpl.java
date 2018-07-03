package com.cd.zj.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.dao.TblClassifyDao;
import com.cd.zj.entity.TblClassify;
import com.cd.zj.mapper.TblClassifyMapper;
@Component
public class TblClassifyDaoImpl implements TblClassifyDao{

	@Autowired
	private TblClassifyMapper tblClassifyMapper;

	@Override
	public List<TblClassify> relateClassify(Map<String, String> map) {
		return tblClassifyMapper.relateClassify(map);
	}

	@Override
	public List<Map<String, Object>> firstLevelClassify(Map<String, String> map) {
		return tblClassifyMapper.firstLevelClassify(map);
	}

	@Override
	public List<Map<String, Object>> secondLevelClassify(Map<String, String> map) {
		return tblClassifyMapper.secondLevelClassify(map);
	}

	@Override
	public int isUsed(String id) {
		return tblClassifyMapper.isUsed(id);
	}
}
