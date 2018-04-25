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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * 上传(更新)完成的党责活动的活动记录
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

            if (dangZeContent.getId() != null) {
                //更新操作
                Long contentId = dangZeContent.getId();
                System.out.println("contentId=" + contentId);
                Map<String, Object> map = new HashMap<>();
                map.put("contentId", contentId);
                map.put("dangzeContent", dangZeContent.getContent());
                activityService.editDangZeContent(map);

                //修改进度表state为1
                Long userId = dangZeContent.getUserId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("dangzeId", dangzeId);
                updateMap.put("time", time);
                updateMap.put("userId", userId);
                activityService.updateDangZeState(updateMap);

                //删除原有附件
                Map<String, Object> delMap = new HashMap<>();
                map.put("contentId", contentId);
                map.put("activityType", 0);
                activityAttachmentService.delAttachmentByContentIdAndActivityType(delMap);
                result = new MessageResult(true, "更新党责活动记录成功", contentId);
            } else {
                //插入操作
                dangZeContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
                Long dangZeContentId = activityService.saveDangZeContent(dangZeContent, dangzeId, time, rate, sumScore / dScore);
                result = new MessageResult(true, "提交党责活动记录成功", dangZeContentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交党责活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传(更新)完成的社责活动的活动记录
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
            if (sheZeContent.getId() != null) {
                //更新操作
                Long contentId = sheZeContent.getId();
                System.out.println("contentId=" + contentId);
                Map<String, Object> map = new HashMap<>();
                map.put("contentId", contentId);
                map.put("sheZeContent", sheZeContent.getContent());
                activityService.editSheZeContent(map);

                //修改进度表state为1
                Long userId = sheZeContent.getUserId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("shezeId", shezeId);
                updateMap.put("time", time);
                updateMap.put("userId", userId);
                activityService.updateSheZeState(updateMap);

                //删除原有附件
                Map<String, Object> delMap = new HashMap<>();
                map.put("contentId", contentId);
                map.put("activityType", 1);
                activityAttachmentService.delAttachmentByContentIdAndActivityType(delMap);
                result = new MessageResult(true, "更新社责活动记录成功", contentId);
            } else {
                //插入操作
                sheZeContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
                Long sheZeContentId = activityService.saveSheZeContent(sheZeContent, shezeId, time, rate, sumScore / dScore);
                result = new MessageResult(true, "提交社责活动记录成功", sheZeContentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交社责活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传(更新)完成的工作业绩活动的活动记录
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

            if (achievementContent.getId() != null) {
                //更新操作
                Long contentId = achievementContent.getId();
                System.out.println("contentId=" + contentId);
                Map<String, Object> map = new HashMap<>();
                map.put("contentId", contentId);
                map.put("achievementContent", achievementContent.getContent());
                activityService.editAchievementContent(map);

                //修改进度表state为1
                Long userId = achievementContent.getUserId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("achievementId", achievementId);
                updateMap.put("time", time);
                updateMap.put("userId", userId);
                activityService.updateAchievementState(updateMap);

                //删除原有附件
                Map<String, Object> delMap = new HashMap<>();
                map.put("contentId", contentId);
                map.put("activityType", 2);
                activityAttachmentService.delAttachmentByContentIdAndActivityType(delMap);
                result = new MessageResult(true, "更新工作业绩活动记录成功", contentId);
            } else {
                //插入操作
                achievementContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
                Long achievementContentId = activityService.saveAchievementContent(achievementContent, achievementId, time, rate, sumScore / dScore);
                result = new MessageResult(true, "提交工作业绩活动记录成功", achievementContentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交工作业绩活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传(更新)完成的荣誉奖励活动的活动记录
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

            if (honorsAwardContent.getId() != null) {
                //更新操作
                Long contentId = honorsAwardContent.getId();
                System.out.println("contentId=" + contentId);
                Map<String, Object> map = new HashMap<>();
                map.put("contentId", contentId);
                map.put("honorsAwardContent", honorsAwardContent.getContent());
                activityService.editHonorsAwardContent(map);

                //修改进度表state为1
                Long userId = honorsAwardContent.getUserId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("honorsAwardId", honorsAwardId);
                updateMap.put("time", time);
                updateMap.put("userId", userId);
                activityService.updateHonorsAwardState(updateMap);

                //删除原有附件
                Map<String, Object> delMap = new HashMap<>();
                map.put("contentId", contentId);
                map.put("activityType", 3);
                activityAttachmentService.delAttachmentByContentIdAndActivityType(delMap);
                result = new MessageResult(true, "更新荣誉奖励活动记录成功", contentId);
            } else {
                //插入操作
                honorsAwardContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
                Long honorsAwardContentId = activityService.saveHonorsAwardContent(honorsAwardContent, honorsAwardId, time, rate, sumScore / dScore);
                result = new MessageResult(true, "提交荣誉奖励活动记录成功", honorsAwardContentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交荣誉奖励活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传(更新)完成的专业提升活动的活动记录
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

            if (professDevelopContent.getId() != null) {
                //更新操作
                Long contentId = professDevelopContent.getId();
                System.out.println("contentId=" + contentId);
                Map<String, Object> map = new HashMap<>();
                map.put("contentId", contentId);
                map.put("professDevelopContent", professDevelopContent.getContent());
                activityService.editProfessDevelopContent(map);

                //修改进度表state为1
                Long userId = professDevelopContent.getUserId();
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("professdevelopId", professdevelopId);
                updateMap.put("time", time);
                updateMap.put("userId", userId);
                activityService.updateProfessDevelopState(updateMap);

                //删除原有附件
                Map<String, Object> delMap = new HashMap<>();
                map.put("contentId", contentId);
                map.put("activityType", 4);
                activityAttachmentService.delAttachmentByContentIdAndActivityType(delMap);
                result = new MessageResult(true, "更新专业提升活动记录成功", contentId);
            } else {
                //插入操作
                professDevelopContent.setYear(String.valueOf(DateUtil.getYear(new Date())));
                Long professDevelopContentId = activityService.saveProfessDevelopContent(professDevelopContent, professdevelopId, time, rate, sumScore / dScore);
                result = new MessageResult(true, "提交专业提升活动记录成功", professDevelopContentId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "提交专业提升活动记录失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 上传完成的活动的活动附件(目前为图片)（localAddress，attachmentType，contentId，activityType）
     *
     * @param activityAttachment
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/activityAttachment"}, produces = "application/json;charset=utf-8")
    public String saveActivityAttachment(ActivityAttachment activityAttachment, MultipartFile multipartFile) {
        System.out.println("activityAttachment = " + activityAttachment);
        System.out.println("multipartFile=" + multipartFile);
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
