package com.telecom.jx.dangyuan.pojo.po;

/**
 * 进度
 */
public class Progress {
    private String title;
    private Integer otherAttr;
    private String commitTime;
    private String finishTime;
    private Integer rScore;
    private Integer type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(Integer otherAttr) {
        this.otherAttr = otherAttr;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getrScore() {
        return rScore;
    }

    public void setrScore(Integer rScore) {
        this.rScore = rScore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
