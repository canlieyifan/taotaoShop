package com.kk.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.ItemService;
import com.kk.mapper.ItemDescMapper;
import com.kk.mapper.ItemMapper;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import com.kk.pojo.ItemDesc;
import com.kk.pojo.ItemExample;
import com.kk.pojo.ext.ItemView;
import com.kk.redis.JedisClient;
import com.kk.utils.E3Result;
import com.kk.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${id.prefix}")
    private String prefix;
    @Value("${id.end}")
    private String end;


    @Value("${ITEM_CACHE_EXPIRE}")
    private String ITEM_CACHE_EXPIRE;

    @Override
    public Item selectItemBuyId(Long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
    }

    @Override
    public ItemDesc selectItemDescById(Long id) {
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        return itemDesc;
    }

    @Override
    public ItemView createItemView(Long id) {

        String itemId = prefix+id+end;


        System.out.println("itemId = " + itemId);

     //清除缓存注入

//        if(jedisClient.get(itemId)!=null&&!"".equals(jedisClient.get(itemId))){
//            System.out.println("缓存存在于内存");
//            JsonUtils.jsonToPojo( jedisClient.get(itemId),ItemView.class);
//        }



        Item item = itemMapper.selectByPrimaryKey(id);
        ItemView itemView = new ItemView(item);

//        jedisClient.set(itemId,JsonUtils.objectToJson(itemView));
//        jedisClient.expire(itemId,Integer.parseInt(ITEM_CACHE_EXPIRE));
//        System.out.println("缓存被加入" );
        return itemView;


    }

    @Override
    public E3Result saveItem(Item item, String desc) {


//      创建商品描述类 并完善信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDesc.setItemId(item.getId());

//       持久化数据
        itemMapper.insert(item);
        itemDescMapper.insert(itemDesc);

        return E3Result.ok();
    }

    @Override
    public EasyUIResult getItemList(Integer page, Integer rows) {
        EasyUIResult easyUIResult = new EasyUIResult();

//      设置分页信息
        PageHelper.startPage(page, rows);

        List<Item> items = itemMapper.selectByExample(new ItemExample());

        PageInfo<Item> itemPageInfo = new PageInfo<>(items);

        easyUIResult.setRows(items);
        easyUIResult.setTotal((int) itemPageInfo.getTotal());

        return easyUIResult;
    }
}
