package com.cd.zj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cd.zj.bean.AreaInfo;

@Mapper
public interface OrgAndAreaMapper {

	List<Map<String, Object>> selectOrg();

	List<AreaInfo> selectRegion();

	List<Map<String, Object>> relateExam();
}
