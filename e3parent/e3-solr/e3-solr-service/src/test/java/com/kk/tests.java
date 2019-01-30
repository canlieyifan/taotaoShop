package com.kk;


import com.kk.solr.SolrService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class tests {

    @Test
    public void fun(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/*");
        SolrService solrServiceImpl = (SolrService) context.getBean("solrServiceImpl");
    }



}
