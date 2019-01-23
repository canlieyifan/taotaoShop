package com.kk.contentService.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.contentService.ContentService;
import com.kk.mapper.ContentMapper;
import com.kk.pojo.Content;
import com.kk.pojo.ContentExample;
import com.kk.pojo.EasyUIResult;
import com.kk.redis.JedisClient;
import com.kk.utils.E3Result;
import com.kk.utils.JsonUtils;
import org.omg.CORBA.TCKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImp implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

//    自动注入属性 如果冲突 根据Name注入相应属性
    @Autowired
    @Qualifier("clientCluster")
    private JedisClient jedisClient;



    @Value("${CONTENT_LIST}")
    private String Content_List;




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
        String temp = ids.get(0);
        int i = Integer.parseInt(temp);

        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andIdEqualTo((long) i);

        List<Content> contents = contentMapper.selectByExample(contentExample);
        Long categoryId = contents.get(0).getCategoryId();



        for (String id:
             ids) {
            contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }


        jedisClient.hdel(Content_List,categoryId+"");

        return  E3Result.ok();
    }




//    根据CategoryId进行查询(加入缓存技术)
    @Override
    public List<Content> getContentByCategory(Long categoryId) {

        try{
            String value = jedisClient.hget(Content_List, categoryId + "");
            if(value!=null){
                return JsonUtils.jsonToList(value,Content.class);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<Content> contents = contentMapper.selectByExample(contentExample);

        try {
            jedisClient.hset(Content_List,categoryId+"",JsonUtils.objectToJson(contents));
        }catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }




    @Override
//    写入数据
    public E3Result addContent(Content content) {
        contentMapper.insert(content);
        jedisClient.hdel(Content_List,content.getCategoryId().toString());
        return E3Result.ok();
    }
}
