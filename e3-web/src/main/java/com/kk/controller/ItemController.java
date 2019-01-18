package com.kk.controller;


import com.kk.ItemService;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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





}
