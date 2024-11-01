package com.hhu.myhjycommunity.web.test;

import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.exception.BaseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/queryUserById")
    public BaseResponse<User> queryUserById(String userId){
        if(userId != null){
            return BaseResponse.success(new User(userId,"spike"));
        }else{
            return BaseResponse.fail("查询用户信息失败！");
        }
    }
    @RequestMapping("/queryUserByIdInValidated")
    public BaseResponse queryUserByIdInValidated(@Validated User user, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()){
            return BaseResponse.fail(fieldErrors.get(0).getDefaultMessage());
        }
        return BaseResponse.success("查询成功");
    }
    @RequestMapping("/queryUserExceptionTest1")
    public BaseResponse queryUserExceptionTest1(User user){
        throw new BaseException("501","异常测试1");
    }
}
