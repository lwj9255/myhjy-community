package com.hhu.myhjycommunity.common.constant;

/**
 * 返回状态码
 */
public class HttpStatus {
    // 操作成功
    public static final int SUCCESS = 200;
    // 对象创建成功
    public static final int CREATED = 201;
    // 请求已被接受
    public static final int ACCEPTED = 202;
    // 操作执行成功，但没返回数据
    public static final int NP_CONTENT = 204;
    // 资源已被移除
    public static final int MOVE_PERM = 301;
    // 重定向
    public static final int SEE_OTHER = 303;
    // 资源没有被修改
    public static final int NOT_MODIFIED = 304;
    // 参数列表错误（缺少，或格式不匹配）
    public static final int BAD_REQUEST = 400;
    // 未授权
    public static final int UNAUTHORIZED = 401;
    // 访问受限，授权过期
    public static final int FORBIDDED = 403;
    // 资源、服务未找到
    public static final int NOT_FOUND = 404;
    // 不允许的HTTP方法
    public static final int BAD_METHOD = 405;
    // 资源冲突/资源被锁
    public static final int CONFLICT = 409;
    // 不支持的数据
    public static final int UNSUPPORT_TYPE = 415;
    // 系统内部错误
    public static final int ERROR = 500;
    // 接口未实现
    public static final int NOT_IMPLEMENTED = 501;

}
