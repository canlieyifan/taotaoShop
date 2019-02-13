package com.kk.order.service;

import com.kk.pojo.ext.OrderInfo;
import com.kk.utils.E3Result;

public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
