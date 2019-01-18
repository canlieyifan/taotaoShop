package com.kk.service.imp;


import com.kk.ItemCatService;
import com.kk.mapper.ItemCatMapper;
import com.kk.pojo.EasyUIResult;
import com.kk.pojo.EasyUITreeNode;
import com.kk.pojo.ItemCat;
import com.kk.pojo.ItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long id) {
        ArrayList<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        ItemCatExample itemCatExample = new ItemCatExample();
        ItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<ItemCat> itemCats = itemCatMapper.selectByExample(itemCatExample);
        for (ItemCat itemCat:itemCats){
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(itemCat.getId());
            easyUITreeNode.setState(itemCat.getIsParent()?"closed":"open");
            easyUITreeNode.setText(itemCat.getName());
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
