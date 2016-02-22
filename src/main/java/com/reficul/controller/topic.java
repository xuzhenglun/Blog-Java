package com.reficul.controller;

import com.reficul.mapper.CategoryMapper;
import com.reficul.mapper.CommentMapper;
import com.reficul.mapper.TopicMapper;
import com.reficul.mapper.UserMapper;
import com.reficul.model.Category;
import com.reficul.model.Topic;
import com.reficul.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xuzl on 16-2-19.
 */

@Controller
public class topic {
    @Autowired
    private TopicMapper topic;
    @Autowired
    private UserMapper user;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CommentMapper comments;

    @RequestMapping("topic")
    public String topic(Model model) {
        model.addAttribute("title", "Topics");
        model.addAttribute("isTopic", true);
        model.addAttribute("Topics", topic.findAllTopics());
        return "topic";
    }

    @RequestMapping("/topic/view/{tid}")
    public String view(Model model,
                       @PathVariable("tid") String tid) {
        Topic t = topic.findTopicById(tid);
        model.addAttribute("topic", t);
        model.addAttribute("Comments", comments.findAllComments(tid));
        topic.modifyViews(tid, t.getViews() + 1);
        return "view";
    }

    @RequestMapping(value = "/topic/modify", method = RequestMethod.GET)
    public String GetModPage(Model model, @RequestParam("tid") String tid) {
        model.addAttribute("topic", topic.findTopicById(tid));
        model.addAttribute("Mode", "Modify");
        return "add";
    }

    @RequestMapping(value = "/topic/Modify", method = RequestMethod.POST)
    public String modifyTopic(@RequestParam("tid") String tid,
                              @RequestParam("title") String title,
                              @RequestParam("category") String category,
                              @RequestParam("tags") String tags,
                              @RequestParam("content") String content,
                              @RequestParam("attachment") MultipartFile attachment,
                              @CookieValue(value = "name", defaultValue = "null") String Name,
                              @CookieValue(value = "PasswdHash", defaultValue = "null") String Passwdhash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(Passwdhash)) {
            topic.updateTopic(tid, u.getId(), title, category, tags, content, attachment.getName());
            return "redirect:/topic";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/topic/add", method = RequestMethod.GET)
    public String GetAddPage(Model model) {
        model.addAttribute("title", "New Topic");
        model.addAttribute("Mode", "Add");
        return "add";
    }


    @RequestMapping(value = "/topic/Add", method = RequestMethod.POST)
    public String newTopic(@RequestParam("title") String title,
                           @RequestParam("category") String category,
                           @RequestParam("tags") String tags,
                           @RequestParam("content") String content,
                           @RequestParam("attachment") MultipartFile attachment,
                           @CookieValue(value = "name", defaultValue = "null") String Name,
                           @CookieValue(value = "PasswdHash", defaultValue = "null") String Passwdhash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(Passwdhash)) {
            topic.insertTopic(u.getId(), title, category, tags, content, attachment.getOriginalFilename());
            if (category != null) {
                long topicCounts = topic.findTopicCounts(category);
                Category cate = categoryMapper.findCategoryByName(category);
                if (category != null && cate != null) {
                    categoryMapper.modifyCategoryCounts(cate.getId(), topicCounts);
                } else {
                    categoryMapper.insertCategorywithnum(category, topicCounts);
                }
            }

            if (!attachment.isEmpty()) {
                try {
                    byte[] bytes = attachment.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File("uploaded/" + attachment.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    System.out.print(e + "");
                }
            }

            return "redirect:/topic";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/topic/delete/{tid}")
    public String deleteTopic(@PathVariable("tid") String tid,
                              @CookieValue(value = "name", defaultValue = "null") String Name,
                              @CookieValue(value = "PasswdHash", defaultValue = "null") String Passwdhash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(Passwdhash)) {

            Topic t = topic.findTopicById(tid);
            if (t.getCategory() != null) {
                long topicCounts = topic.findTopicCounts(t.getCategory());
                try {
                    String cid = categoryMapper.findCategoryByName(t.getCategory()).getId();
                    if (topicCounts == 1) {
                        categoryMapper.deleteCategoryById(cid);
                    } else {
                        categoryMapper.modifyCategoryCounts(cid, topicCounts - 1);
                    }
                } catch (NullPointerException ex) {
                    System.out.print("Can't find the fucking Category: " + t.getCategory());
                }
            }
            topic.deleteTopic(tid);
            comments.deleteCommentByTid(tid);

            return "redirect:/topic";
        } else {
            return "redirect:/login";
        }
    }
}
