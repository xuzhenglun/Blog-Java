package com.reficul.controller;

import com.reficul.mapper.CommentMapper;
import com.reficul.mapper.TopicMapper;
import com.reficul.mapper.UserMapper;
import com.reficul.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * Created by xuzl on 16-2-21.
 */

@Controller
public class comment {
    @Autowired
    private CommentMapper comments;
    @Autowired
    private UserMapper user;
    @Autowired
    private TopicMapper topic;

    @RequestMapping(value = "/topic/{tid}/reply/{rid}", method = RequestMethod.GET)
    public String delComment(@PathVariable("tid") String tid,
                             @PathVariable("rid") String rid,
                             @RequestParam("op") String action,
                             @CookieValue(value = "name", defaultValue = "null") String Name,
                             @CookieValue(value = "PasswdHash", defaultValue = "null") String Passwdhash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(Passwdhash) && action.equals("delete")) {
            comments.deleteCommentById(rid);
            topic.modifyReplyCounts(tid, topic.findTopicById(tid).getReplyCounts() - 1);
            return "redirect:/topic/view/" + tid;
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/reply/add", method = RequestMethod.POST)
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("tid") String tid,
                             @CookieValue(value = "Uid", defaultValue = "null") String uid,
                             @CookieValue(value = "name", defaultValue = "null") String Name,
                             @CookieValue(value = "PasswdHash", defaultValue = "null") String Passwdhash) {
        User u = user.findUserByName(Name);
        if (u != null && u.getPasswdHash().equals(Passwdhash)) {
            if (uid.equals("null")) {
                return "redirect:/topic/view/" + tid;
            }
            comments.insertComment(tid, uid, content);
            topic.modifyReplyCounts(tid, topic.findTopicById(tid).getReplyCounts() + 1);
            topic.modifyLastReply(tid, new Timestamp(System.currentTimeMillis()));
            return "redirect:/topic/view/" + tid;
        } else {
            return "redirect:/login";
        }
    }
}