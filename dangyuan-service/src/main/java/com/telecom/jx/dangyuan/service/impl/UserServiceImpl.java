package com.telecom.jx.dangyuan.service.impl;


import com.telecom.jx.dangyuan.mapper.UserMapper;
import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.service.UserService;
import com.telecom.jx.dangyuan.util.CryptographyUtil;
import com.telecom.jx.dangyuan.util.dto.Menu;
import com.telecom.jx.dangyuan.util.dto.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> showUsers() throws Exception {
        return userMapper.listUsers();
    }

    @Override
    public List<User> getUserByUserName(String userName) throws Exception {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public List<String> getUserRolesByUserId(Long userId) throws Exception {
        return userMapper.selectUserRolesByUserId(userId);
    }

    @Override
    public List<String> getUserPermissionsByUserId(Long userId) throws Exception {
        return userMapper.selectUserPermissionsByUserId(userId);
    }

    @Override
    public List<Menu> getMenusByUserId(Long userId) throws Exception {
        return userMapper.selectMenusByUserId(userId);
    }

    @Override
    public User getUserByAccountName(String accountName) throws Exception {
        return userMapper.selectUserByAccountName(accountName);
    }

    @Override
    public List<Score> getScorePublicity() throws Exception {
        return userMapper.selectScorePublicity();
    }

    @Override
    public PageBean<Score> getScorePublicityByPage(Integer currentPage, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Integer count = userMapper.selectScorePublicitySize();
        PageBean<Score> pBean = new PageBean<>();
        pBean.setCurrentPage(currentPage);
        pBean.setPageSize(pageSize);
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        pBean.setpList(userMapper.selectScorePublicityByPage(map));
        pBean.setTotalCount(count);
        pBean.setTotalPage((count + pageSize - 1) / pageSize);
        return pBean;
    }

    @Override
    public Long getUserHighRoleByUserId(Long userId) throws Exception {
        //得到角色id集合
        List<Long> roleIds = userMapper.selectUserRoleIdsByUserId(userId);
        if (roleIds.size() == 1) {
            return roleIds.get(0);
        } else {
            return Collections.min(roleIds);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void editPassword(Long userId, String newPwd) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("password",CryptographyUtil.md5(newPwd, "dangyuan", 2));
        userMapper.updatePassword(map);
    }
}
