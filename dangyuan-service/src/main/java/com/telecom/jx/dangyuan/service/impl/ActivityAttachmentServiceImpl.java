package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.ActivityAttachmentMapper;
import com.telecom.jx.dangyuan.mapper.DangZeMapper;
import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;
import com.telecom.jx.dangyuan.service.ActivityAttachmentService;
import com.telecom.jx.dangyuan.util.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

@Service
public class ActivityAttachmentServiceImpl implements ActivityAttachmentService {

    @Autowired
    private ActivityAttachmentMapper activityAttachmentMapper;

    @Autowired
    private DangZeMapper dangZeMapper;

    //加上注解@Transactional之后，这个方法就变成了事务方法
    //并不是事务方法越多越好，查询方法不需要添加为事务方法
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void uploadActivityImg(ActivityAttachment activityAttachment, MultipartFile multipartFile) throws Exception {
        System.out.println("multipartFile=" + multipartFile);
        System.out.println("multipartFile.getBytes()=" + multipartFile.getBytes().length);
        System.out.println("multipartFile.getContentType()=" + multipartFile.getContentType());
        System.out.println("multipartFile.getName()=" + multipartFile.getName());
        System.out.println("multipartFile.getOriginalFilename()=" + multipartFile.getOriginalFilename());
        System.out.println("multipartFile.getSize()=" + multipartFile.getSize());
        //读取FTP配置文件信息
        String name = "ftp.properties";
        String host = PropKit.use(name).get("ftp.address");
        int port = PropKit.use(name).getInt("ftp.port");
        String username = PropKit.use(name).get("ftp.username");
        String password = PropKit.use(name).get("ftp.password");
        String basePath = PropKit.use(name).get("ftp.basePath");//   /home/ftpuser/dangyuan/activityAttachment/images
        //创建文件路径：基础路径+文件路径+文件名+扩展名
        String filePath = new DateTime().toString("/yyyyMMdd");
        //获取原有的文件名，包含扩展名
        String originalFilename = multipartFile.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newName = IDUtils.genImageName() + fileType;
        System.out.println("newName=" + newName);
        InputStream inputStream = multipartFile.getInputStream();
        //执行上传操作
        FtpUtils.uploadFile(host, port, username, password, basePath, filePath, newName, inputStream);
        //插入activityAttachment表
        activityAttachment.setServerAddress(filePath + "/" + newName);//图片在服务器上的地址
        activityAttachment.setUploadTime(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        activityAttachmentMapper.insertActivityAttachment(activityAttachment);
    }

    @Override
    public void delAttachmentByContentIdAndActivityType(Map<String, Object> map) throws Exception {
        activityAttachmentMapper.deleteAttachmentByContentIdAndActivityType(map);
    }
}
