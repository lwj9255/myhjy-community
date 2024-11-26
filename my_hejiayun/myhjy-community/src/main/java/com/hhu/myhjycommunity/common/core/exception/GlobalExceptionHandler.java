package com.hhu.myhjycommunity.common.core.exception;

import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //@RestControllerAdvice是一个应用于Controller层的切面注解，
// 它一般配合@ExceptionHandler注解一起使用，作为项目的全局异常处理
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    // 我们有想要拦截的异常类型，比如想拦截BaseException类型，
    // 就新增一个方法，使用@ExceptionHandler注解修饰，
    // 并指定你想处理的异常类型，接着在方法内编写对该异常的操作逻辑，就完成了对该异常的全局处理
    @ResponseBody
    public BaseResponse baseExceptionHandler(BaseException e){
        return BaseResponse.fail(e.getDefaultMessage());
    }

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public BaseResponse businessException(CustomException e){
        return BaseResponse.fail(e.getCode()+"",e.getMsg(),e.isSuccess());
    }
}
