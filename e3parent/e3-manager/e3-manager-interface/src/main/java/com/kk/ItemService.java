package com.kk;


import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import com.kk.utils.E3Result;

import java.util.List;

public interface ItemService {
     Item selectItemBuyId(Long id);
     EasyUIResult getItemList(Integer page,Integer rows);
     E3Result saveItem(Item item,String desc);
}
