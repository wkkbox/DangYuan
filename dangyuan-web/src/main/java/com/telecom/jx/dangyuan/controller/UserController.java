package com.telecom.jx.dangyuan.controller;


import com.telecom.jx.dangyuan.pojo.po.User;
import com.telecom.jx.dangyuan.pojo.vo.Score;
import com.telecom.jx.dangyuan.service.InfoService;
import com.telecom.jx.dangyuan.service.UserService;
import com.telecom.jx.dangyuan.util.CryptographyUtil;
import com.telecom.jx.dangyuan.util.DateUtil;
import com.telecom.jx.dangyuan.util.JsonUtils;
import com.telecom.jx.dangyuan.util.dto.MessageResult;
import com.telecom.jx.dangyuan.util.dto.PageBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InfoService infoService;

    /**
     * 登录控制器（accountName，password）
     *
     * @param user
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/login"}, produces = "application/json;charset=utf-8")
    public String login(User user, Model model, HttpServletRequest request) {
        System.out.println(request.getMethod());
        System.out.println(user);
        /*if ("GET".equals(request.getMethod())) {
            //回显
            model.addAttribute("msg", "提交方式有误");
            return "login";
        }*/
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccountName(), user.getPassword());
        try {
            subject.login(token);
            Session session = subject.getSession();
            System.out.println("sessionId:" + session.getId());
            System.out.println("sessionHost:" + session.getHost());
            System.out.println("sessionTimeout:" + session.getTimeout());
            //session.setAttribute("info", "session的数据");
        } catch (Exception e) {
            e.printStackTrace();
            //回显
            MessageResult result = new MessageResult(false, "用户名或密码错误");
            return JsonUtils.objectToJson(result);
        }
        User currentUser = null;
        if (subject.isAuthenticated()) {
            try {
                currentUser = userService.getUserByAccountName(user.getAccountName());
                //登录成功currentUser存入session
                subject.getSession().setAttribute("currentUser", currentUser);
                //登录成功查询未读信息条数
                Map<String, Object> map = new HashMap<>();
                map.put("userId", currentUser.getId());
                map.put("year", DateUtil.getYear(new Date()));
                int unreadInfoCount = infoService.getUnreadInfoCount(map);
                currentUser.setUnreadInfoCount(unreadInfoCount);
                //User currentUser = userService.getUserByName(user.getUserName());
                //List<Menu> menus = userService.getMenusByUserId(currentUser.getId());
                //System.out.println("菜单：" + menus.size() + " " + menus);
                //model.addAttribute("menus", menus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //登录成功
        return JsonUtils.objectToJson(new MessageResult(true, "登录成功", currentUser));
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param accountName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/editPassword"}, produces = "application/json;charset=utf-8")
    public String editPassword(String oldPwd, String newPwd, String accountName) {
        MessageResult result = null;
        User currentUser = null;
        try {
            currentUser = userService.getUserByAccountName(accountName);
            if (currentUser == null) {
                result = new MessageResult(false, "帐户名错误", null);
            } else {
                if (!currentUser.getPassword().equals(CryptographyUtil.md5(oldPwd, "dangyuan", 2))) {
                    result = new MessageResult(false, "原密码输入错误", null);
                } else {
                    try {
                        System.out.println("currentUser.getId()=" + currentUser.getId());
                        userService.editPassword(currentUser.getId(), newPwd);
                        result = new MessageResult(true, "密码修改成功", null);
                    } catch (Exception e) {
                        result = new MessageResult(false, "密码修改失败", null);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 管理员退出控制器，未使用
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        //执行subject.logout()会自动调用stop()方法来销毁会话session.stop()
        subject.logout();
        return "login";
    }

    /**
     * 分页获得党员积分公示信息
     *
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/scorePublicity"}, produces = "application/json;charset=utf-8")
    public String scorePublicityByPage(Integer currentPage, Integer pageSize, Model model, HttpServletRequest request) {
        MessageResult result = null;
        PageBean<Score> scorePage = null;
        try {
            scorePage = userService.getScorePublicityByPage(currentPage, pageSize);
            result = new MessageResult(true, "查询成功", scorePage);
        } catch (Exception e) {
            e.printStackTrace();
            result = new MessageResult(false, "查询失败", null);
        }
        return JsonUtils.objectToJson(result);
    }

    /**
     * 未使用
     *
     * @return
     */
    @RequestMapping("/createUser")
    @RequiresRoles("admin")
    public String addUser() {
        return "addUser";
    }

    /**
     * 未使用
     *
     * @return
     */
    @RequestMapping("/deleteUser")
    @RequiresRoles("admin")
    public String delUser() {
        return "delUser";
    }

    /**
     * 未使用
     *
     * @return
     */
    @RequestMapping("/updateUser")
    @RequiresPermissions(value = {"user:update"})
    public String updateUser() {
        return "updateUser";
    }

    /**
     * 未使用
     *
     * @param model
     * @return
     */
    @RequestMapping("/listUser")
    @RequiresPermissions(value = {"user:view"})
    public String showUsers(Model model) {
        List<User> userList = null;
        try {
            userList = userService.showUsers();
            System.out.println(userList);
        } catch (Exception e) {
            e.printStackTrace();
            return "error/error";
        }
        model.addAttribute("userList", userList);
        return "listUser";
    }

}
