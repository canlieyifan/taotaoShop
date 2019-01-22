package com.kk.controller;

import com.kk.contentService.ContentService;
import com.kk.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {


    @Value("${Content_LunBo_id}")
    private Long Content_LunBo_id;

    @Autowired
    private ContentService contentService;


    @RequestMapping("index")
    public String fun1(Model model){
        List<Content> contentByCategory = contentService.getContentByCategory(Content_LunBo_id);
        model.addAttribute("ad1List",contentByCategory);
        return "index";
    }



}
