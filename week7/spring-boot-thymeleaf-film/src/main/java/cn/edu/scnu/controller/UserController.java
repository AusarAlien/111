package cn.edu.scnu.controller;

import cn.edu.scnu.Util.PasswordUtil;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model, String returnUrl){
        // 传递当前年份到前端
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("returnUrl", returnUrl);
        return "login";
    }
    @RequestMapping("/toLogOut")
    public String logOut(HttpSession session,String returnUrl){
        session.removeAttribute("user");
        if (returnUrl != null && !returnUrl.equals("")) {
            return "redirect:" + returnUrl;
        }
        return "redirect:/index";
    }
    @RequestMapping("/toRegisterPage")
    public String toRegisterPage(Model model, String returnUrl){
        model.addAttribute("currentYear" , Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("returnUrl", returnUrl);
        return "register";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public List<String> doLogin(String username, String password, HttpSession session) {
        TbUser user = userService.login(username, password);

        List<String> list = new ArrayList<>();

        if (user != null) {
            user.setPwd(""); // 脱敏
            session.setAttribute("user", user);
            list.add("登录成功!");
        } else {
            list.add("用户名或密码错误!");
        }
        return list;
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public List<String> doRegister(String username, String password, String password2) {
        TbUser user = userService.getById(username);

        List<String> list = new ArrayList<>();

        if(user != null){
            list.add("用户名已存在!");
            return list;
        }
        if (!password.equals(password2)) {
            list.add("两次密码不一致!");
            return list;
        }
        TbUser user1 = new TbUser();
        user1.setUsername(username);
        user1.setPwd(PasswordUtil.encode(password));
        userService.save(user1);

        list.add("注册成功");
        return list;
    }
}