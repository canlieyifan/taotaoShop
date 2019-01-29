package com.kk.search;

import com.kk.pojo.SearchResult;
import com.kk.solr.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SolrService solrService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer rows;

    @RequestMapping("search")
    public String fun1(String keyword,
                       @RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {


         keyword = new String(keyword.getBytes("iso-8859-1"), "UTF-8");

        SearchResult searchResult = solrService.search(keyword, page, rows);


        
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());

        return "search";
    }






}
