package com.kk.contentService;

import com.kk.pojo.EasyUITreeNode;
import com.kk.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {
    public List<EasyUITreeNode> getContentCategoryList(Long parentId);

    public E3Result addContentCategory(Long parentId,String name);

    public E3Result updateContentCategory(Long id,String name);

    public E3Result deleteContentCategory(Long id);
}
