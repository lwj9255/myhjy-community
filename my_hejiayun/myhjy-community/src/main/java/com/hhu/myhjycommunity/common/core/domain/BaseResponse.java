package com.hhu.myhjycommunity.common.core.domain;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应结果描述
     */
    private String message;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 成功返回
     * @param data
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> success(E data){
        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }
    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> fail(String message){

        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(ResultCode.ERROR.getCode());
        response.setMessage(message);

        return response;
    }

    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> fail(String code, String message){

        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);

        return response;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
