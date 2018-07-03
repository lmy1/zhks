package com.cd.zj.dao;

import java.util.List;
import java.util.Map;

import com.cd.zj.bean.AreaInfo;

public interface OrgAndAreaDao {

	List<Map<String, Object>> selectOrg();

	List<AreaInfo> selectRegion();

	List<Map<String, Object>> relateExam();
}
