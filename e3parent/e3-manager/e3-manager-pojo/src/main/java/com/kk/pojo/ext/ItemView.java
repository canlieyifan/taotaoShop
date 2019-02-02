package com.kk.pojo.ext;

import com.kk.pojo.Item;

import java.io.Serializable;


public class ItemView extends Item implements Serializable {


    public String[] getImages() {
        System.out.println("调用图片方法");
        String image2 = this.getImage();
        if (image2 != null && !"".equals(image2)) {
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }

    public ItemView() {

    }

    public ItemView(Item Item) {
        this.setBarcode(Item.getBarcode());
        this.setCid(Item.getCid());
        this.setCreated(Item.getCreated());
        this.setId(Item.getId());
        this.setImage(Item.getImage());
        this.setNum(Item.getNum());
        this.setPrice(Item.getPrice());
        this.setSellPoint(Item.getSellPoint());
        this.setStatus(Item.getStatus());
        this.setTitle(Item.getTitle());
        this.setUpdated(Item.getUpdated());
    }

}
