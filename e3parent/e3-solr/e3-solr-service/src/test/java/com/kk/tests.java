package com.kk;


import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;
import com.kk.solr.SolrService;
import com.kk.solrService.impl.SolrServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class tests {

    @Test
    public void fun(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/*");
        SolrService solrServiceImpl = (SolrService) context.getBean("solrServiceImpl");


        try {
            SearchResult result = solrServiceImpl.search("手机", 1, 10);
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
