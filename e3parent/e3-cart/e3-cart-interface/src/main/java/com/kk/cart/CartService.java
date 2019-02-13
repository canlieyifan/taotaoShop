package com.kk.cart;

import com.kk.pojo.Item;
import com.kk.pojo.ext.ItemView;
import com.kk.utils.E3Result;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface CartService {

    Item getItemById(Long id);

    E3Result addCart(Long userId, Long itemId, Integer num);

    E3Result mergeCart(Long userId, List<Item> itemList);

    void deleteItem(Long userId, Long itemId);

    void updateItem(Long userId, Long itemId, Integer num);

    List<ItemView> getCartListById(Long id);

    void deleteCart(Long id);
}
