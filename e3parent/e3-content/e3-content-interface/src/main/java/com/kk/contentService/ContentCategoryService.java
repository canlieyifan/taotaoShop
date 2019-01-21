package com.kk.contentService;

import com.kk.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
    public List<EasyUITreeNode> getContentCategoryList(Long parentId);
}
