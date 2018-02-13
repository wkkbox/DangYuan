package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityAttachmentService {
    void uploadActivityImg(ActivityAttachment activityAttachment, MultipartFile multipartFile) throws Exception;
}
