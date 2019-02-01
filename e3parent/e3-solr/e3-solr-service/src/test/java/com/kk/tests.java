package com.kk;


import com.kk.mapper.SearchMapper;
import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;
import com.kk.solr.SolrService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-solr.xml")
public class tests {


    @Autowired
    private SolrServer solrServer;

    @Test
    public void fun() throws IOException, SolrServerException {

        solrServer.commit();


    }



}
