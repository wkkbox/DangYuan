package com.telecom.jx.dangyuan.controller;

import com.telecom.jx.dangyuan.pojo.po.*;
import com.telecom.jx.dangyuan.pojo.vo.*;
import com.telecom.jx.dangyuan.service.ActivityService;
import com.telecom.jx.dangyuan.service.ActivityAttachmentService;
import com.telecom.jx.dangyuan.service.UserService;
import com.telecom.jx.dangyuan.util.DateUtil;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityAttachmentService activityAttachmentService;

    /**
     * 查询本用户当月所有活动，0表示党责，1表示社责，2表示工作业绩，3表示荣誉奖励，4表示专业提升
     *
     * @param userId
     * @param activityType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/activities"}, produces = "application/json;charset=utf-8")
    public String activities(Long userId, Integer activityType) {
        System.out.println("activities");
        MessageResult result = null;
        //id值越小角色权限越高
        Long roleId = null;
        try {
            roleId = userService.getUserHighRoleByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //判断活动类型
            switch (activityType) {
                //0是党责活动
                case 0:
                    List<DangZeCustom> dangZeCustoms = null;
                    dangZeCustoms = activityService.getDangZeCustoms(userId);
                    System.out.println("dangZeCustoms:" + dangZeCustoms);
                    result = new MessageResult(true, "查询成功", dangZeCustoms);
                    //System.out.println("currentUser=" + SecurityUtils.getSubject().getSession().getAttribute("currentUser"));
                    break;
                //1是社责
                case 1:
                    List<SheZeCustom> sheZeCustoms = null;
                    sheZeCustoms = activityService.getSheZeCustoms(userId);
                    System.out.println("sheZeCustoms:" + sheZeCustoms);
                    result = new MessageResult(true, "查询成功", sheZeCustoms);
                    break;
                //2是工作业绩
                case 2:
                    List<AchievementCustom> achievementCustoms = null;
                    achievementCustoms = activityService.getAchievementCustoms(userId);
                    System.out.println("achievementCustoms:" + achievementCustoms);
                    result = new MessageResult(true, "查询成功", achievementCustoms);
                    break;
                //3是荣誉奖励
                case 3:
                    List<HonorsAwardCustom> honorsAwardCustoms = null;
                    honorsAwardCustoms = activityService.getHonorsAwardCustoms(userId);
                    System.out.println("honorsAwardCustoms:" + honorsAwardCustoms);
                    result = new MessageResult(true, "查询成功", honorsAwardCustoms);
                    break;
                //4是专业提升
                case 4:
                    List<ProfessDevelopCustom> professDevelopCustoms = null;
                    professDevelopCustoms = activityService.getProfessDevelopCustoms(userId);
                    System.out.println("professDevelopCustoms:" + professDevelopCustoms);
                    result = new MessageResult(true, "查询成功", professDevelopCustoms);
                    break;
                //其余活动之后再写
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "查询失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的党责活动的活动记录
     *
     * @param dangZeContent
     * @param dangzeId
     * @param time
     * @param rate
     * @param dScore
     * @param sumScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/dangzeContent"}, produces = "application/json;charset=utf-8")
    public String saveDangZeContent(DangZeContent dangZeContent, Long dangzeId, String time, Integer rate, Integer dScore, Integer sumScore) {
        System.out.println("dangzeContent = " + dangZeContent);
        System.out.println("dangzeId=" + dangzeId);
        System.out.println("time=" + time);
        System.out.println("rate=" + rate);
        System.out.println("dScore" + dScore);
        System.out.println("sumScore" + sumScore);
        MessageResult result = null;
        try {
            dangZeContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
            Long dangZeContentId = activityService.saveDangZeContent(dangZeContent, dangzeId, time, rate, sumScore / dScore);
            result = new MessageResult(true, "提交党责活动记录成功", dangZeContentId);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交党责活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的社责活动的活动记录
     *
     * @param sheZeContent
     * @param shezeId
     * @param time
     * @param rate
     * @param dScore
     * @param sumScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/shezeContent"}, produces = "application/json;charset=utf-8")
    public String saveSheZeContent(SheZeContent sheZeContent, Long shezeId, String time, Integer rate, Integer dScore, Integer sumScore) {
        System.out.println("sheZeContent = " + sheZeContent);
        System.out.println("shezeId=" + shezeId);
        System.out.println("time=" + time);
        System.out.println("rate=" + rate);
        System.out.println("dScore" + dScore);
        System.out.println("sumScore" + sumScore);
        MessageResult result = null;
        try {
            sheZeContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
            Long sheZeContentId = activityService.saveSheZeContent(sheZeContent, shezeId, time, rate, sumScore / dScore);
            result = new MessageResult(true, "提交社责活动记录成功", sheZeContentId);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交社责活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的工作业绩活动的活动记录
     *
     * @param achievementContent
     * @param achievementId
     * @param time
     * @param rate
     * @param dScore
     * @param sumScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/achievementContent"}, produces = "application/json;charset=utf-8")
    public String saveAchievementContent(AchievementContent achievementContent, Long achievementId, String time, Integer rate, Integer dScore, Integer sumScore) {
        System.out.println("achievementContent = " + achievementContent);
        System.out.println("achievementId=" + achievementId);
        System.out.println("time=" + time);
        System.out.println("rate=" + rate);
        System.out.println("dScore" + dScore);
        System.out.println("sumScore" + sumScore);
        MessageResult result = null;
        try {
            achievementContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
            Long achievementContentId = activityService.saveAchievementContent(achievementContent, achievementId, time, rate, sumScore / dScore);
            result = new MessageResult(true, "提交工作业绩活动记录成功", achievementContentId);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交工作业绩活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的荣誉奖励活动的活动记录
     *
     * @param honorsAwardContent
     * @param honorsAwardId
     * @param time
     * @param rate
     * @param dScore
     * @param sumScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/honorsAwardContent"}, produces = "application/json;charset=utf-8")
    public String saveHonorsAwardContent(HonorsAwardContent honorsAwardContent, Long honorsAwardId, String time, Integer rate, Integer dScore, Integer sumScore) {
        System.out.println("honorsAwardContent = " + honorsAwardContent);
        System.out.println("honorsAwardId=" + honorsAwardId);
        System.out.println("time=" + time);
        System.out.println("rate=" + rate);
        System.out.println("dScore" + dScore);
        System.out.println("sumScore" + sumScore);
        MessageResult result = null;
        try {
            honorsAwardContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
            Long honorsAwardContentId = activityService.saveHonorsAwardContent(honorsAwardContent, honorsAwardId, time, rate, sumScore / dScore);
            result = new MessageResult(true, "提交荣誉奖励活动记录成功", honorsAwardContentId);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交荣誉奖励活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的专业提升活动的活动记录
     *
     * @param professDevelopContent
     * @param professdevelopId
     * @param time
     * @param rate
     * @param dScore
     * @param sumScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/professDevelopContent"}, produces = "application/json;charset=utf-8")
    public String saveProfessDevelopContent(ProfessDevelopContent professDevelopContent, Long professdevelopId, String time, Integer rate, Integer dScore, Integer sumScore) {
        System.out.println("professDevelopContent = " + professDevelopContent);
        System.out.println("professdevelopId=" + professdevelopId);
        System.out.println("time=" + time);
        System.out.println("rate=" + rate);
        System.out.println("dScore" + dScore);
        System.out.println("sumScore" + sumScore);
        MessageResult result = null;
        try {
            professDevelopContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
            Long professDevelopContentId = activityService.saveProfessDevelopContent(professDevelopContent, professdevelopId, time, rate, sumScore / dScore);
            result = new MessageResult(true, "提交专业提升活动记录成功", professDevelopContentId);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交专业提升活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的活动的活动附件(目前为图片)
     *
     * @param activityAttachment
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/activityAttachment"}, produces = "application/json;charset=utf-8")
    public String saveActivityAttachment(ActivityAttachment activityAttachment, MultipartFile multipartFile) {
        System.out.println("activityAttachment = " + activityAttachment);
        MessageResult result = null;
        try {
            switch (activityAttachment.getAttachmentType()) {
                //0是图片类型
                case 0:
                    activityAttachmentService.uploadActivityImg(activityAttachment, multipartFile);
                    result = new MessageResult(true, "提交活动附件成功", null);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交活动附件失败", null);
        }
        return JsonUtils.objectToJson(result);
    }


}
