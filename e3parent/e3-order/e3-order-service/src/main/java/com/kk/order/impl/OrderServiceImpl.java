package com.kk.order.impl;

import com.kk.mapper.OrderItemMapper;
import com.kk.mapper.OrderMapper;
import com.kk.mapper.OrderShippingMapper;
import com.kk.order.service.OrderService;
import com.kk.pojo.OrderItem;
import com.kk.pojo.OrderShipping;
import com.kk.pojo.ext.OrderInfo;
import com.kk.redis.JedisClient;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_STAT_ID}")
    private String ORDER_STAT_ID;
    @Value("${ORDER_ITEM_ID_GEN")
    private String ORDER_ITEM_ID;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        if(!jedisClient.exists(ORDER_GEN_KEY)){
            jedisClient.set(ORDER_GEN_KEY,ORDER_STAT_ID);
        }

        Long orderId = jedisClient.incr(ORDER_GEN_KEY);

        //补全orderInfo的属性
        orderInfo.setOrderId(orderId.toString());
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderInfo);


        List<OrderItem> orderItems = orderInfo.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Long itemId = jedisClient.incr(ORDER_ITEM_ID);
            orderItem.setId(itemId.toString());
            orderItem.setOrderId(orderId.toString());
            orderItemMapper.insert(orderItem);
        }


        OrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId.toString());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        return E3Result.ok(orderId);
    }
}
