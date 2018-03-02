package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.DangZeContent;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.DangZeCustom;

import java.util.List;

public interface ActivityService {
    List<DangZe> getDangZes(Long userId, Long roleId) throws Exception;

    List<DangZeCustom> getDangZeCustoms(Long userId) throws Exception;

    //未使用
    Long saveActivityContent(DangZeContent activityContent) throws Exception;

    Long saveDangZeContent(DangZeContent dangZeContent, Long dangzeId, String time, Integer rate, Integer count) throws Exception;
}
