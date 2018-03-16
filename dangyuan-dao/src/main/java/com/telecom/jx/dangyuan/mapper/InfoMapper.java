package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.Info;

import java.util.List;
import java.util.Map;

public interface InfoMapper {
    Integer selectUnreadInfoCount(Map<String, Object> map);

    Integer selectInfoSize(Map<String, Object> map);

    List<Info> selectInfoByPage(Map<String, Object> map);

    List<Long> selectUnreadInfoId(Map<String, Object> map);

    Info selectInfoByInfoId(Long infoId);

    void updateInfoReaded(Map<String, Object> map);

    Integer selectInfoUserSize(Map<String, Object> map);

    Info selectInfoUserById(Long infoId);
}
