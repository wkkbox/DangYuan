package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.DangZeContent;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.*;

import java.util.List;

public interface ActivityService {
    List<DangZe> getDangZes(Long userId, Long roleId) throws Exception;

    List<DangZeCustom> getDangZeCustoms(Long userId) throws Exception;

    //未使用
    Long saveActivityContent(DangZeContent activityContent) throws Exception;

    Long saveDangZeContent(DangZeContent dangZeContent, Long dangzeId, String time, Integer rate, Integer count) throws Exception;

    List<SheZeCustom> getSheZeCustoms(Long userId) throws Exception;

    List<AchievementCustom> getAchievementCustoms(Long userId) throws Exception;

    List<HonorsAwardCustom> getHonorsAwardCustoms(Long userId) throws Exception;

    List<ProfessDevelopCustom> getProfessDevelopCustoms(Long userId) throws Exception;
}
