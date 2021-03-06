package com.telecom.jx.dangyuan.controller;

import com.telecom.jx.dangyuan.pojo.po.Info;
import com.telecom.jx.dangyuan.service.InfoService;
import com.telecom.jx.dangyuan.service.UserService;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import com.telecom.jx.dangyuan.util.dto.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private InfoService infoService;

    /**
     * 查询本用户所有消息
     *
     * @param userId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/infos"}, produces = "application/json;charset=utf-8")
    public String infos(Long userId, Integer currentPage, Integer pageSize) {
        MessageResult result = null;
        PageBean<Info> infoPage = null;
        try {
            //id值越小角色权限越高
            System.out.println("currentpage="+currentPage);
            System.out.println("pageSize="+pageSize);
            Long roleId = userService.getUserHighRoleByUserId(userId);
            infoPage = infoService.getInfos(userId, roleId, currentPage, pageSize);
            result = new MessageResult(true, "查询成功", infoPage);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "查询失败", infoPage);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 消息详情查看
     * @param infoId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/infoDetail"}, produces = "application/json;charset=utf-8")
    public String infoDetail(Long infoId,Long userId) {
        MessageResult result = null;
        Info info = null;
        try {
            info = infoService.getInfoByInfoId(infoId,userId);
            result = new MessageResult(true, "查询成功", info);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "查询失败", info);
        }
        return JsonUtils.objectToJson(result);
    }
}
