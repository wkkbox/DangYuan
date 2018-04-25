package com.telecom.jx.dangyuan.controller;

import com.telecom.jx.dangyuan.pojo.po.FeedBack;
import com.telecom.jx.dangyuan.service.FeedBackService;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/feedBack")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 添加反馈信息
     *
     * @param feedBack
     * @return
     */
    @RequestMapping(value = {"/addFeedBack"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addFeedBack(FeedBack feedBack) {
        MessageResult result = null;
        try {
            feedBackService.addFeedBack(feedBack);
            result = new MessageResult(true, "反馈成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "反馈失败", null);
        }
        return JsonUtils.objectToJson(result);
    }
}
