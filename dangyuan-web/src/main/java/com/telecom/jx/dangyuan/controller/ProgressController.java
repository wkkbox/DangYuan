package com.telecom.jx.dangyuan.controller;

import com.telecom.jx.dangyuan.pojo.po.Progress;
import com.telecom.jx.dangyuan.service.ProgressService;
import com.telecom.jx.dangyuan.util.DateUtil;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    /**
     * 得到每个人的活动的进度，申请中，已完成
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = {"/progresses"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getProgresses(Long userId) {
        MessageResult result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("year", DateUtil.getYear(new Date()));
            List<Progress> progresses = progressService.getProgresses(map);
            result = new MessageResult(true, "成功", progresses);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(true, "失败", null);
        }
        return JsonUtils.objectToJson(result);
    }
}
