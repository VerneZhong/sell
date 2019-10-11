package com.zxb.product.service;

import com.zxb.product.dto.CartDTO;
import com.zxb.product.enums.ProductStatusEnum;
import com.zxb.product.enums.ResultEnum;
import com.zxb.product.exception.ProductException;
import com.zxb.product.model.ProductInfo;
import com.zxb.product.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 13:52
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    /**
     * 查询上架的商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP .getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIds) {
        return productInfoRepository.findByProductIdIn(productIds);
    }

    /**
     * 扣库存
     * @param cartDTOS
     */
    @Override
    public void decreaseStock(List<CartDTO> cartDTOS) {
        List<String> productIds = cartDTOS.stream().map(CartDTO::getProductId).collect(Collectors.toList());

        List<ProductInfo> productInfos = productInfoRepository.findByProductIdIn(productIds);

        Map<String, ProductInfo> productInfoMap = productInfos.stream().collect(Collectors.toMap(k -> k.getProductId(), v -> v));

        for (CartDTO cartDTO : cartDTOS) {
            // 判断商品是否存在
            if (!productInfoMap.containsKey(cartDTO.getProductId())) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = productInfoMap.get(cartDTO.getProductId());

            // 判断库存是否足够
            int stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (stock < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }
}
