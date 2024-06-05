package team.ik.exception;

import team.ik.common.HttpCodeEnum;

/**
 * ThrowUtils is a utility class that provides methods for throwing exceptions based on certain conditions.
 * It includes methods for throwing a RuntimeException, a BusinessException with an ErrorCode,
 * and a BusinessException with an ErrorCode and a custom message.
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
public class ThrowUtils {

    /**
     * Throws a RuntimeException if the specified condition is true.
     *
     * @param condition The condition to check.
     * @param runtimeException The RuntimeException to throw if the condition is true.
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * Throws a BusinessException with the specified ErrorCode if the specified condition is true.
     *
     * @param condition The condition to check.
     */
    public static void throwIf(boolean condition, HttpCodeEnum httpCodeEnum) {
        throwIf(condition, new BusinessException(httpCodeEnum));
    }

    /**
     * Throws a BusinessException with the specified ErrorCode and custom message if the specified condition is true.
     *
     * @param condition The condition to check.
     * @param message The custom message to use for the BusinessException.
     */
    public static void throwIf(boolean condition, HttpCodeEnum httpCodeEnum, String message) {
        throwIf(condition, new BusinessException(httpCodeEnum, message));
    }
}