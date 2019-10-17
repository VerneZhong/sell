package com.zxb.product.controller;

import com.zxb.product.common.domain.DecreaseStockInput;
import com.zxb.product.common.domain.ProductInfoOutput;
import com.zxb.product.dto.CartDTO;
import com.zxb.product.model.ProductCategory;
import com.zxb.product.model.ProductInfo;
import com.zxb.product.service.CategoryService;
import com.zxb.product.service.ProductService;
import com.zxb.product.vo.ProductInfoVO;
import com.zxb.product.vo.ProductVO;
import com.zxb.product.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 14:33
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    // 跨域方法一：该方法支持跨域访问
    @CrossOrigin(allowCredentials = "true")
    public ResultVO<List<ProductVO>> list() {
        
        List<ProductInfo> productInfos = productService.findUpAll();
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(productInfos.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList()));
        List<ProductVO> productVOs = categoryList.stream().map(productCategory -> {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOs = productInfos.stream()
                    .filter(productInfo -> productCategory.getCategoryType()
                            .equals(productInfo.getCategoryType()))
                    .map(productInfo -> {
                        ProductInfoVO productInfoVO = new ProductInfoVO();
                        BeanUtils.copyProperties(productInfo, productInfoVO);
                        return productInfoVO;
                    }).collect(Collectors.toList());
            productVO.setProductInfoVOS(productInfoVOs);
            return productVO;
        }).collect(Collectors.toList());
        return ResultVO.success(productVOs);
    }

    /**
     * 获取商品列表（给订单服务使用）
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputs) {
        productService.decreaseStock(decreaseStockInputs);
    }
}
