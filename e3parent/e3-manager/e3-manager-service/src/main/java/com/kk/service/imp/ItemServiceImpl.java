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
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public Item selectItemBuyId(Long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
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
        PageHelper.startPage(page,rows);

        List<Item> items = itemMapper.selectByExample(new ItemExample());

        PageInfo<Item> itemPageInfo = new PageInfo<>(items);

        easyUIResult.setRows(items);
        easyUIResult.setTotal((int) itemPageInfo.getTotal());

        return easyUIResult;
    }
}
