package com.telecom.jx.dangyuan.pojo.vo;

import com.telecom.jx.dangyuan.pojo.po.ActivityAttachment;

import java.util.List;

public class ProfessDevelopCustom {

    private String title;//专业提升标题
    private String content;//专业提升内容
    private Long roleId;//所属用户角色
    private Integer dScore;//固定得分，1，2，3...
    private Integer lScore;//最低得分
    private Integer hScore;//最高得分
    private Integer rate;//活动频率，0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
    private Integer sumScore;//累计可得最高分
    private String month;//月份
    private String time;//时间
    private Long professdevelopId;//专业提升id
    private Integer state;//状态，申请是0，申请中是1，完成是2，默认状态为0
    private Integer rScore;//真实得分，默认为0
    private String commitContent;//用户提交内容
    private List<ActivityAttachment> attachmentList;//附件
    private Long arrangeId;//专业提升安排表主键
    private Integer otherAttr;//其他属性，1表示国家级，2表示省部级，3表示地市级，4表示集团（中央企业）级，5表示省公司级，6表示市公司级

    //private String commitTime;//党员提交时间
    //private String finishTime;//管理员审核通过时间
    //private Long adminId;//审核人id
    //private String publishTime;//管理员发布党责时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getdScore() {
        return dScore;
    }

    public void setdScore(Integer dScore) {
        this.dScore = dScore;
    }

    public Integer getlScore() {
        return lScore;
    }

    public void setlScore(Integer lScore) {
        this.lScore = lScore;
    }

    public Integer gethScore() {
        return hScore;
    }

    public void sethScore(Integer hScore) {
        this.hScore = hScore;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getSumScore() {
        return sumScore;
    }

    public void setSumScore(Integer sumScore) {
        this.sumScore = sumScore;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getrScore() {
        return rScore;
    }

    public void setrScore(Integer rScore) {
        this.rScore = rScore;
    }

    public Long getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Long arrangeId) {
        this.arrangeId = arrangeId;
    }

    public String getCommitContent() {
        return commitContent;
    }

    public void setCommitContent(String commitContent) {
        this.commitContent = commitContent;
    }

    public List<ActivityAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<ActivityAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public Integer getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(Integer otherAttr) {
        this.otherAttr = otherAttr;
    }

    public Long getProfessdevelopId() {
        return professdevelopId;
    }

    public void setProfessdevelopId(Long professdevelopId) {
        this.professdevelopId = professdevelopId;
    }
}
