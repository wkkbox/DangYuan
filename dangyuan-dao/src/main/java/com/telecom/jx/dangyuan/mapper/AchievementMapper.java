package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.AchievementCustom;

import java.util.List;
import java.util.Map;

public interface AchievementMapper {
    List<DangZe> selectDangZes(Long roleId) throws Exception;

    List<AchievementCustom> selectAchievementCustoms(Map<String, Object> map) throws Exception;

    Integer selectAchievementState(Map<String, Object> map) throws Exception;

    String selectAchievementContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectAchievementAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserDangZe(Map<String, Object> map) throws Exception;

    void insertDangZe3Arrange(Map<String, Object> map) throws Exception;

    List<AchievementCustom> selectAchievement3Customs(Map<String, Object> map) throws Exception;

    Integer selectDangze3ArrangeCount(Map<String, Object> map) throws Exception;
}
