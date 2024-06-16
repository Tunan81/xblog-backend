package team.ik.model.dto.user;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户创建请求
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private Integer userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}