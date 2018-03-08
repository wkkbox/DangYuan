package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.SheZeCustom;

import java.util.List;
import java.util.Map;

public interface SheZeMapper {
    List<DangZe> selectDangZes(Long roleId) throws Exception;

    List<SheZeCustom> selectSheZeCustoms(Map<String, Object> map) throws Exception;

    Integer selectSheZeState(Map<String, Object> map) throws Exception;

    String selectSheZeContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectSheZeAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserDangZe(Map<String, Object> map) throws Exception;

    void insertDangZe3Arrange(Map<String, Object> map) throws Exception;

    List<SheZeCustom> selectSheZe3Customs(Map<String, Object> map) throws Exception;

    Integer selectDangze3ArrangeCount(Map<String, Object> map) throws Exception;
}
