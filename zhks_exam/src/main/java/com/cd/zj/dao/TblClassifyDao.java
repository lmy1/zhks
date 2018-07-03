package com.cd.zj.dao;

import java.util.List;
import java.util.Map;

import com.cd.zj.entity.TblClassify;

public interface TblClassifyDao {

	List<TblClassify> relateClassify(Map<String, String> map);

	List<Map<String, Object>> firstLevelClassify(Map<String, String> map);

	List<Map<String, Object>> secondLevelClassify(Map<String, String> map);

	int isUsed(String id);

}
