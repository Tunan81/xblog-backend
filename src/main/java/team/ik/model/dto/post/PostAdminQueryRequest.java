package team.ik.model.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import team.ik.common.PageRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author tunan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostAdminQueryRequest extends PageRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    @Serial
    private static final long serialVersionUID = 1L;
}
