package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.Info;
import com.telecom.jx.dangyuan.util.dto.PageBean;


public interface InfoService {
    Integer getUnreadInfoCount(Long userId) throws Exception;

    PageBean<Info> getInfos(Long userId, Long roleId, Integer currentPage, Integer pageSize) throws Exception;

    Info getInfoByInfoId(Long infoId) throws Exception;
}
