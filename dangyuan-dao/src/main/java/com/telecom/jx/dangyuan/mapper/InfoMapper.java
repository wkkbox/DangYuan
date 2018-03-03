package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.Info;

import java.util.List;
import java.util.Map;

public interface InfoMapper {
    Integer selectUnreadInfoCount(Map<String, Object> map) throws Exception;

    Integer selectInfoSize(Map<String, Object> map) throws Exception;

    List<Info> selectInfoByPage(Map<String, Object> map) throws Exception;

    List<Long> selectUnreadInfoId(Map<String, Object> map) throws Exception;

    Info selectInfoByInfoId(Long infoId) throws Exception;

    void updateInfoReaded(Long infoId) throws Exception;
}
