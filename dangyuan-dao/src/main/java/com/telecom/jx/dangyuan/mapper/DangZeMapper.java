package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.DangZeCustom;

import java.util.List;
import java.util.Map;

public interface DangZeMapper {
    List<DangZe> selectDangZes(Long roleId) throws Exception;

    List<DangZeCustom> selectDangZeCustoms(Map<String, Object> map) throws Exception;

    Integer selectDangZeState(Map<String, Object> map) throws Exception;

    String selectDangZeContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectDangZeAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserDangZe(Map<String, Object> map) throws Exception;

    void insertDangZe3Arrange(Map<String, Object> map) throws Exception;

    List<DangZeCustom> selectDangZe3Customs(Map<String, Object> map) throws Exception;

    Integer selectDangze3ArrangeCount(Map<String, Object> map) throws Exception;
}
