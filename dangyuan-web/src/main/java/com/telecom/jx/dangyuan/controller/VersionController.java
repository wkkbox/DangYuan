package com.telecom.jx.dangyuan.controller;

import com.telecom.jx.dangyuan.pojo.po.Version;
import com.telecom.jx.dangyuan.service.VersionService;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @ResponseBody
    @RequestMapping(value = {"/getVersion"}, produces = "application/json;charset=utf-8")
    public String getVersion() {
        MessageResult result = null;
        try {
            Version version = versionService.getVersion();
            result = new MessageResult(true, "查询成功", version);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "查询失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

}
