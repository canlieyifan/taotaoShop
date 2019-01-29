package com.kk.solr;

import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;
import com.kk.utils.E3Result;

import java.util.List;

public interface SolrService {

    List<SearchItem>  getSearchItem();

    E3Result importAllItems();

    SearchResult search(String keywords,Integer page,Integer rows) throws Exception;
}
