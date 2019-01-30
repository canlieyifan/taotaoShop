package com.kk.dao;

import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {



    @Autowired
    @Qualifier("cloudSolrServer")
    private SolrServer solrServer;

    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {

//        根据solrQuery条件查询结果集
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();

//        初始化返回对象
        SearchResult searchResult = new SearchResult();
        List<SearchItem> searchItems = new ArrayList<>();

//        取出总条数 并赋值
        long numFound = results.getNumFound();
        searchResult.setRecordCount(numFound);

//      取出高亮部分
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();


//        取出逐条数据 对应赋值
        for(SolrDocument solrDocument :results){

//            创建SearchItem单个对象 接受参数
            SearchItem item = new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));

//            取出高亮部分 如为空则保存原有title数据
            List<String> titles = highlighting.get(solrDocument.get("id")).get("item_title");

            if(titles.size()!=0||titles!=null){
                item.setTitle(titles.get(0));
            }else {
                item.setTitle((String) solrDocument.get("item_title"));
            }

            searchItems.add(item);
        }


        searchResult.setItemList(searchItems);

        return  searchResult;
    }



}
