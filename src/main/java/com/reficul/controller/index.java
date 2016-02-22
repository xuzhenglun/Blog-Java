package com.reficul.controller;

import com.reficul.mapper.CategoryMapper;
import com.reficul.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xuzl on 16-2-18.
 */

@Controller
public class index {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/index";
    }

    @Autowired
    private TopicMapper topics;
    @Autowired
    private CategoryMapper category;

    @RequestMapping("/index")
    public String index(Model model,
                        @RequestParam(value = "cate", defaultValue = "null") String cate) {
        model.addAttribute("title", "Home");
        model.addAttribute("isHome", true);
        if (cate.equals("null")) {
            model.addAttribute("Topics", topics.findAllTopics());
        } else {
            model.addAttribute("Topics", topics.findAllTopicsByCategory(cate));
        }
        model.addAttribute("Categories", category.findAllCategories());
        return "index";
    }
}
