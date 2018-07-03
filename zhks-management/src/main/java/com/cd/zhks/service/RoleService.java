package com.cd.zhks.service;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;

import com.cd.zhks.bean.OrgCode;
import com.cd.zhks.dao.UserRoleDao;
import com.cd.zhks.entity.*;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.cd.zhks.bean.dto.RoleDto;
import com.cd.zhks.bean.query.PageBean;
import com.cd.zhks.bean.query.RoleQuery;
import com.cd.zhks.bean.vo.RoleVo;
import com.cd.zhks.dao.RoleAuthorityDao;
import com.cd.zhks.dao.RoleDao;
import com.cd.zhks.dao.UserDao;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleAuthorityDao roleAuthorityDao;


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    private Logger log = LoggerFactory.getLogger(RoleService.class);

    @Transactional
    public Long addRole(RoleDto roleDto) throws Exception {
        // role名字唯一的校验
        this.hasRoleName2(roleDto.getRoleName());

        // 封装 创建时间，创建者id等参数
        Role role = new Role();
        role.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        role.setCreatedUserId(getLoginUser().getId());
        BeanUtils.copyProperties(roleDto, role);

        return roleDao.save(role).getId();
    }

    @Transactional
    public void updateRole(Long id, RoleDto role) throws Exception {

        validationId(id);

        // 当前用户只能看到 自己单位的角色
        User loginUser = getLoginUser();
        Long id1 = loginUser.getOrg().getId();

        // 根据 orgId, 得到 某个单位下面所有的userId
        List<User> byOrgAndId = userDao.findAllByOrg_Id(id1);

        List<Long> idList = new ArrayList<>();
        if (byOrgAndId.size() > 0 && byOrgAndId != null) {
            for (User user : byOrgAndId) {
                List<Role> list = roleDao.findByCreatedUserId(user.getId());
                for (Role role1 : list) {
                    idList.add(role1.getId());
                }
            }
        }
        boolean contains = idList.contains(id);

        if (!contains) {
            throw new Exception("您没有权限！");
        }

        String roleName = role.getRoleName();
        Long sort = role.getSort();

        Role oldRole = roleDao.findOne(id);

        String oldRoleRoleName = oldRole.getRoleName();
        Long oldRoleSort = oldRole.getSort();

        if (roleName.equals(oldRoleRoleName) && (sort == oldRoleSort)) {
            throw new Exception("请修改角色信息后，再保存！");
        }
        if (!roleName.equals(oldRoleRoleName)) {
            this.hasRoleName2(roleName);
        }
        oldRole.setRoleName(role.getRoleName());
        oldRole.setSort(role.getSort());
        roleDao.save(oldRole);
    }

    @Transactional
    public void deleteRole(Long id) throws Exception {

        validationId(id);

        // 当前用户只能看到 自己单位的角色
        User loginUser = getLoginUser();
        Long id1 = loginUser.getOrg().getId();

        // 根据 orgId, 得到 某个单位下面所有的userId
        List<User> byOrgAndId = userDao.findAllByOrg_Id(id1);

        List<Long> idList = new ArrayList<>();
        if (byOrgAndId.size() > 0 && byOrgAndId != null) {
            for (User user : byOrgAndId) {
                List<Role> list = roleDao.findByCreatedUserId(user.getId());
                for (Role role1 : list) {
                    idList.add(role1.getId());
                }
            }
        }
        boolean contains = idList.contains(id);

        if (!contains) {
            throw new Exception("您没有权限！");
        }

        this.validBeforeDeleteRole(id);
        Role one = roleDao.findOne(id);

        List<RoleAuthority> roleAuthoritys = roleAuthorityDao.findByRoleId(id);
        roleAuthorityDao.deleteInBatch(roleAuthoritys);
        User loginUser1 = getLoginUser();
        String name = loginUser1.getName();
        log.info("{} 删除了角色 {}", name, one.getRoleName());
        roleDao.delete(id);

    }

    @Transactional
    public RoleVo getRole(Long id) throws Exception {
        validationId(id);

        Role role = roleDao.findOne(id);

        RoleVo vo = new RoleVo();
        BeanUtils.copyProperties(role, vo);

        Set<Long> authorityIds = new HashSet<>();
        Set<Authority> authoritys = role.getAuthoritys();
        if (authoritys.size() > 0) {
            for (Authority authority : authoritys) {
                authorityIds.add(authority.getId());
            }
        }
        Long[] longs = authorityIds.toArray(new Long[authorityIds.size()]);
        vo.setAuthoritys(longs);
        return vo;
    }


    /**
     * 给角色设置权限
     */
    @Transactional
    public void updateRoleAuthoritys(Long roleId, Set<Long> authorityIds) throws Exception {

        this.validationId(roleId);

        // 当前用户只能看到 自己单位的角色
        User loginUser = getLoginUser();
        Long id1 = loginUser.getOrg().getId();

        // 根据 orgId, 得到 某个单位下面所有的userId
        List<User> byOrgAndId = userDao.findAllByOrg_Id(id1);

        List<Long> idList = new ArrayList<>();
        if (byOrgAndId.size() > 0 && byOrgAndId != null) {
            for (User user : byOrgAndId) {
                List<Role> list = roleDao.findByCreatedUserId(user.getId());
                for (Role role1 : list) {
                    idList.add(role1.getId());
                }
            }
        }
        boolean contains = idList.contains(roleId);

        if (!contains) {
            throw new Exception("您没有权限！");
        }


        for (Long authorityId : authorityIds) {
            if (authorityId < 1 || authorityId > 16) {
                throw new Exception("很抱歉，这个操作存在异常！！");
            }
        }

        List<RoleAuthority> roleAuthoritys = roleAuthorityDao.findByRoleId(roleId);
        roleAuthorityDao.deleteInBatch(roleAuthoritys);

        List<RoleAuthority> newRoleAuthoritys = new java.util.ArrayList<RoleAuthority>();
        for (Long authorityId : authorityIds) {
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(roleId);
            roleAuthority.setAuthorityId(authorityId);

            newRoleAuthoritys.add(roleAuthority);
        }

        roleAuthorityDao.save(newRoleAuthoritys);
    }

    /**
     * 查出当前用户的 所有角色。以及 所有权限
     */
    public Set<Long> getAuthorityList() throws Exception {
        User loginUser = this.getLoginUser();
        Set<Role> roles = loginUser.getRoles();
        Set<Long> hashSet = new HashSet<>();
        for (Role role : roles) {
            Set<Authority> authoritys = role.getAuthoritys();
            for (Authority authority : authoritys) {
                hashSet.add(authority.getId());
            }
        }
        return hashSet;
    }


    /**
     * 角色的条件分页查询
     */
    @Transactional
    public PageBean<RoleVo> getRoleList(RoleQuery roleQuery) throws Exception {

        String roleName = roleQuery.getRoleName();

        // 当前用户只能看到 自己单位的角色
        User loginUser = getLoginUser();
        Long id1 = loginUser.getOrg().getId();

        // 根据 orgId, 得到 某个单位下面所有的userId
        List<User> byOrgAndId = userDao.findAllByOrg_Id(id1);
        List<Long> userIdList = new ArrayList<>();
        for (User user : byOrgAndId) {
            userIdList.add(user.getId());
        }
        userIdList.add(1L);

        // 创建分页对象
        PageRequest pageRequest = null;
        // 创建查询结果集对象
        PageBean<Role> pageBean = new PageBean<>();

        // 创建条件查询对象
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 创建一个集合，将所有创建好的条件存进集合，一次查询。
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 条件查询
                if (null != roleName) {
                    Predicate p1 = cb.like(root.get("roleName"), "%" + roleName + "%");
                    predicateList.add(p1);
                }

                Predicate p3 = cb.and((root.get("createdUserId")).in(userIdList));
                predicateList.add(p3);

                // 将条件集合转换为数组并返回
                Predicate[] p = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(p));
            }
        };
        // 倒序排序
        Sort sort = new Sort(Sort.Direction.ASC, "sort");

        if (roleQuery.getPage() > 0 && roleQuery.getSize() > 0) {
            // 如果是分页条件查询
            pageRequest = new PageRequest(roleQuery.getPage() - 1, roleQuery.getSize(), sort);
            Page<Role> pageInfo = roleDao.findAll(specification, pageRequest);
            List<Role> content = pageInfo.getContent();

            pageBean.setItems(content);
            pageBean.setTotalNum(pageInfo.getTotalElements());
        } else {
            // 如果是条件查询
            List<Role> findAll = roleDao.findAll(specification, sort);

            pageBean.setItems(findAll);
            pageBean.setTotalNum((long) findAll.size());

        }

        List<Role> items = pageBean.getItems();

        // 封装权限数组
        List<RoleVo> voList = new ArrayList<>();
        for (Role role : items) {
            RoleVo vo = new RoleVo();
            BeanUtils.copyProperties(role, vo);
            Set<Authority> authoritys = role.getAuthoritys();
            Set<Long> authorityId = new HashSet<>();
            if (authoritys.size() > 0) {
                for (Authority authority : authoritys) {
                    Long id = authority.getId();
                    authorityId.add(id);
                }
            }
            Long[] longs = new Long[authorityId.size()];
            Long[] longs1 = authorityId.toArray(longs);
            vo.setAuthoritys(longs1);
            voList.add(vo);
        }
        PageBean<RoleVo> voPageBean = new PageBean<>();
        voPageBean.setItems(voList);

        voPageBean.setTotalNum(pageBean.getTotalNum());
        return voPageBean;
    }

    /**
     * 新增时，， 角色名字 唯一性的校验
     */
    public void hasRoleName2(String name) throws Exception {
        // 加自己单位的角色名字不能重复

        // 当前用户只能看到 自己单位的角色
        User loginUser = getLoginUser();
        Long id1 = loginUser.getOrg().getId();

        // 根据 orgId, 得到 某个单位下面所有的userId
        List<User> byOrgAndId = userDao.findAllByOrg_Id(id1);

        List<String> nameList = new ArrayList<>();
        if (byOrgAndId.size() > 0 && byOrgAndId != null) {
            for (User user : byOrgAndId) {
                List<Role> list = roleDao.findByCreatedUserId(user.getId());
                for (Role role : list) {
                    nameList.add(role.getRoleName());
                }
            }
        }

        Long orgId = OrgCode.DISEASE_CONTROL_CENTER.getOrgId();
        if (id1 != orgId) {
            List<User> byOrgAndId1 = userDao.findAllByOrg_Id(orgId);

            if (byOrgAndId1.size() > 0 && byOrgAndId1 != null) {
                for (User user : byOrgAndId1) {
                    List<Role> list = roleDao.findByCreatedUserId(user.getId());
                    for (Role role : list) {
                        nameList.add(role.getRoleName());
                    }
                }
            }

        }

        boolean contains = nameList.contains(name);

        // List<Role> byRoleName = roleDao.findByRoleName(name);
        if (contains) {
            throw new Exception("角色名已经存在！");
        }

    }


    /**
     * 对参数 id进行校验
     */
    public void validationId(Long id) throws Exception {
        if (id == null) {
            throw new Exception("角色不能为空");
        }
        Role one = roleDao.findOne(id);
        if (one == null) {
            throw new Exception("此角色不存在");
        }
    }


    /**
     * 获取登录用户,  并对请求头中的用户信息做校验
     */
    public User getLoginUser() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userInfo = request.getHeader("userInfo");
        userInfo = URLDecoder.decode(userInfo, "UTF-8");
        if (StringUtils.isEmpty(userInfo)) {
            throw new Exception("无法获得该用户信息");
        }
        Map<String, Object> map = (Map<String, Object>) JSON.parse(userInfo);
        Long id = Long.parseLong((String) map.get("id"));
        if (id == 0) {
            throw new Exception("角色名为空");
        }
        User user = userDao.findOne(id);
        if (null == user) {
            throw new Exception("此用户不存在");
        }
        return user;
    }


    /**
     * 删除角色前的校验   ---  不能删除已经被 用户绑定的角色
     */
    public void validBeforeDeleteRole(Long roleId) throws Exception {

        List<UserRole> list = userRoleDao.findByRoleId(roleId);
        if (null != list && list.size() > 0) {
            throw new Exception("本角色已经被用户绑定，不能删除！");
        }
    }

    /**
     * 校验创建者这个字段， 只能编辑删除自己创建的角色
     */
    public void validationCreateId(Long createId) throws Exception {
        User user = this.getLoginUser();
        Long id = user.getId();
        if (!id.equals(createId)) {
            throw new Exception("很抱歉，您没有权限，您只能操作自己创建的角色！");
        }
    }


}