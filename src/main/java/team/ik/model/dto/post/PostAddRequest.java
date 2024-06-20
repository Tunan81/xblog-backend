package team.ik.model.dto.post;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 创建请求
 * @author tunan
 */
@Data
public class PostAddRequest implements Serializable {

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