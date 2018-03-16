package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;

import java.util.Map;

public interface ActivityAttachmentMapper {
    void insertActivityAttachment(ActivityAttachment activityAttachment) throws Exception;

    void deleteAttachmentByContentIdAndActivityType(Map<String, Object> map);
}
