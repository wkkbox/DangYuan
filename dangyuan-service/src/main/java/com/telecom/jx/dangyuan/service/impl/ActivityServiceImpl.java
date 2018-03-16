package com.telecom.jx.dangyuan.service.impl;

import com.telecom.jx.dangyuan.mapper.*;
import com.telecom.jx.dangyuan.pojo.po.*;
import com.telecom.jx.dangyuan.pojo.vo.*;
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
    private SheZeMapper sheZeMapper;

    @Autowired
    private ActivityContentMapper activityContentMapper;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private HonorsAwardMapper honorsAwardMapper;

    @Autowired
    private ProfessDevelopMapper professDevelopMapper;

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

            item.setContentId(dangZeMapper.selectDangZeContentIdByArrangeIdAndUserId(map));

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
            System.out.println("__count=" + dangZeMapper.selectDangze3ArrangeCount(map));
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

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long saveSheZeContent(SheZeContent sheZeContent, Long shezeId, String time, Integer rate, Integer count) throws Exception {
        activityContentMapper.insertSheZeContent(sheZeContent);
        Long contentId = sheZeContent.getId();
        //在t_user_sheze插入记录，state为1
        Long userId = sheZeContent.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("shezeId", shezeId);
        map.put("time", time);
        map.put("year", DateUtil.getYear(new Date()));
        map.put("state", 1);
        map.put("rScore", 0);
        map.put("commitTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        sheZeMapper.insertUserSheZe(map);
        System.out.println("rate=" + rate);
        if (rate == 3) {//0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
            System.out.println("参加不限次数的社责活动");
            //判断是否能再插入t_sheze3_arrange
            System.out.println("__count=" + sheZeMapper.selectSheZe3ArrangeCount(map));
            if (sheZeMapper.selectSheZe3ArrangeCount(map) < count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                String newTime = sdf.format(DateUtil.addSeconds(date, 1));
                map.put("time", newTime);
                map.put("id", IDUtils.getItemId());
                sheZeMapper.insertSheZe3Arrange(map);
            }
        }
        return contentId;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long saveAchievementContent(AchievementContent achievementContent, Long achievementId, String time, Integer rate, Integer count) throws Exception {
        activityContentMapper.insertAchievementContent(achievementContent);
        Long contentId = achievementContent.getId();
        //在t_user_achievement插入记录，state为1
        Long userId = achievementContent.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("achievementId", achievementId);
        map.put("time", time);
        map.put("year", DateUtil.getYear(new Date()));
        map.put("state", 1);
        map.put("rScore", 0);
        map.put("commitTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        achievementMapper.insertUserAchievement(map);
        System.out.println("rate=" + rate);
        if (rate == 3) {//0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
            System.out.println("参加不限次数的工作业绩活动");
            //判断是否能再插入t_achievement3_arrange
            System.out.println("__count=" + achievementMapper.selectAchievement3ArrangeCount(map));
            if (achievementMapper.selectAchievement3ArrangeCount(map) < count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                String newTime = sdf.format(DateUtil.addSeconds(date, 1));
                map.put("time", newTime);
                map.put("id", IDUtils.getItemId());
                achievementMapper.insertAchievement3Arrange(map);
            }
        }
        return contentId;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long saveHonorsAwardContent(HonorsAwardContent honorsAwardContent, Long honorsAwardId, String time, Integer rate, Integer count) throws Exception {
        activityContentMapper.insertHonorsAwardContent(honorsAwardContent);
        Long contentId = honorsAwardContent.getId();
        //在t_user_honorsaward插入记录，state为1
        Long userId = honorsAwardContent.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("honorsAwardId", honorsAwardId);
        map.put("time", time);
        map.put("year", DateUtil.getYear(new Date()));
        map.put("state", 1);
        map.put("rScore", 0);
        map.put("commitTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        honorsAwardMapper.insertUserHonorsAward(map);
        System.out.println("rate=" + rate);
        if (rate == 3) {//0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
            System.out.println("参加不限次数的荣誉奖励活动");
            //判断是否能再插入t_honorsaward3_arrange
            System.out.println("__count=" + honorsAwardMapper.selectHonorsAward3ArrangeCount(map));
            if (honorsAwardMapper.selectHonorsAward3ArrangeCount(map) < count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                String newTime = sdf.format(DateUtil.addSeconds(date, 1));
                map.put("time", newTime);
                map.put("id", IDUtils.getItemId());
                honorsAwardMapper.insertHonorsAward3Arrange(map);
            }
        }
        return contentId;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long saveProfessDevelopContent(ProfessDevelopContent professDevelopContent, Long professdevelopId, String time, Integer rate, Integer count) throws Exception {
        activityContentMapper.insertProfessDevelopContent(professDevelopContent);
        Long contentId = professDevelopContent.getId();
        //在t_user_professdevelop插入记录，state为1
        Long userId = professDevelopContent.getUserId();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("professdevelopId", professdevelopId);
        map.put("time", time);
        map.put("year", DateUtil.getYear(new Date()));
        map.put("state", 1);
        map.put("rScore", 0);
        map.put("commitTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        professDevelopMapper.insertUserProfessDevelop(map);
        System.out.println("rate=" + rate);
        if (rate == 3) {//0表示每月一次，1表示每季度一次，2表示每年一次，3表示不限次数
            System.out.println("参加不限次数的专业提升活动");
            //判断是否能再插入t_professdevelop3_arrange
            System.out.println("__count=" + professDevelopMapper.selectProfessDevelop3ArrangeCount(map));
            if (professDevelopMapper.selectProfessDevelop3ArrangeCount(map) < count) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(time);
                String newTime = sdf.format(DateUtil.addSeconds(date, 1));
                map.put("time", newTime);
                map.put("id", IDUtils.getItemId());
                professDevelopMapper.insertProfessDevelop3Arrange(map);
            }
        }
        return contentId;
    }

    @Override
    public List<SheZeCustom> getSheZeCustoms(Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        System.out.println("userId=" + userId);
        map.put("month", DateUtil.getYear(new Date()) + "" + DateUtil.getMonth(new Date()));
        System.out.println("month=" + map.get("month"));
        map.put("year", DateUtil.getYear(new Date()));
        //得到不是无限次数的，在t_sheze_arrange中查
        List<SheZeCustom> sheZeCustomList = sheZeMapper.selectSheZeCustoms(map);
        //得到是无限次数的，在t_sheze3_arrange中查
        List<SheZeCustom> sheZe3CustomList = sheZeMapper.selectSheZe3Customs(map);
        //将不是无限次数的和是无限次数的放到一个list中
        List<SheZeCustom> sheZeCustoms = new ArrayList<>();
        for (SheZeCustom item : sheZeCustomList) {
            sheZeCustoms.add(item);
        }
        for (SheZeCustom item : sheZe3CustomList) {
            sheZeCustoms.add(item);
        }
        //查询附件内容
        for (SheZeCustom item : sheZeCustoms) {
            map.put("arrangeId", item.getArrangeId());

            item.setContentId(sheZeMapper.selectSheZeContentIdByArrangeIdAndUserId(map));

            item.setCommitContent(sheZeMapper.selectSheZeContentByArrangeIdAndUserId(map));
            item.setAttachmentList(sheZeMapper.selectSheZeAttachmentByArrangeIdAndUserId(map));
            map.put("shezeId", item.getShezeId());
            System.out.println("shezeId=" + item.getShezeId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(item.getTime());
            map.put("time", sdf.format(date));
            System.out.println("time=" + sdf.format(date));
            Integer state = sheZeMapper.selectSheZeState(map);
            System.out.println("state=" + state);
            if (state != null) {
                item.setState(state);
            } else {
                item.setState(0);//申请
            }
        }
        return sheZeCustoms;
    }

    @Override
    public List<AchievementCustom> getAchievementCustoms(Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        System.out.println("userId=" + userId);
        map.put("month", DateUtil.getYear(new Date()) + "" + DateUtil.getMonth(new Date()));
        System.out.println("month=" + map.get("month"));
        map.put("year", DateUtil.getYear(new Date()));
        //得到不是无限次数的，在t_achievement_arrange中查
        List<AchievementCustom> achievementCustomList = achievementMapper.selectAchievementCustoms(map);
        //得到是无限次数的，在t_achievement3_arrange中查
        List<AchievementCustom> achievement3CustomList = achievementMapper.selectAchievement3Customs(map);
        //将不是无限次数的和是无限次数的放到一个list中
        List<AchievementCustom> achievementCustoms = new ArrayList<>();
        for (AchievementCustom item : achievementCustomList) {
            achievementCustoms.add(item);
        }
        for (AchievementCustom item : achievement3CustomList) {
            achievementCustoms.add(item);
        }
        //查询附件内容
        for (AchievementCustom item : achievementCustoms) {
            map.put("arrangeId", item.getArrangeId());

            item.setContentId(achievementMapper.selectAchievementContentIdByArrangeIdAndUserId(map));

            item.setCommitContent(achievementMapper.selectAchievementContentByArrangeIdAndUserId(map));
            item.setAttachmentList(achievementMapper.selectAchievementAttachmentByArrangeIdAndUserId(map));
            map.put("achievementId", item.getAchievementId());
            System.out.println("achievementId=" + item.getAchievementId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(item.getTime());
            map.put("time", sdf.format(date));
            System.out.println("time=" + sdf.format(date));
            Integer state = achievementMapper.selectAchievementState(map);
            System.out.println("state=" + state);
            if (state != null) {
                item.setState(state);
            } else {
                item.setState(0);//申请
            }
        }
        return achievementCustoms;
    }

    @Override
    public List<HonorsAwardCustom> getHonorsAwardCustoms(Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        System.out.println("userId=" + userId);
        map.put("month", DateUtil.getYear(new Date()) + "" + DateUtil.getMonth(new Date()));
        System.out.println("month=" + map.get("month"));
        map.put("year", DateUtil.getYear(new Date()));
        //得到不是无限次数的，在t_honorsaward_arrange中查
        List<HonorsAwardCustom> honorsAwardCustomList = honorsAwardMapper.selectHonorsAwardCustoms(map);
        //得到是无限次数的，在t_honorsaward3_arrange中查
        List<HonorsAwardCustom> honorsAward3CustomList = honorsAwardMapper.selectHonorsAward3Customs(map);
        //将不是无限次数的和是无限次数的放到一个list中
        List<HonorsAwardCustom> honorsAwardCustoms = new ArrayList<>();
        for (HonorsAwardCustom item : honorsAwardCustomList) {
            honorsAwardCustoms.add(item);
        }
        for (HonorsAwardCustom item : honorsAward3CustomList) {
            honorsAwardCustoms.add(item);
        }
        //查询附件内容
        for (HonorsAwardCustom item : honorsAwardCustoms) {
            map.put("arrangeId", item.getArrangeId());

            item.setContentId(honorsAwardMapper.selectHonorsAwardContentIdByArrangeIdAndUserId(map));

            item.setCommitContent(honorsAwardMapper.selectHonorsAwardContentByArrangeIdAndUserId(map));
            item.setAttachmentList(honorsAwardMapper.selectHonorsAwardAttachmentByArrangeIdAndUserId(map));
            map.put("honorsAwardId", item.getHonorsAwardId());
            System.out.println("honorsAwardId=" + item.getHonorsAwardId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(item.getTime());
            map.put("time", sdf.format(date));
            System.out.println("time=" + sdf.format(date));
            Integer state = honorsAwardMapper.selectHonorsAwardState(map);
            System.out.println("state=" + state);
            if (state != null) {
                item.setState(state);
            } else {
                item.setState(0);//申请
            }
        }
        return honorsAwardCustoms;
    }

    @Override
    public List<ProfessDevelopCustom> getProfessDevelopCustoms(Long userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        System.out.println("userId=" + userId);
        map.put("month", DateUtil.getYear(new Date()) + "" + DateUtil.getMonth(new Date()));
        System.out.println("month=" + map.get("month"));
        map.put("year", DateUtil.getYear(new Date()));
        //得到不是无限次数的，在t_professdevelop_arrange中查
        List<ProfessDevelopCustom> professDevelopCustomList = professDevelopMapper.selectProfessDevelopCustoms(map);
        //得到是无限次数的，在t_professdevelop3_arrange中查
        List<ProfessDevelopCustom> professDevelop3CustomList = professDevelopMapper.selectProfessDevelop3Customs(map);
        //将不是无限次数的和是无限次数的放到一个list中
        List<ProfessDevelopCustom> professDevelopCustoms = new ArrayList<>();
        for (ProfessDevelopCustom item : professDevelopCustomList) {
            professDevelopCustoms.add(item);
        }
        for (ProfessDevelopCustom item : professDevelop3CustomList) {
            professDevelopCustoms.add(item);
        }
        //查询附件内容
        for (ProfessDevelopCustom item : professDevelopCustoms) {
            map.put("arrangeId", item.getArrangeId());

            item.setContentId(professDevelopMapper.selectProfessDevelopContentIdByArrangeIdAndUserId(map));

            item.setCommitContent(professDevelopMapper.selectProfessDevelopContentByArrangeIdAndUserId(map));
            item.setAttachmentList(professDevelopMapper.selectProfessDevelopAttachmentByArrangeIdAndUserId(map));
            map.put("professdevelopId", item.getProfessdevelopId());
            System.out.println("professdevelopId=" + item.getProfessdevelopId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(item.getTime());
            map.put("time", sdf.format(date));
            System.out.println("time=" + sdf.format(date));
            Integer state = professDevelopMapper.selectProfessDevelopState(map);
            System.out.println("state=" + state);
            if (state != null) {
                item.setState(state);
            } else {
                item.setState(0);//申请
            }
        }
        return professDevelopCustoms;
    }

    @Override
    public void editDangZeContent(Map<String, Object> map) throws Exception {
        dangZeMapper.updateDangZeContent(map);
    }

    @Override
    public void editSheZeContent(Map<String, Object> map) throws Exception {
        sheZeMapper.updateSheZeContent(map);
    }

    @Override
    public void editAchievementContent(Map<String, Object> map) throws Exception {
        achievementMapper.updateAchievementContent(map);
    }

    @Override
    public void editHonorsAwardContent(Map<String, Object> map) throws Exception {
        honorsAwardMapper.updateHonorsAwardContent(map);
    }

    @Override
    public void editProfessDevelopContent(Map<String, Object> map) throws Exception {
        professDevelopMapper.updateProfessDevelopContent(map);
    }

    @Override
    public void updateDangZeState(Map<String, Object> map) throws Exception {
        dangZeMapper.updateDangZeState(map);
    }

    @Override
    public void updateSheZeState(Map<String, Object> map) throws Exception {
        sheZeMapper.updateSheZeState(map);
    }

    @Override
    public void updateAchievementState(Map<String, Object> map) throws Exception {
        achievementMapper.updateAchievementState(map);
    }

    @Override
    public void updateHonorsAwardState(Map<String, Object> map) throws Exception {
        honorsAwardMapper.updateHonorsAwardState(map);
    }

    @Override
    public void updateProfessDevelopState(Map<String, Object> map) throws Exception {
        professDevelopMapper.updateProfessDevelopState(map);
    }
}
