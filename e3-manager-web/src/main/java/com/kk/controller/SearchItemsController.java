package com.kk.controller;

import com.kk.solr.SolrService;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemsController {

    @Autowired
    private SolrService solrService;

    @RequestMapping("index/item/import")
    @ResponseBody
    public E3Result importAllItems(){
        E3Result e3Result = solrService.importAllItems();
        System.out.println("e3Result = " + e3Result);
        return e3Result;
    }

}
