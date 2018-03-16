package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.vo.HonorsAwardCustom;

import java.util.List;
import java.util.Map;

public interface HonorsAwardMapper {

    List<HonorsAwardCustom> selectHonorsAwardCustoms(Map<String, Object> map) throws Exception;

    Integer selectHonorsAwardState(Map<String, Object> map) throws Exception;

    String selectHonorsAwardContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectHonorsAwardAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserHonorsAward(Map<String, Object> map) throws Exception;

    void insertHonorsAward3Arrange(Map<String, Object> map) throws Exception;

    List<HonorsAwardCustom> selectHonorsAward3Customs(Map<String, Object> map) throws Exception;

    Integer selectHonorsAward3ArrangeCount(Map<String, Object> map) throws Exception;

    Long selectHonorsAwardContentIdByArrangeIdAndUserId(Map<String, Object> map);

    void updateHonorsAwardContent(Map<String, Object> map);

    void updateHonorsAwardState(Map<String, Object> map);

    Integer selectHonorsAwardScore(Map<String, Object> scoreMap);
}
