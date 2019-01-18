package com.kk.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.ItemService;
import com.kk.mapper.ItemMapper;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.Item;
import com.kk.pojo.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public Item selectItemBuyId(Long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
    }

    @Override
    public EasyUIResult getItemList(Integer page, Integer rows) {
        EasyUIResult easyUIResult = new EasyUIResult();

//      设置分页信息
        PageHelper.startPage(page,rows);

        List<Item> items = itemMapper.selectByExample(new ItemExample());

        PageInfo<Item> itemPageInfo = new PageInfo<>(items);

        easyUIResult.setRows(items);
        easyUIResult.setTotal((int) itemPageInfo.getTotal());

        return easyUIResult;
    }
}
