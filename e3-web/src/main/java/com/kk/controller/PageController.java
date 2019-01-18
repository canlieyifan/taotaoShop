package com.kk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @RequestMapping("/")
    public String showPage(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String selectPage(@PathVariable String page){
        return page;
    }
}
