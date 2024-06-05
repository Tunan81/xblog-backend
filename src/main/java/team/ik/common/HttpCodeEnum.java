package team.ik.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tunan
 * @since 2023/6/20 14:55
 * @version 1.0
 * 常用枚举
 */
@Getter
@AllArgsConstructor
public enum HttpCodeEnum {
    SUCCESS(0, "操作成功"),

    PARAMS_ERROR(40000, "请求参数错误"),

    NOT_LOGIN_ERROR(40100, "未登录"),

    NO_AUTH_ERROR(40101, "无权限"),

    NOT_FOUND_ERROR(40400, "请求数据不存在"),

    FORBIDDEN_ERROR(40300, "禁止访问"),

    SYSTEM_ERROR(50000, "系统内部异常"),

    OPERATION_ERROR(50001, "操作失败"),

    API_REQUEST_ERROR(50002, "接口请求异常"),

    SANDBOX_ERROR(50003, "沙箱异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 返回信息
     */
    private final String message;

}
