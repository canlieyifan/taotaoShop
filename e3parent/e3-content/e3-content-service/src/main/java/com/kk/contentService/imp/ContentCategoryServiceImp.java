package com.kk.contentService.imp;

import com.kk.contentService.ContentCategoryService;
import com.kk.mapper.ContentCategoryMapper;
import com.kk.pojo.ContentCategory;
import com.kk.pojo.ContentCategoryExample;
import com.kk.pojo.EasyUITreeNode;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImp implements ContentCategoryService {


    @Autowired
    private ContentCategoryMapper categoryMapper;

    @Override
//    删除节点
    public E3Result deleteContentCategory(Long id) {

        ContentCategory contentCategory = categoryMapper.selectByPrimaryKey(id);

//        如果是父节点 无操作
        if(contentCategory.getIsParent()) {
            return E3Result.ok();
        }

        categoryMapper.deleteByPrimaryKey(id);

//        判断父节点是否还有其他叶子节点 如果没有 则将父节点置为叶子节点
        Long parentId = contentCategory.getParentId();
        ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
        ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
        ContentCategoryExample.Criteria criteria1 = criteria.andParentIdEqualTo(parentId);
        List<ContentCategory> contentCategories = categoryMapper.selectByExample(contentCategoryExample);

        if(contentCategories.size()==0){
            ContentCategory contentCategory1 = categoryMapper.selectByPrimaryKey(parentId);
            contentCategory1.setIsParent(false);
            categoryMapper.updateByPrimaryKey(contentCategory1);
        }
        return E3Result.ok();
    }

    @Override
//    更新节点
    public E3Result updateContentCategory(Long id, String name) {
        ContentCategory contentCategory = categoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        categoryMapper.updateByPrimaryKey(contentCategory);
        return E3Result.ok(contentCategory);
    }

    @Override
//    添加叶子节点
    public E3Result addContentCategory(Long parentId, String name) {
//        新建一个pojo 进行相应封装
        ContentCategory contentCategory = new ContentCategory();

        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
//        默认0正常
        contentCategory.setStatus(0);
//        默认1排序
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

//        写入到数据库中
        int id= categoryMapper.insertSelective(contentCategory);

//        对父节点进行相应修改
       if(!categoryMapper.selectByPrimaryKey(parentId).getIsParent()){
           ContentCategory parentNode = categoryMapper.selectByPrimaryKey(parentId);
           parentNode.setIsParent(true);
           categoryMapper.updateByPrimaryKey(parentNode);
       }
        return E3Result.ok(contentCategory);
    }

    @Override
//    查询节点列表
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
