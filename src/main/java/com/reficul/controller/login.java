package com.reficul.controller;

import com.reficul.Toolkit.Hash;
import com.reficul.mapper.UserMapper;
import com.reficul.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xuzl on 16-2-19.
 */

@Controller
public class login {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String doGet(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @Autowired
    private UserMapper user;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doPost(@RequestParam("uname") String name,
                         @RequestParam("pwd") String passwd,
                         @RequestParam(value = "autoLogin", defaultValue = "null") String autoLogin,
                         HttpServletResponse response) {
        Hash hash = new Hash(passwd);
        User supposedUser = user.findUserByName(name);
        if (supposedUser == null) {
            System.out.print("There is no user named " + name + "with passwd:" + passwd + "\n");
            return "login";
        }
        if (supposedUser.getPasswdHash().equals(hash.MD5().getOutput())) {
            Cookie cookieName = new Cookie("name", name);
            Cookie cookiePsw = new Cookie("PasswdHash", supposedUser.getPasswdHash());
            Cookie cookieStatus = new Cookie("IsLogin", "1");
            Cookie cookieUid = new Cookie("Uid", supposedUser.getId() + "");
            System.out.print(autoLogin);

            if (autoLogin.equals("on")) {
                cookieName.setMaxAge(60 * 60 * 24 * 30);
                cookiePsw.setMaxAge(60 * 60 * 24 * 30);
                cookieStatus.setMaxAge(60 * 60 * 24 * 30);
                cookieUid.setMaxAge(60 * 60 * 24 * 30);
            }
            response.addCookie(cookieName);
            response.addCookie(cookiePsw);
            response.addCookie(cookieStatus);//非敏感操作验证，减少查询数据库次数
            response.addCookie(cookieUid);
            return "index";
        } else {
            return "login";
        }
    }
}
