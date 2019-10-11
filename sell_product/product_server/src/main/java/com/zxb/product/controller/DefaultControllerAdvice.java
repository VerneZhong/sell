package com.zxb.product.controller;

import com.zxb.product.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 14:31
 */
@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler
    public ResultVO onException(Throwable throwable) {
        return ResultVO.error(throwable);
    }
}
