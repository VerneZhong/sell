package com.zxb.order.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zxb.order.dto.OrderDTO;
import com.zxb.order.enums.ResultEnum;
import com.zxb.order.exception.OrderException;
import com.zxb.order.model.OrderDetail;
import com.zxb.order.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:13
 */
@Slf4j
public class OrderRequestToOrderDTO {

    public static OrderDTO convert(OrderRequest orderRequest) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderRequest.getName());
        orderDTO.setBuyerPhone(orderRequest.getPhone());
        orderDTO.setBuyerAddress(orderRequest.getAddress());
        orderDTO.setBuyerOpenid(orderRequest.getOpenid());

        List<OrderDetail> orderDetails;
        try {
            Gson gson = new Gson();
            orderDetails = gson.fromJson(orderRequest.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            log.error("[Json转换错误] message={}", orderRequest.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetails(orderDetails);
        return orderDTO;
    }
}
