package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.vo.SheZeCustom;
import java.util.List;
import java.util.Map;

public interface SheZeMapper {

    List<SheZeCustom> selectSheZeCustoms(Map<String, Object> map);

    Integer selectSheZeState(Map<String, Object> map);

    String selectSheZeContentByArrangeIdAndUserId(Map<String, Object> map);

    List<ActivityAttachment> selectSheZeAttachmentByArrangeIdAndUserId(Map<String, Object> map);

    void insertUserSheZe(Map<String, Object> map);

    void insertSheZe3Arrange(Map<String, Object> map);

    List<SheZeCustom> selectSheZe3Customs(Map<String, Object> map);

    Integer selectSheZe3ArrangeCount(Map<String, Object> map);

    Long selectSheZeContentIdByArrangeIdAndUserId(Map<String, Object> map);

    void updateSheZeContent(Map<String, Object> map);

    void updateSheZeState(Map<String, Object> map);

    Integer selectSheZeScore(Map<String, Object> scoreMap);
}
