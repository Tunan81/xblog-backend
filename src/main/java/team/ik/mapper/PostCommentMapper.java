package team.ik.mapper;

import com.mybatisflex.core.BaseMapper;
import team.ik.model.entity.PostComment;

import java.util.List;

/**
 * 帖子评论表 映射层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface PostCommentMapper extends BaseMapper<PostComment> {

    /**
     * 根据题目id查询评论列表(不包含回复)
     *
     * @param postId 帖子id
     * @return 评论列表
     */
    List<PostComment> pageByQid(Long postId);

    List<PostComment> selectByPid(Long postId);
}
