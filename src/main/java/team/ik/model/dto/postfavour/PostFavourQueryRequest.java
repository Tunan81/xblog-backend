package team.ik.model.dto.postfavour;

import lombok.Data;
import lombok.EqualsAndHashCode;
import team.ik.common.PageRequest;
import team.ik.model.dto.post.PostQueryRequest;

import java.io.Serializable;

/**
 * 帖子收藏查询请求
 *
 * @author tunan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}