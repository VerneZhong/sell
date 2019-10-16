package com.zxb.order.repository;

import com.zxb.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:24
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrOrderId(String orderId);
}
