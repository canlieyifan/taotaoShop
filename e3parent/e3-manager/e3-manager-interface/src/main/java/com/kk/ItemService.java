package com.kk;


import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;

import java.util.List;

public interface ItemService {
    public Item selectItemBuyId(Long id);
    public EasyUIResult getItemList(Integer page,Integer rows);

}
