package com.kk.contentService;

import com.kk.pojo.Content;
import com.kk.pojo.EasyUIResult;
import com.kk.utils.E3Result;

import java.util.List;

public interface ContentService {
     EasyUIResult getContentList(Long categoryId, Integer page, Integer rows);
     E3Result addContent(Content content);
     E3Result deleteContent(List<String> ids);
     List<Content> getContentByCategory(Long categoryId);
}
