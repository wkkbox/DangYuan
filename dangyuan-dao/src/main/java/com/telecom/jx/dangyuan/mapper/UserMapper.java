package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.util.dto.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserMapper {
    List<User> listUsers() throws Exception;

    List<User> selectUserByUserName(String userName) throws Exception;

    User selectUserByAccountName(String accountName) throws Exception;

    List<String> selectUserRolesByUserId(Long userId) throws Exception;

    List<Long> selectUserRoleIdsByUserId(Long userId) throws Exception;

    List<String> selectUserPermissionsByUserId(Long userId) throws Exception;

    List<Menu> selectMenusByUserId(Long userId) throws Exception;

    List<Score> selectScorePublicity() throws Exception;

    Integer selectScorePublicitySize() throws Exception;

    List<Score> selectScorePublicityByPage(Map<String, Object> map) throws Exception;
}
