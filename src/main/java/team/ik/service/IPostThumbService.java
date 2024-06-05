package team.ik.service;

import com.mybatisflex.core.service.IService;
import team.ik.model.entity.PostThumb;
import team.ik.model.entity.User;

/**
 * 帖子点赞表 服务层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface IPostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     */
    int doPostThumbInner(long userId, long postId);
}
