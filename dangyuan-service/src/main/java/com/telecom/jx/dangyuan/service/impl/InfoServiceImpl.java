package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.InfoMapper;
import com.telecom.jx.dangyuan.pojo.po.Info;
import com.telecom.jx.dangyuan.service.InfoService;
import com.telecom.jx.dangyuan.util.DateUtil;
import com.telecom.jx.dangyuan.util.dto.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Override
    public Integer getUnreadInfoCount(Map<String, Object> map) throws Exception {
        return infoMapper.selectUnreadInfoCount(map);
    }

    @Override
    public PageBean<Info> getInfos(Long userId, Long roleId, Integer currentPage, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap<>();
        PageBean<Info> pBean = new PageBean<>();
        pBean.setCurrentPage(currentPage);
        pBean.setPageSize(pageSize);
        map.put("roleId", roleId);
        map.put("currentPage", (currentPage - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("year", Long.valueOf(DateUtil.getYear(new Date())));
        map.put("userId", userId);
        //消息总数
        Integer count = infoMapper.selectInfoSize(map) + infoMapper.selectInfoUserSize(map);
        //查询本用户所有消息集合
        List<Info> infoList = infoMapper.selectInfoByPage(map);
        //查询本用户未读消息id集合
        List<Long> unReadInfoIds = infoMapper.selectUnreadInfoId(map);
        //已读消息设置状态state为1
        for (Info item : infoList) {
            if (!unReadInfoIds.contains(item.getId())) {
                item.setState(1);
            } else {
                item.setState(0);
            }
        }
        pBean.setpList(infoList);
        pBean.setTotalCount(count);
        pBean.setTotalPage((count + pageSize - 1) / pageSize);
        return pBean;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Info getInfoByInfoId(Long infoId, Long userId) throws Exception {
        Info info = infoMapper.selectInfoByInfoId(infoId);
        if (info == null) {
            info = infoMapper.selectInfoUserById(infoId);
        }
        //将消息id为infoId的消息变为已读--->在数据库中删除t_user_unreadedinfo表中对应infoId的记录
        Map<String, Object> map = new HashMap<>();
        map.put("infoId", infoId);
        map.put("userId", userId);
        infoMapper.updateInfoReaded(map);
        return info;
    }

}
