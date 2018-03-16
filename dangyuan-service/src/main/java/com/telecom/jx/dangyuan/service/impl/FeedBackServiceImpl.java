package com.telecom.jx.dangyuan.service.impl;


import com.telecom.jx.dangyuan.mapper.FeedBackMapper;
import com.telecom.jx.dangyuan.pojo.po.FeedBack;
import com.telecom.jx.dangyuan.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;

    @Override
    public void addFeedBack(FeedBack feedBack) throws Exception {
        feedBackMapper.insertFeedBack(feedBack);
    }
}
