package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.ActivityContentMapper;
import com.telecom.jx.dangyuan.mapper.DangZeMapper;
import com.telecom.jx.dangyuan.pojo.po.DangZeContent;
import com.telecom.jx.dangyuan.pojo.po.DangZe;
import com.telecom.jx.dangyuan.pojo.vo.DangZeCustom;
import com.telecom.jx.dangyuan.service.ActivityService;
import com.telecom.jx.dangyuan.util.DateUtil;
import com.telecom.jx.dangyuan.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private DangZeMapper dangZeMapper;

    @Autowired
    private ActivityContentMapper activityContentMapper;

    @Override
    public List<DangZe> getDangZes(Long userId, Long roleId) throws Exception {
        return null;
    }

    @Override
    public List<DangZeCustom> getDangZeCustoms(Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        System.out.println("userId=" + userId);
        map.put("month", DateUtil.getYear(new Date()) + "" + DateUtil.getMonth(new Date()));
        System.out.println("month=" + map.get("month"));
        map.put("year", DateUtil.getYear(new Date()));
        //得到不是无限次数的，在t_dangze_arrange中查
        List<DangZeCustom> dangZeCustomList = dangZeMapper.selectDangZeCustoms(map);
        //得到是无限次数的，在t_dangze3_arrange中查
        List<DangZeCustom> dangZe3CustomList = dangZeMapper.selectDangZe3Customs(map);
        //将不是无限次数的和是无限次数的放到一个list中
        List<DangZeCustom> dangZeCustoms = new ArrayList<>();
        for (DangZeCustom item : dangZeCustomList) {
            dangZeCustoms.add(item);
        }
        for (DangZeCustom item : dangZe3CustomList) {
            dangZeCustoms.add(item);
        }
        //查询附件内容
        for (DangZeCustom item : dangZeCustoms) {
            map.put("arrangeId", item.getArrangeId());
            item.setCommitContent(dangZeMapper.selectDangZeContentByArrangeIdAndUserId(map));
            item.setAttachmentList(dangZeMapper.selectDangZeAttachmentByArrangeIdAndUserId(map));
            map.put("dangzeId", item.getDangzeId());
            System.out.println("dangzeId=" + item.getDangzeId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(item.getTime());
            map.put("time", sdf.format(date));
            System.out.println("time=" + sdf.format(date));
            Integer state = dangZeMapper.selectDangZeState(map);
            System.out.println("state=" + state);
            if (state != null) {
                item.setState(state);
            } else {
                item.setState(0);//申请
            }
        }
        return dangZeCustoms;
    }

    /**
     * 未使用
     *
     * @param activityContent
     * @return
     * @throws Exception
     */
    @Override
    public Long saveActivityContent(DangZeContent activityContent) throws Exception {
        activityContentMapper.insertActivityContent(activityContent);
        return activityContent.getId();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long saveDangZeContent(DangZeContent dangZeContent, Long dangzeId, String time, Integer rate, Integer count) throws Exception {
        activityContentMapper.insertDangZeContent(dangZeContent);
        Long contentId = dangZeContent.getId();
        //在t_user_dangze插入记录，state为1
        Long userId = dangZeContent.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("dangzeId", dangzeId);
        map.put("time", time);
        map.put("year", DateUtil.getYear(new Date()));
        map.put("state", 1);
        map.put("rScore", 0);
        map.put("commitTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        dangZeMapper.insertUserDangZe(map);

        System.out.println("rate=" + rate);
        if (rate == 3) {//0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
            System.out.println("参加不限次数的党责活动");
            //判断是否能再插入t_dangze3_arrange
            System.out.println("__count="+dangZeMapper.selectDangze3ArrangeCount(map));
            if (dangZeMapper.selectDangze3ArrangeCount(map) < count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                String newTime = sdf.format(DateUtil.addSeconds(date, 1));
                map.put("time", newTime);
                map.put("id", IDUtils.getItemId());
                dangZeMapper.insertDangZe3Arrange(map);
            }
        }
        return contentId;
    }
}
