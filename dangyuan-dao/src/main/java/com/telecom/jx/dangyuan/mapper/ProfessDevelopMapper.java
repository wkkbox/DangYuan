package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.pojo.vo.ProfessDevelopCustom;
import java.util.List;
import java.util.Map;

public interface ProfessDevelopMapper {

    List<ProfessDevelopCustom> selectProfessDevelopCustoms(Map<String, Object> map) throws Exception;

    Integer selectProfessDevelopState(Map<String, Object> map) throws Exception;

    String selectProfessDevelopContentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    List<ActivityAttachment> selectProfessDevelopAttachmentByArrangeIdAndUserId(Map<String, Object> map) throws Exception;

    void insertUserProfessDevelop(Map<String, Object> map) throws Exception;

    void insertProfessDevelop3Arrange(Map<String, Object> map) throws Exception;

    List<ProfessDevelopCustom> selectProfessDevelop3Customs(Map<String, Object> map) throws Exception;

    Integer selectProfessDevelop3ArrangeCount(Map<String, Object> map) throws Exception;

    Long selectProfessDevelopContentIdByArrangeIdAndUserId(Map<String, Object> map);

    void updateProfessDevelopContent(Map<String, Object> map);

    void updateProfessDevelopState(Map<String, Object> map);

    Integer selectProfessDevelopScore(Map<String, Object> scoreMap);
}
