package com.kk.controller;

import com.kk.contentService.ContentCategoryService;
import com.kk.contentService.ContentService;
import com.kk.pojo.Content;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.EasyUITreeNode;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode>
    selectCategoryList(@RequestParam(name = "id" ,defaultValue = "0") Long parentId ){
        List<EasyUITreeNode> contentCategoryList = contentCategoryService.getContentCategoryList(parentId);
        return contentCategoryList;
    }

    @RequestMapping("content/category/create")
    @ResponseBody
    public E3Result addContentCategory(Long parentId,String name){
        E3Result e3Result = contentCategoryService.addContentCategory( parentId,name);
        return e3Result;
    }


    @RequestMapping("/content/category/update")
    @ResponseBody
    public  E3Result updateContentCategory(Long id ,String name){
        E3Result e3Result = contentCategoryService.updateContentCategory(id, name);
        return e3Result;
    }

    @RequestMapping("/content/category/delete")
    @ResponseBody
    public E3Result deleteContentCategory(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }



    @RequestMapping("content/query/list")
    @ResponseBody
    public EasyUIResult getContentList(Long categoryId,Integer page,Integer rows){
        EasyUIResult contentList = contentService.getContentList(categoryId, page, rows);
        return contentList;
    }



    @RequestMapping("content/save")
    @ResponseBody
    public E3Result addContent(Content content){
        content.setUpdated(new Date());
        content.setCreated(new Date());

        E3Result e3Result = contentService.addContent(content);
        return e3Result;
    }


    @RequestMapping("content/delete")
    @ResponseBody
    public E3Result deleteContent(String ids){
        String[] split = ids.split(",");
        List<String> datas = Arrays.asList(split);
        E3Result e3Result = contentService.deleteContent(datas);
        return e3Result;
    }

}











