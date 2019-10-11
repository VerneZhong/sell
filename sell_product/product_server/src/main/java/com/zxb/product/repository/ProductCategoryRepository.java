package com.zxb.product.repository;

import com.zxb.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-09-25 11:20
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
}
