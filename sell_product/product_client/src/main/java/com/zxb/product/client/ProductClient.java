package com.zxb.product.client;

import com.zxb.common.product.domain.DecreaseStockInput;
import com.zxb.common.product.domain.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    /**
     * 获取商品信息
     * @param productIdList
     * @return
     */
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 扣库存接口
     * @param decreaseStockInputs
     */
    void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputs);

    class ProductClientFallback implements ProductClient {

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {

        }
    }

}
