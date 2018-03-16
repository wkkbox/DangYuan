package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.Info;
import com.telecom.jx.dangyuan.util.dto.PageBean;

import java.util.Map;


public interface InfoService {
    Integer getUnreadInfoCount(Map<String, Object> map) throws Exception;

    PageBean<Info> getInfos(Long userId, Long roleId, Integer currentPage, Integer pageSize) throws Exception;

    Info getInfoByInfoId(Long infoId, Long userId) throws Exception;
}
