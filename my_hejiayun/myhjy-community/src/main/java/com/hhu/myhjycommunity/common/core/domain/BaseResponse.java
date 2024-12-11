package com.hhu.myhjycommunity.common.core.domain;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应结果描述
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 成功返回
     * @param data
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> success(E data){
        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(ResultCode.SUCCESS.getCode()));
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        response.setSuccess(true);
        return response;
    }
    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> fail(String message){

        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(ResultCode.ERROR.getCode()));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <E> BaseResponse<E> fail(String code, String message){

        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回
     */
    public static <E> BaseResponse<E> fail(String code, String msg,boolean success){
        BaseResponse<E> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(msg);
        response.setSuccess(success);
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
