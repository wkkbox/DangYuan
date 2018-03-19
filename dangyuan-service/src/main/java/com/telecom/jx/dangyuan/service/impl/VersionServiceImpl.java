package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.VersionMapper;
import com.telecom.jx.dangyuan.pojo.po.Version;
import com.telecom.jx.dangyuan.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version getVersion() throws Exception {
        return versionMapper.selectVersion();
    }
}
