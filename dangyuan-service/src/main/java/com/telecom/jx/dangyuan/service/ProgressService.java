package com.telecom.jx.dangyuan.service;

import com.telecom.jx.dangyuan.pojo.po.Progress;

import java.util.List;
import java.util.Map;

public interface ProgressService {
    List<Progress> getProgresses(Map<String, Object> map) throws Exception;
}
