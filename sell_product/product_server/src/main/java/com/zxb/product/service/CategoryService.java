package com.zxb.product.service;

import com.zxb.product.model.ProductCategory;

import java.util.List;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-09-25 12:36
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
}
