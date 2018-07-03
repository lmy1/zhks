package com.cd.zj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cd.zj.entity.TblClassify;

@Mapper
public interface TblClassifyMapper {

	List<TblClassify> relateClassify(Map<String, String> map);

	List<Map<String, Object>> firstLevelClassify(Map<String, String> map);

	List<Map<String, Object>> secondLevelClassify(Map<String, String> map);

	int isUsed(String id);
}
