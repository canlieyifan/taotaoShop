package com.kk.cart;

import com.kk.mapper.ItemMapper;
import com.kk.pojo.Item;
import com.kk.pojo.ext.ItemView;
import com.kk.redis.JedisClient;
import com.kk.utils.E3Result;
import com.kk.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("cart:")
    private String cartPre;


    //    添加购物车
    @Override
    public E3Result addCart(Long userId, Long itemId, Integer num) {

        Boolean isExist = jedisClient.hexists(cartPre + userId, itemId.toString());

        if (isExist) {
            String json = jedisClient.hget(cartPre + userId, itemId.toString());
            Item item = JsonUtils.jsonToPojo(json, Item.class);
            item.setNum(item.getNum() + num);
            jedisClient.hset(cartPre + userId, itemId.toString(), JsonUtils.objectToJson(item));
            return E3Result.ok();
        }

        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setNum(num);
        item.setImage(item.getImage().split(",")[0]);

        jedisClient.hset(cartPre + userId, itemId.toString(), JsonUtils.objectToJson(item));
        return E3Result.ok();
    }


    @Override
    public void updateItem(Long userId, Long itemId, Integer num) {
        String json = jedisClient.hget(cartPre + userId, itemId.toString());
        Item item = JsonUtils.jsonToPojo(json, Item.class);
        item.setNum(num);
        jedisClient.hset(cartPre + userId, itemId.toString(),JsonUtils.objectToJson(item));
    }

    @Override
    public void deleteCart(Long id) {
         jedisClient.del(id.toString());
    }

    @Override
    public List<ItemView> getCartListById(Long id) {
        List<String> hvals = jedisClient.hvals(cartPre + id);

        ArrayList<ItemView> items = new ArrayList<>();

        for (String hval : hvals) {
            Item item = JsonUtils.jsonToPojo(hval, Item.class);
            ItemView itemView = new ItemView(item);
            items.add(itemView);
        }
        return items;
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        jedisClient.hdel(cartPre+userId,itemId.toString());
    }

    //    合并购物车
    @Override
    public E3Result mergeCart(Long userId, List<Item> itemList) {


        for (Item item : itemList) {
//          根据id取值 是否为null
            String json = jedisClient.hget(cartPre + userId, item.getId().toString());
            if (StringUtils.isNotBlank(json)) {
                Item temp = JsonUtils.jsonToPojo(json, Item.class);
                item.setNum(temp.getNum() + item.getNum());
            }
            jedisClient.hset(cartPre + userId, item.getId().toString(), JsonUtils.objectToJson(item));
        }

        List<String> hvals = jedisClient.hvals(cartPre + userId);


        ArrayList<Item> items = new ArrayList<>();

        for (String hval : hvals) {
            Item item = JsonUtils.jsonToPojo(hval, Item.class);
            items.add(item);
        }
        return E3Result.ok(items);
    }

    @Override
    public Item getItemById(Long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
    }


}
