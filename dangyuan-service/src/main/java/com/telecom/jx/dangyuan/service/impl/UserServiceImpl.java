package com.telecom.jx.dangyuan.service.impl;


import com.telecom.jx.dangyuan.mapper.*;
import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.service.UserService;
import com.telecom.jx.dangyuan.util.CryptographyUtil;
import com.telecom.jx.dangyuan.util.DateUtil;
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

    @Autowired
    private DangZeMapper dangZeMapper;

    @Autowired
    private SheZeMapper sheZeMapper;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private HonorsAwardMapper honorsAwardMapper;

    @Autowired
    private ProfessDevelopMapper professDevelopMapper;

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
        List<Score> scoreList = userMapper.selectScorePublicityByPage(map);
        System.out.println("scoreList.size="+scoreList.size());
        //把参加的活动的分数设置上来
        for (int i = 0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            Long userId = score.getId();
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("year", DateUtil.getYear(new Date()));
            scoreMap.put("userId", userId);
            //党责分
            Integer dangZeScore = dangZeMapper.selectDangZeScore(scoreMap);
            if(dangZeScore==null){
                dangZeScore = 0;
            }
            //社责分
            Integer sheZeScore = sheZeMapper.selectSheZeScore(scoreMap);
            if(sheZeScore==null){
                sheZeScore = 0;
            }
            //工作业绩分
            Integer achieveScore = achievementMapper.selectAchieveScore(scoreMap);
            if(achieveScore==null){
                achieveScore = 0;
            }
            //荣誉奖励分
            Integer honorsAwardScore = honorsAwardMapper.selectHonorsAwardScore(scoreMap);
            if(honorsAwardScore==null){
                honorsAwardScore = 0;
            }
            //专业提升分
            Integer professDevelopScore = professDevelopMapper.selectProfessDevelopScore(scoreMap);
            if(professDevelopScore==null){
                professDevelopScore = 0;
            }
            Integer sumScore = dangZeScore+sheZeScore+achieveScore+honorsAwardScore+professDevelopScore;
            score.setScore(sumScore);
        }

        pBean.setpList(scoreList);
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
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", CryptographyUtil.md5(newPwd, "dangyuan", 2));
        userMapper.updatePassword(map);
    }
}
