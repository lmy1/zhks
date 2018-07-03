package com.cd.zhks.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.cd.zhks.bean.dto.PasswordDto;
import com.cd.zhks.bean.dto.ReviewStatusDto;
import com.cd.zhks.bean.query.PageBean;
import com.cd.zhks.bean.query.UserQuery;
import com.cd.zhks.bean.vo.UserVo;
import com.cd.zhks.dao.OrgDao;
import com.cd.zhks.dao.UserDao;
import com.cd.zhks.dao.UserRoleDao;
import com.cd.zhks.entity.Org;
import com.cd.zhks.entity.Role;
import com.cd.zhks.entity.User;
import com.cd.zhks.entity.UserRole;
import com.cd.zhks.service.exception.InspectionException;
import com.cd.zhks.service.exception.LogicException;
import com.cd.zhks.utils.AESUtils;
import com.cd.zhks.utils.LoginUserUtils;
import com.cd.zhks.utils.MD5Utils;
import com.cd.zhks.utils.ValidateUtil;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private OrgDao orgDao;

	@Autowired
    private RestTemplate restTemplate;

	private Logger log = LoggerFactory.getLogger(UserService.class);

	@Transactional
	public Long addUser(User user) throws Exception {
		// orgCode转orgId
		user.getOrg().setId(orgCodeToId(user.getOrg().getOrgCode()));

		if (userDao.existsByTelephone(user.getTelephone())) {
			throw new InspectionException("手机号已存在");
		}
		if (userDao.existsByIdCard(user.getIdCard())) {
			throw new InspectionException("该身份证号已存在");
		}
		// 身份证校验
		if (!ValidateUtil.isIdCard01(user.getIdCard()))
			throw new InspectionException("该身份证号格式不正确");
		user.setCreatedDate(new Date());
		// 当前登录者id设为创建者id
		User loginUser = getLoginUser();
		user.setCreatedUserId(loginUser.getId());
		
		//所在单位判断
		if((!isJkzx())&&!(user.getOrg().getOrgCode().equals(loginUser.getOrg().getOrgCode()))){
			throw new InspectionException("只能选择自己单位");
		}
		user.setPassword(MD5Utils.md5Digest("88888888"));
		user.setReviewStatus(1L);
		Long id = userDao.save(user).getId();
		// 保存关联表
		addOrUpdateOrDeleteUserRoles(id, user.getRoles());
		return id;
	}
	/**
	 * 获取当前登录用户
	 * @throws Exception 
	 */
	private User getLoginUser() throws Exception {
		// 当前登录者id设为创建者id
		Map<String, Object> userInfoMap = LoginUserUtils.appUserInfo();
		String loginId = (String) userInfoMap.get("id");
		if (StringUtils.isEmpty(loginId))
			throw new InspectionException("获取登录者id为空");
		User user = userDao.findOne(Long.valueOf(loginId));
		if (user == null)
			throw new InspectionException("用户不存在");
		return user;
	}

	
	/**
	 * 是否为镇海疾控中心
	 * @throws Exception 
	 */
	private boolean isJkzx() throws Exception {
		User user = getLoginUser();
		String orgCode = user.getOrg().getOrgCode();
		if("330211011".equals(orgCode))
			return true;
		return false;
	}


	/**
	 * orgCode转orgId
	 * 
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	private Long orgCodeToId(String orgCode) throws Exception {
		Org org = orgDao.findByOrgCode(orgCode);
		return org.getId();
	}

	/**
	 * 关联表维护
	 * 
	 * @param id
	 * @param roles
	 * @throws InspectionException
	 */
	private void addOrUpdateOrDeleteUserRoles(Long id, Set<Role> roles) throws InspectionException {
		List<Long> roleIds = new ArrayList<Long>();
		if (roles.size() > 0) {
			Iterator<Role> it = roles.iterator();
			while (it.hasNext()) {
				roleIds.add(it.next().getId());
			}
		}
		updateUserRoles(id, roleIds);
	}

	@Transactional
	public void updateUser(Long id, User user) throws Exception {
		// orgCode转orgId
		user.getOrg().setId(orgCodeToId(user.getOrg().getOrgCode()));

		User userOld = userDao.findOne(id);
		if (userDao.existsByTelephoneAndIdNot(user.getTelephone(), id)) {
			throw new InspectionException("手机号已存在");
		}
		if (userDao.existsByIdCardAndIdNot(user.getIdCard(), id)) {
			throw new InspectionException("该身份证号已存在");
		}
		userOld.setName(user.getName());
		userOld.setRoles(user.getRoles());
		userOld.setTelephone(user.getTelephone());
		userOld.setSex(user.getSex());
		userOld.setOrg(user.getOrg());
		user.setId(id);
		// 保存关联表
		addOrUpdateOrDeleteUserRoles(id, user.getRoles());
		userDao.save(userOld);
	}

	@Transactional
	public void deleteUser(Long id) throws InspectionException, LogicException {
		User user = userDao.findOne(id);
		if(user==null)throw new InspectionException("用户不存在");
		if(!canDelete(id)) throw new LogicException("用户不允许删除");
		userDao.delete(user);
		// 删除关联表
		addOrUpdateOrDeleteUserRoles(user.getId(), user.getRoles());
	}
	
    public boolean canDelete(Long id){
 
    	boolean canDelete=restTemplate.getForObject("http://zhks-web/count/countNum/"+id, 
                                   Boolean.class);
        
        return canDelete;
      }

	@Transactional
	public UserVo getUser(Long id) throws InspectionException {
		User user = userDao.findOne(id);
		if (user == null)
			throw new InspectionException("用户不存在");
		UserVo userVo = copyProperties(user);
		return userVo;
	}

	@Transactional
	public void updateUserRoles(Long userId, List<Long> roleIds) throws InspectionException {
		List<UserRole> userRoles = userRoleDao.findByUserId(userId);
		userRoleDao.deleteInBatch(userRoles);

		List<UserRole> newUserRoles = new java.util.ArrayList<UserRole>();
		for (Long roleId : roleIds) {
			UserRole userRole = new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);

			newUserRoles.add(userRole);
		}

		userRoleDao.save(newUserRoles);
	}

	public PageBean<UserVo> findUserByConditions(UserQuery userQuery) throws Exception {
		User loginUser = getLoginUser();
		String orgCode=loginUser.getOrg().getOrgCode();
	
		// 创建分页对象
		PageRequest pageRequest = null;
		// 创建查询结果集对象
		PageBean<UserVo> pageBean = new PageBean<>();
		// 创建条件查询对象
		Specification<User> specification = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 创建一个集合，将所有创建好的条件存进集合，一次查询。
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (!StringUtils.isEmpty(userQuery.getQueryCondition())) {// 姓名，身份证号
					Predicate or1 = cb.like(root.get("name"), "%" + userQuery.getQueryCondition() + "%");
					Predicate or2 = cb.like(root.get("idCard"), "%" + userQuery.getQueryCondition() + "%");
					Predicate predicate1 = cb.or(or1, or2);
					predicateList.add(predicate1);
				}
				if (!StringUtils.isEmpty(userQuery.getRoleName())) {// 角色名
					SetJoin<User, Role> join = root.join(root.getModel().getSet("roles", Role.class), JoinType.LEFT);
					Predicate predicate2 = cb.like(join.get("roleName"), "%" + userQuery.getRoleName() + "%");
					predicateList.add(predicate2);
				}
				if (!StringUtils.isEmpty(userQuery.getOrgCode())) {// 所在单位
					Predicate predicate3 = cb.equal(root.get("org").get("orgCode"), userQuery.getOrgCode());
					predicateList.add(predicate3);
				}
				if(!"330211011".equals(orgCode)) {
					Predicate predicate4 = cb.equal(root.get("org").get("orgCode"), orgCode);
					predicateList.add(predicate4);
				}
				//当前登录用户不在分页中显示
				Predicate predicate5 = cb.notEqual(root.get("id"), loginUser.getId());
				predicateList.add(predicate5);
				// 将条件集合转换为数组并返回
				Predicate[] p = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(p));
			}
		};

		// 倒序排序
		Sort sort = new Sort(Sort.Direction.DESC, "id");

		if (userQuery.getPage() > 0 && userQuery.getSize() > 0) {
			// 如果是分页条件查询
			pageRequest = new PageRequest(userQuery.getPage() - 1, userQuery.getSize(), sort);
			Page<User> pageInfo = userDao.findAll(specification, pageRequest);
			List<User> content = pageInfo.getContent();
			List<UserVo> contentVo = new ArrayList<UserVo>();
			for (User userSource : content) {
				UserVo userVo = copyProperties(userSource);
				contentVo.add(userVo);
			}
			pageBean.setItems(contentVo);
			pageBean.setTotalNum(pageInfo.getTotalElements());
		} else {
			// 如果是条件查询
			List<User> findAll = userDao.findAll(specification, sort);
			// 转化属性给前端
			List<UserVo> findAllVo = new ArrayList<UserVo>();
			for (User userSource : findAll) {

				UserVo userVo = copyProperties(userSource);

				findAllVo.add(userVo);
			}

			pageBean.setItems(findAllVo);
			pageBean.setTotalNum((long) findAllVo.size());

		}

		return pageBean;
	}

	/**
	 * 类属性转换及拷贝给前端
	 * 
	 * @param userSource
	 * @return
	 */
	private UserVo copyProperties(User userSource) {
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(userSource, userVo);
		// 机构转化
		if (userSource.getOrg() != null) {
			userVo.setOrgCode(userSource.getOrg().getOrgCode());
			userVo.setOrgName(userSource.getOrg().getOrgName());
		}
		// 遍历获得角色信息
		Set<Role> roles = userSource.getRoles();
		if (roles.size() > 0) {
			Iterator<Role> it = roles.iterator();
			Long[] roleIds = new Long[roles.size()];
			String[] roleNames = new String[roles.size()];
			int index = 0;
			while (it.hasNext()) {
				Role role = (Role) it.next();
				roleIds[index] = role.getId();
				roleNames[index] = role.getRoleName();
				index++;
			}
			userVo.setRoleIds(roleIds);// 角色id数组
			userVo.setRoleNames(roleNames);
		}

		if (!StringUtils.isEmpty(userVo.getSex())) {// 性别转中文
			if (userVo.getSex() == 0L)
				userVo.setSexName("女");
			if (userVo.getSex() == 1L)
				userVo.setSexName("男");
		}
		if (!StringUtils.isEmpty(userVo.getReviewStatus())) {// 审核状态转中文
			if (userVo.getReviewStatus() == 0L)
				userVo.setReviewStatusName("审核");
			if (userVo.getReviewStatus() == 1L)
				userVo.setReviewStatusName("通过");
			if (userVo.getReviewStatus() == 2L)
				userVo.setReviewStatusName("未通过");
		}
		return userVo;
	}

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param password
	 * @throws Exception
	 */
	public void updatePasswordById(PasswordDto passwordDto) throws Exception {
		User user = getLoginUser();
		if(!user.getPassword().equals(MD5Utils.md5Digest(AESUtils.desEncrypt(passwordDto.getOldPassword()))))
			throw new InspectionException("旧密码错误");
		// 密码加解密
		user.setPassword(MD5Utils.md5Digest(AESUtils.desEncrypt(passwordDto.getNewPassword())));
		userDao.save(user);
	}

	/**
	 * 重置密码
	 * 
	 * @param id
	 * @throws NoSuchAlgorithmException
	 * @throws InspectionException
	 */
	public void resetPasswordById(Long id) throws NoSuchAlgorithmException, InspectionException {
		User user = userDao.findOne(id);
		if (user == null)
			throw new InspectionException("用户不存在");
		user.setPassword(MD5Utils.md5Digest("88888888"));
		userDao.save(user);
	}

	/**
	 * 修改用户审核状态
	 * 
	 * @param id
	 * @param reviewStatus
	 * @throws InspectionException
	 */
	public void updatereviewStatusById(Long id, ReviewStatusDto reviewStatus) throws InspectionException {
		User user = userDao.findOne(id);
		if (user == null)
			throw new InspectionException("用户不存在");
		user.setReviewStatus(reviewStatus.getReviewStatus());
		user.setUnreviewReason(reviewStatus.getUnreviewReason());
		userDao.save(user);
	}

}