package com.cd.zj.dao;

import java.util.List;
import java.util.Map;

import com.cd.zj.entity.TblTopic;

public interface TblTopicDao {

	List<TblTopic> findTopic(Map<String, Object> map);

}
