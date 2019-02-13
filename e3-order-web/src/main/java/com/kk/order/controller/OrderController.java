package com.kk.order.controller;

import com.kk.cart.CartService;
import com.kk.order.service.OrderService;
import com.kk.pojo.Item;
import com.kk.pojo.OrderItem;
import com.kk.pojo.User;
import com.kk.pojo.ext.ItemView;
import com.kk.pojo.ext.OrderInfo;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;


    @RequestMapping("order-cart")
    public String addOrder(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        List<ItemView> items = cartService.getCartListById(user.getId());
        request.setAttribute("cartList", items);
        return "order-cart";
    }


    @RequestMapping("create")
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        E3Result e3Result = orderService.createOrder(orderInfo);

        cartService.deleteCart(user.getId());

        request.setAttribute("payment",orderInfo.getPayment());

        request.setAttribute("orderId",orderInfo.getOrderId());

        return "success";
    }








}
