package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.ActivityContent;
import com.telecom.jx.dangyuan.pojo.po.DangZe;

import java.util.List;

public interface ActivityService {
    List<DangZe> getDangZes(Long userId, Long roleId) throws Exception;

    Long saveActivityContent(ActivityContent activityContent) throws Exception;
}
