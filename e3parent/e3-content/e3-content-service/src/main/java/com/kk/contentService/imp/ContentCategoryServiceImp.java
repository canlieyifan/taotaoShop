package com.kk.contentService.imp;

import com.kk.contentService.ContentCategoryService;
import com.kk.mapper.ContentCategoryMapper;
import com.kk.pojo.ContentCategory;
import com.kk.pojo.ContentCategoryExample;
import com.kk.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ContentCategoryServiceImp implements ContentCategoryService {


    @Autowired
    private ContentCategoryMapper categoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {

//        数据查询
        ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
        ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria().andParentIdEqualTo(parentId);
        List<ContentCategory> contentCategories = categoryMapper.selectByExample(contentCategoryExample);

//        数据封装
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();


        for(ContentCategory contentCategory: contentCategories){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setState(contentCategory.getIsParent()?"closed":"open");
            easyUITreeNode.setId(contentCategory.getId());
            easyUITreeNode.setText(contentCategory.getName());
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
