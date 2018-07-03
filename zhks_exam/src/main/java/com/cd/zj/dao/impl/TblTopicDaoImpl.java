package com.cd.zj.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.dao.TblTopicDao;
import com.cd.zj.entity.TblTopic;
import com.cd.zj.mapper.TblTopicMapper;

@Component
public class TblTopicDaoImpl implements TblTopicDao {

	@Autowired
	private TblTopicMapper tblTopicMapper;

	@Override
	public List<TblTopic> findTopic(Map<String, Object> map) {
		return tblTopicMapper.findTopic(map);
	}
}
