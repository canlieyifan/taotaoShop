package com.kk;


import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import com.kk.utils.E3Result;

import java.util.List;

public interface ItemService {
    public Item selectItemBuyId(Long id);
    public EasyUIResult getItemList(Integer page,Integer rows);
    public E3Result saveItem(Item item,String desc);
}
