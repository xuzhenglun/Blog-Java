package com.reficul.controller;

import com.reficul.mapper.CategoryMapper;
import com.reficul.mapper.UserMapper;
import com.reficul.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xuzl on 16-2-19.
 */

@Controller
public class category {
    @Autowired
    private CategoryMapper category;
    @Autowired
    private UserMapper user;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String doGet(Model model,
                        @RequestParam(value = "op", defaultValue = "show") String action,
                        @RequestParam(value = "id", defaultValue = "null") String id,
                        @CookieValue("name") String Name,
                        @CookieValue("PasswdHash") String PasswdHash) {
        if (action.equals("del")) {
            User u = user.findUserByName(Name);
            if (u != null && u.getPasswdHash().equals(PasswdHash)) {
                category.deleteCategoryById(id);
                return "redirect:/category";
            } else {
                return "redirect:/login";
            }
        } else {
            model.addAttribute("title", "Categories");
            model.addAttribute("isCategory", true);
            model.addAttribute("Categories", category.findAllCategories());
            return "category";
        }
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String doPost(@RequestParam("name") String Category,
                         @CookieValue("name") String Name,
                         @CookieValue("PasswdHash") String PasswdHash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(PasswdHash)) {
            category.insertCategory(Category);
            return "redirect:/category";
        } else {
            return "redirect:/login";
        }
    }
}
