package com.kk.cart.controller;

import com.kk.cart.CartService;
import com.kk.pojo.Item;
import com.kk.pojo.User;
import com.kk.sso.UserService;
import com.kk.utils.CookieUtils;
import com.kk.utils.E3Result;
import com.kk.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {

    @Value("${COOKIE_CART}")
    private String cookie;
    @Autowired
    private CartService cartService;



    @RequestMapping("cart")
    public String listCart(HttpServletRequest request, Model model,HttpServletResponse response){

        if(request.getAttribute("user")!=null){
            User user = (User) request.getAttribute("user");
            List<Item> cartFormCookie = getCartFormCookie(request);
            E3Result e3Result = cartService.mergeCart(user.getId(), cartFormCookie);
            model.addAttribute("cartList",e3Result.getData());
            CookieUtils.deleteCookie(request,response,cookie);
            return "cart";
        }

        model.addAttribute("cartList",getCartFormCookie(request));
        return "cart";
    }




    @RequestMapping("update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCart(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        if(request.getAttribute("user")!=null) {
            User user = (User) request.getAttribute("user");
            cartService.updateItem(user.getId(),itemId,num);
        }else{
            List<Item> items = getCartFormCookie(request);
            for (Item item : items) {
                if(item.getId()==itemId.longValue()){
                    item.setNum(num);
                    break;
                }
            }
            CookieUtils.setCookie(request,response,cookie,JsonUtils.objectToJson(items),true);
        }
        return E3Result.ok();
    }


    @RequestMapping("delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        if(request.getAttribute("user")!=null) {
            User user = (User) request.getAttribute("user");
            cartService.deleteItem(user.getId(),itemId);
        }else{
            List<Item> items = getCartFormCookie(request);
            for (Item item : items) {
                if(item.getId()==itemId.longValue()){
                    items.remove(item);
                    break;
                }
            }
            CookieUtils.setCookie(request,response,cookie,JsonUtils.objectToJson(items),true);
        }
        return "redirect:/cart/cart.html";
    }




    @RequestMapping("add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){

//        有用户信息存入redis 无则写入cookie

//        1.取出cookie中的值
//        2.将cookie中存在的值与redis中的数据相加
//        3.不存在的数查询后在加入的redis中

        if(request.getAttribute("user")!=null){
            User user = (User) request.getAttribute("user");
            E3Result e3Result = cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }

        List<Item> cartFormCookie = getCartFormCookie(request);
//      查看列表中是否有相应的id

//      标志位 true表示购物车加入的商品id已存在
        boolean isExist = false;
        for (Item item : cartFormCookie) {
            if(item.getId()==itemId.longValue()){
                item.setNum(num+item.getNum());
                isExist = true;
                break;
            }
        }

        if(!isExist){
            Item item = cartService.getItemById(itemId);
            item.setNum(num);
            item.setImage(item.getImage().split(",")[0]);
            cartFormCookie.add(item);
        }

        CookieUtils.setCookie(request,response,cookie,JsonUtils.objectToJson(cartFormCookie),true);
        return "cartSuccess";
    }




















    private List<Item> getCartFormCookie(HttpServletRequest request){
        String cartList = CookieUtils.getCookieValue(request, cookie, true);
        if(StringUtils.isBlank(cartList)){
            return new ArrayList<Item>();
        }
        return JsonUtils.jsonToList(cartList,Item.class);
    }


}
