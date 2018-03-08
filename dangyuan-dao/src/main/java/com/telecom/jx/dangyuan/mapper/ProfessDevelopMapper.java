package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.ProfessDevelopCustom;

import java.util.List;
import java.util.Map;

public interface ProfessDevelopMapper {
    List<DangZe> selectDangZes(Long roleId) throws Exception;

    List<ProfessDevelopCustom> selectProfessDevelopCustoms(Map<String, Object> map) throws Exception;

    Integer selectProfessDevelopState(Map<String, Object> map) throws Exception;

    String selectProfessDevelopContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectProfessDevelopAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserDangZe(Map<String, Object> map) throws Exception;

    void insertDangZe3Arrange(Map<String, Object> map) throws Exception;

    List<ProfessDevelopCustom> selectProfessDevelop3Customs(Map<String, Object> map) throws Exception;

    Integer selectDangze3ArrangeCount(Map<String, Object> map) throws Exception;
}
