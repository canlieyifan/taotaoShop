package com.kk.content;

import com.kk.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {
     List<EasyUITreeNode> getContentCategory(Long parentId);
}
