package team.ik.model.dto.user;

import com.mybatisflex.annotation.Column;
import team.ik.common.PageRequest;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户角色：user/admin/ban
     */
    private Integer userRole;

    /**
     * 性别
     */
    private Integer userGender;

    /**
     * 创建时间（开始）
     */
    private LocalDateTime startTime;

    /**
     * 创建时间（结束）
     */
    private LocalDateTime endTime;

    @Serial
    private static final long serialVersionUID = 1L;
}