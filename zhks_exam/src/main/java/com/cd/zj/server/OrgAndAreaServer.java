package com.cd.zj.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.zj.bean.AreaInfo;
import com.cd.zj.bean.result.TreeNode;
import com.cd.zj.dao.OrgAndAreaDao;

@Service
public class OrgAndAreaServer {

	@Autowired
	private OrgAndAreaDao orgAndAreaDao;

	/**
	 * 查询机构下拉列表
	 * @return
	 */
	public List<Map<String, Object>> relateOrg() throws Exception{
		List<Map<String, Object>> mapper=orgAndAreaDao.selectOrg();
		return mapper;
	}

	/**
	 * 地区关联下拉
	 * @return
	 */
	public List<TreeNode> relateArea() throws Exception{
		List<TreeNode> list = new ArrayList<TreeNode>();
		List<AreaInfo> selectRegion = orgAndAreaDao.selectRegion();
		for(AreaInfo areaInfo :selectRegion){
			if(areaInfo.getPcode().equals("330211")) {
				TreeNode node = new TreeNode();
				node.setValue(areaInfo.getCode());
				node.setLabel(areaInfo.getName());
				list.add(node);
			}
		}
		ConversionCascade(list,selectRegion);
		return list;
	}

	/**
	 *循环遍历地区
	 * @param list
	 * @param areaInfos
	 */
	private void ConversionCascade( List<TreeNode> list,List<AreaInfo> areaInfos) throws Exception{
		for(TreeNode treeNode : list) {
			List<TreeNode> nodes = new ArrayList<TreeNode>();
			for(AreaInfo areaInfo : areaInfos) {
				if(areaInfo.getPcode().equals(treeNode.getValue())) {
					TreeNode node = new TreeNode();
					node.setValue(areaInfo.getCode());
					node.setLabel(areaInfo.getName());
					nodes.add(node);
				}
			}
			treeNode.setChildren(nodes);
			if(!nodes.isEmpty()) {
				ConversionCascade(nodes,areaInfos);
			}
		}
	}

	/**
	 * 试卷下拉框
	 * @return
	 */
	public List<Map<String, Object>> relateExam() {
		List<Map<String, Object>> mapper=orgAndAreaDao.relateExam();
		return mapper;
	}
}
