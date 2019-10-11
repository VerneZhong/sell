package com.zxb.product.vo;

import lombok.Data;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 10:57
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    private T data;

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setData(data);
        resultVO.setMessage("请求成功");
        return resultVO;
    }

    public static ResultVO error(Throwable throwable) {
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(500);
        resultVO.setData(throwable.getMessage());
        resultVO.setMessage("请求失败");
        return resultVO;
    }
}
