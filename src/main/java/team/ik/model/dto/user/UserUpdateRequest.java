package team.ik.model.dto.user;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新请求
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
@Data
public class UserUpdateRequest implements Serializable {


    private Long userId;

    private String userName;

    private Integer userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}