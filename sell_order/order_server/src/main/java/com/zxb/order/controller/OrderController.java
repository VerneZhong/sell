package com.zxb.order.controller;

import com.zxb.order.converter.OrderRequestToOrderDTO;
import com.zxb.order.dto.OrderDTO;
import com.zxb.order.enums.ResultEnum;
import com.zxb.order.exception.OrderException;
import com.zxb.order.request.OrderRequest;
import com.zxb.order.service.OrderService;
import com.zxb.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-26 10:29
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderRequest orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数错误，orderRequest={}", orderRequest);
            throw new OrderException(bindingResult.getFieldError().getDefaultMessage(), ResultEnum.PARAM_ERROR.getCode());
        }
        OrderDTO convert = OrderRequestToOrderDTO.convert(orderRequest);
        if (CollectionUtils.isEmpty(convert.getOrderDetails())) {
            log.error("[创建订单] 购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        OrderDTO orderDTO = orderService.create(convert);
        return ResultVO.success(Maps.newHashMap("orderId", orderDTO.getOrderId()));
    }

    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId") String orderId) {
        return ResultVO.success(orderService.finish(orderId));
    }

}
