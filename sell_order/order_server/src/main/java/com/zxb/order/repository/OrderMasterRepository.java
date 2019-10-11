package com.zxb.order.repository;

import com.zxb.order.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:25
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
