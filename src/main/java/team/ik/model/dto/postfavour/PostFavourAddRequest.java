package team.ik.model.dto.postfavour;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子收藏 / 取消收藏请求
 * @author tunan
 */
@Data
public class PostFavourAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    @Serial
    private static final long serialVersionUID = 1L;
}