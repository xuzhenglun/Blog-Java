package com.reficul.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;


/**
 * Created by xuzl on 16-2-22.
 */

@Controller
public class attachment {
    @RequestMapping(value = "/attachments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource getFile(@RequestParam("name") String fileName) {
        return new FileSystemResource(new File("uploaded/" + fileName));
    }
}
