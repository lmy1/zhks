package com.cd.zj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cd.zj.entity.TblTopic;

@Mapper
public interface TblTopicMapper {

	List<TblTopic> findTopic(Map<String, Object> map);

}
