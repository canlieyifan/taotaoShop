package com.kk.mapper;


import com.kk.pojo.SearchItem;
import com.kk.pojo.SearchResult;

import java.util.List;

public interface SearchMapper {
    List<SearchItem> getSearchItemList();
    SearchItem getSearchItemById(Long id);
}