package com.kk.solrService.impl;

import com.kk.dao.SearchDao;
import com.kk.mapper.SearchMapper;
import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;
import com.kk.solr.SolrService;
import com.kk.utils.E3Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolrServiceImpl implements SolrService {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    @Qualifier("cloudSolrServer")
    private SolrServer solrServer;

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<SearchItem> getSearchItem() {
        List<SearchItem> searchItems = searchMapper.getSearchItemList();
        return searchItems;
    }


    @Override
    public SearchResult search(String keywords, Integer page, Integer rows) throws SolrServerException {
//        创建查询对象
        SolrQuery solrQuery = new SolrQuery();

//        查询条件
        solrQuery.setQuery(keywords);


//       设置分页信息
        page=page<1?1:page;
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);

//        设置默认搜索域
        solrQuery.set("df", "item_title");


//      设置高亮 并对 item_title中存在关键字 进行高亮显示
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");

        SearchResult search = searchDao.search(solrQuery);

        if(search.getRecordCount()%rows==0){
            search.setTotalPages((int) (search.getRecordCount()/rows));
        }else{
            search.setTotalPages((int) (search.getRecordCount()/rows)+1);
        }



        return search;
    }



    @Override
    public E3Result importAllItems() {
        List<SearchItem> searchItems = searchMapper.getSearchItemList();
        try {
            for (SearchItem searchItem : searchItems) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                solrServer.add(document);
            }
            solrServer.commit();
            return E3Result.ok();
        }
        catch (Exception e){
            e.printStackTrace();
            return E3Result.build(500,"索引导入异常");
        }


    }







}
