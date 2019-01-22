package com.kk.contentService.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.contentService.ContentService;
import com.kk.mapper.ContentMapper;
import com.kk.pojo.Content;
import com.kk.pojo.ContentExample;
import com.kk.pojo.EasyUIResult;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImp implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
//    查找操作
    public EasyUIResult getContentList(Long categoryId,Integer page,Integer rows) {

//        设置查询条件
        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

//        查询结果
        List<Content> contents = contentMapper.selectByExample(contentExample);

        PageHelper.startPage(page,rows);

        PageInfo<Content> contentPageInfo = new PageInfo<>(contents);

        EasyUIResult easyUIResult = new EasyUIResult();
        easyUIResult.setRows(contents);
        easyUIResult.setTotal((int) contentPageInfo.getTotal());


        return easyUIResult;
    }

    @Override
    public E3Result deleteContent(List<String> ids) {

        for (String id:
             ids) {
            contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }

        return  E3Result.ok();
    }

    @Override
    public List<Content> getContentByCategory(Long categoryId) {
        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<Content> contents = contentMapper.selectByExample(contentExample);

        return contents;
    }

    @Override
//    写入数据
    public E3Result addContent(Content content) {
        contentMapper.insert(content);
        return E3Result.ok();
    }
}
