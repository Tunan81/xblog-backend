package team.ik.service;

import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import team.ik.model.dto.PostCommentDTO;
import team.ik.model.entity.PostComment;
import team.ik.utils.PageInfo;

import java.util.List;

/**
 * 帖子评论表 服务层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface IPostCommentService extends IService<PostComment> {

    /**
     * 分页查询评论列表
     *
     * @param pageNum    页码
     * @param pageSize   每页数量
     * @param postId     帖子id
     * @return void
     */
    List<PostComment> pageByQid(int pageNum, int pageSize, Long postId);

    /**
     * 分页查询回复
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param parentId 父评论id
     * @return void
     */
    PageInfo<PostComment> replyPage(int pageNum, int pageSize, Long parentId);

    PostComment mySave(PostCommentDTO commentDTO, HttpServletRequest request);
}
