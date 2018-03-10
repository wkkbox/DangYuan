package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.util.dto.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserMapper {
    List<User> listUsers();

    List<User> selectUserByUserName(String userName);

    User selectUserByAccountName(String accountName);

    List<String> selectUserRolesByUserId(Long userId);

    List<Long> selectUserRoleIdsByUserId(Long userId);

    List<String> selectUserPermissionsByUserId(Long userId);

    List<Menu> selectMenusByUserId(Long userId);

    List<Score> selectScorePublicity();

    Integer selectScorePublicitySize();

    List<Score> selectScorePublicityByPage(Map<String, Object> map);

    void updatePassword(Map<String, Object> map);
}
