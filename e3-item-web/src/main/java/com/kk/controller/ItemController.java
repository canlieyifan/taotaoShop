package com.kk.controller;

import com.kk.ItemService;
import com.kk.pojo.ItemDesc;
import com.kk.pojo.ext.ItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("item/{itemId}")
    public String selectItemViewById(@PathVariable(value = "itemId")Long id, Model model){
        System.out.println("id = " + id);

        ItemView itemView = itemService.createItemView(id);
        ItemDesc itemDesc = itemService.selectItemDescById(id);

        model.addAttribute("item",itemView);
        model.addAttribute("itemDesc",itemDesc);

        return "item";
    }


}
