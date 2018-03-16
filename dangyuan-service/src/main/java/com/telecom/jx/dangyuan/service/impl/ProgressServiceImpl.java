package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.ProgressMapper;
import com.telecom.jx.dangyuan.pojo.po.Progress;
import com.telecom.jx.dangyuan.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressMapper progressMapper;

    @Override
    public List<Progress> getProgresses(Map<String, Object> map) throws Exception {
        List<Progress> progressList = new ArrayList<>();
        //党责
        List<Progress> progresses0 = progressMapper.selectDangZeProgress(map);
        for (Progress item : progresses0) {
            item.setType(0);
        }
        //社责
        List<Progress> progresses1 = progressMapper.selectSheZeProgress(map);
        for (Progress item : progresses1) {
            if(item.getOtherAttr()==1){
                item.setTitle("(个人名义参加)"+item.getTitle());
            }
            if(item.getOtherAttr()==2){
                item.setTitle("(集体名义参加)"+item.getTitle());
            }
            item.setType(1);
        }
        //工作业绩
        List<Progress> progresses2 = progressMapper.selectAchieveProgress(map);
        for (Progress item : progresses2) {
            if(item.getOtherAttr()==1){
                item.setTitle("(考核优秀)"+item.getTitle());
            }
            if(item.getOtherAttr()==2){
                item.setTitle("(考核称职)"+item.getTitle());
            }
            item.setType(2);
        }
        //荣誉奖励
        List<Progress> progresses3 = progressMapper.selectHonorsAwardProgress(map);
        for (Progress item : progresses3) {
            if(item.getOtherAttr()==1){
                item.setTitle("(国家级)"+item.getTitle());
            }
            if(item.getOtherAttr()==2){
                item.setTitle("(省部级)"+item.getTitle());
            }
            if(item.getOtherAttr()==3){
                item.setTitle("(地市级)"+item.getTitle());
            }
            if(item.getOtherAttr()==4){
                item.setTitle("(县(区)级)"+item.getTitle());
            }
            if(item.getOtherAttr()==5){
                item.setTitle("(集团(中央企业)级)"+item.getTitle());
            }
            if(item.getOtherAttr()==6){
                item.setTitle("(行业级)"+item.getTitle());
            }
            if(item.getOtherAttr()==7){
                item.setTitle("(省公司级)"+item.getTitle());
            }
            if(item.getOtherAttr()==8){
                item.setTitle("(市公司级)"+item.getTitle());
            }
            if(item.getOtherAttr()==9){
                item.setTitle("(客户级)"+item.getTitle());
            }
            item.setType(3);
        }
        //专业提升
        List<Progress> progresses4 = progressMapper.selectProfessDevelopProgress(map);
        for (Progress item : progresses4) {
            if(item.getOtherAttr()==1){
                item.setTitle("(国家级)"+item.getTitle());
            }
            if(item.getOtherAttr()==2){
                item.setTitle("(省部级)"+item.getTitle());
            }
            if(item.getOtherAttr()==3){
                item.setTitle("(地市级)"+item.getTitle());
            }
            if(item.getOtherAttr()==4){
                item.setTitle("(集团(中央企业)级)"+item.getTitle());
            }
            if(item.getOtherAttr()==5){
                item.setTitle("(省公司级)"+item.getTitle());
            }
            if(item.getOtherAttr()==6){
                item.setTitle("(市公司级)"+item.getTitle());
            }
            item.setType(4);
        }
        progressList.addAll(progresses0);
        progressList.addAll(progresses1);
        progressList.addAll(progresses2);
        progressList.addAll(progresses3);
        progressList.addAll(progresses4);

        return progressList;
    }
}
