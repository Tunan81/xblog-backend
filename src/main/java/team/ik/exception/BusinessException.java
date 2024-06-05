package team.ik.exception;


import lombok.Getter;
import team.ik.common.HttpCodeEnum;

/**
 * 自定义异常类
 *
 * @author <a href="https://gitee.com/xia-haike">图南</a>
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMessage());
        this.code = httpCodeEnum.getCode();
    }

    public BusinessException(HttpCodeEnum httpCodeEnum, String message) {
        super(message);
        this.code = httpCodeEnum.getCode();
    }

}
