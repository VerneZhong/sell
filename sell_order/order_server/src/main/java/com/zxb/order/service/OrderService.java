package com.zxb.order.service;

import com.zxb.order.dto.OrderDTO;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:27
 */
public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);

    OrderDTO finish(String orderId);
}
