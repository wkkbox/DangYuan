package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.vo.AchievementCustom;

import java.util.List;
import java.util.Map;

public interface AchievementMapper {

    List<AchievementCustom> selectAchievementCustoms(Map<String, Object> map) throws Exception;

    Integer selectAchievementState(Map<String, Object> map) throws Exception;

    String selectAchievementContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectAchievementAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserAchievement(Map<String, Object> map) throws Exception;

    void insertAchievement3Arrange(Map<String, Object> map) throws Exception;

    List<AchievementCustom> selectAchievement3Customs(Map<String, Object> map) throws Exception;

    Integer selectAchievement3ArrangeCount(Map<String, Object> map) throws Exception;

    Long selectAchievementContentIdByArrangeIdAndUserId(Map<String, Object> map);

    void updateAchievementContent(Map<String, Object> map);

    void updateAchievementState(Map<String, Object> map);

    Integer selectAchieveScore(Map<String, Object> scoreMap);
}
