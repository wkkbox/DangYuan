package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ActivityAttachmentService {

    void uploadActivityImg(ActivityAttachment activityAttachment, MultipartFile multipartFile) throws Exception;

    void uploadActivityImg2(ActivityAttachment activityAttachment, MultipartFile multipartFile) throws Exception;

    void delAttachmentByContentIdAndActivityType(Map<String, Object> map) throws Exception;
}
