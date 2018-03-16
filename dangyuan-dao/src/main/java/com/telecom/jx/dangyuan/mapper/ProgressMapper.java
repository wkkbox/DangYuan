package com.telecom.jx.dangyuan.mapper;

import com.telecom.jx.dangyuan.pojo.po.Progress;

import java.util.List;
import java.util.Map;

public interface ProgressMapper {
    List<Progress> selectDangZeProgress(Map<String, Object> map);

    List<Progress> selectSheZeProgress(Map<String, Object> map);

    List<Progress> selectAchieveProgress(Map<String, Object> map);

    List<Progress> selectHonorsAwardProgress(Map<String, Object> map);

    List<Progress> selectProfessDevelopProgress(Map<String, Object> map);
}
