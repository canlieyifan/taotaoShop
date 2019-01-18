package com.kk.controller;


import com.kk.ItemService;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import com.kk.utils.E3Result;
import com.kk.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.EditorKit;
import java.util.Date;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("selectTbItemById/{id}")
    public @ResponseBody
    Item selectTbItemById(@PathVariable Long id)
    {
        System.out.println("id = " + id);
        Item item = itemService.selectItemBuyId(id);
        System.out.println("tbItem = " + item);
        return item;
    }


    @RequestMapping("list")
    @ResponseBody
    public EasyUIResult getItemList(Integer page,Integer rows){
        EasyUIResult itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    @RequestMapping("save")
    @ResponseBody
    public E3Result saveItem(Item item,String desc){

//        完成商品列表的其他信息
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
//        自定义ID生成策略
        Long id = IDUtils.genItemId();
        item.setId(id);
        return itemService.saveItem(item, desc);
    }

}
