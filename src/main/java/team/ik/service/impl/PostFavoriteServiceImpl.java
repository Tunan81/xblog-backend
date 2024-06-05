package team.ik.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.transaction.annotation.Transactional;
import team.ik.common.HttpCodeEnum;
import team.ik.exception.BusinessException;
import team.ik.model.entity.Post;
import team.ik.model.entity.PostFavorite;
import team.ik.mapper.PostFavoriteMapper;
import team.ik.model.entity.User;
import team.ik.service.IPostFavoriteService;
import org.springframework.stereotype.Service;
import team.ik.service.IPostService;



/**
 * 帖子收藏表 服务层实现。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Service
public class PostFavoriteServiceImpl extends ServiceImpl<PostFavoriteMapper, PostFavorite> implements IPostFavoriteService {
    @Resource
    private IPostService postService;

    /**
     * 帖子收藏
     *
     */
    @Override
    public int doPostFavour(long postId, User loginUser) {
        // 判断是否存在
        Post post = postService.getById(postId);
        if (post == null) {
            throw new BusinessException(HttpCodeEnum.NOT_FOUND_ERROR);
        }
        // 是否已帖子收藏
        long userId = loginUser.getId();
        // 每个用户串行帖子收藏
        // 锁必须要包裹住事务方法
        IPostFavoriteService postFavourService = (IPostFavoriteService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return postFavourService.doPostFavourInner(userId, postId);
        }
    }

    /**
     * 封装了事务的方法
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doPostFavourInner(long userId, long postId) {
        PostFavorite postFavour = new PostFavorite();
        postFavour.setUserId(userId);
        postFavour.setPostId(postId);
        QueryWrapper postFavourQueryWrapper = new QueryWrapper();
        PostFavorite oldPostFavour = this.getOne(postFavourQueryWrapper);
        boolean result;
        // 已收藏
        if (oldPostFavour != null) {
            result = this.remove(postFavourQueryWrapper);
            if (result) {
                // 帖子收藏数 - 1
                result = UpdateChain.of(Post.class)
                        .eq("id", postId)
                        .gt("favourNum", 0)
                        .setRaw(Post::getFavourNum, "favourNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(HttpCodeEnum.SYSTEM_ERROR);
            }
        } else {
            // 未帖子收藏
            result = this.save(postFavour);
            if (result) {
                // 帖子收藏数 + 1
                result = UpdateChain.of(Post.class)
                        .eq("id", postId)
                        .setRaw(Post::getFavourNum, "favourNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(HttpCodeEnum.SYSTEM_ERROR);
            }
        }
    }
}
