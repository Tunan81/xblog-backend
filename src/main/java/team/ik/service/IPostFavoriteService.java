package team.ik.service;

import com.mybatisflex.core.service.IService;
import team.ik.model.entity.PostFavorite;
import team.ik.model.entity.User;

/**
 * 帖子收藏表 服务层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface IPostFavoriteService extends IService<PostFavorite> {
    /**
     * 帖子收藏
     */
    int doPostFavour(long postId, User loginUser);


    /**
     * 帖子收藏（内部服务）
     */
    int doPostFavourInner(long userId, long postId);
}
