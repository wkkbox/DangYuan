package com.telecom.jx.dangyuan.service;


import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.util.dto.Menu;
import com.telecom.jx.dangyuan.util.dto.PageBean;

import java.util.List;

public interface UserService {
    List<User> showUsers() throws Exception;

    List<User> getUserByUserName(String userName) throws Exception;

    List<String> getUserRolesByUserId(Long userId) throws Exception;

    List<String> getUserPermissionsByUserId(Long userId) throws Exception;

    List<Menu> getMenusByUserId(Long userId) throws Exception;

    User getUserByAccountName(String accountName) throws Exception;

    List<Score> getScorePublicity() throws Exception;

    PageBean<Score> getScorePublicityByPage(Integer currentPage, Integer pageSize) throws Exception;

    Long getUserHighRoleByUserId(Long userId) throws Exception;
}
