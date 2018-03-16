package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.DangZeCustom;

import java.util.List;
import java.util.Map;

public interface DangZeMapper {
    List<DangZe> selectDangZes(Long roleId);

    List<DangZeCustom> selectDangZeCustoms(Map<String, Object> map);

    Integer selectDangZeState(Map<String, Object> map);

    String selectDangZeContentByArrangeIdAndUserId(Map<String, Object> map);

    List<ActivityAttachment> selectDangZeAttachmentByArrangeIdAndUserId(Map<String, Object> map);

    void insertUserDangZe(Map<String, Object> map);

    void insertDangZe3Arrange(Map<String, Object> map);

    List<DangZeCustom> selectDangZe3Customs(Map<String, Object> map);

    Integer selectDangze3ArrangeCount(Map<String, Object> map);

    Long selectDangZeContentIdByArrangeIdAndUserId(Map<String, Object> map);

    void updateDangZeContent(Map<String, Object> map);

    void updateDangZeState(Map<String, Object> map);

    Integer selectDangZeScore(Map<String, Object> map);
}
