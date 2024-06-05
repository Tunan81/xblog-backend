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
import team.ik.model.entity.PostThumb;
import team.ik.mapper.PostThumbMapper;
import team.ik.model.entity.User;
import team.ik.service.IPostService;
import team.ik.service.IPostThumbService;
import org.springframework.stereotype.Service;



/**
 * 帖子点赞表 服务层实现。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Service
public class PostThumbServiceImpl extends ServiceImpl<PostThumbMapper, PostThumb> implements IPostThumbService {

    @Resource
    private IPostService postService;

    /**
     * 点赞
     */
    @Override
    public int doPostThumb(long postId, User loginUser) {
        // 判断实体是否存在，根据类别获取实体
        Post post = postService.getById(postId);
        if (post == null) {
            throw new BusinessException(HttpCodeEnum.NOT_FOUND_ERROR);
        }
        // 是否已点赞
        long userId = loginUser.getId();
        // 每个用户串行点赞
        // 锁必须要包裹住事务方法
        IPostThumbService postThumbService = (IPostThumbService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return postThumbService.doPostThumbInner(userId, postId);
        }
    }

    /**
     * 封装了事务的方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doPostThumbInner(long userId, long postId) {
        PostThumb postThumb = new PostThumb();
        postThumb.setUserId(userId);
        postThumb.setPostId(postId);
        QueryWrapper thumbQueryWrapper = new QueryWrapper();
        PostThumb oldPostThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        // 已点赞
        if (oldPostThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // 点赞数 - 1
                result = UpdateChain.of(Post.class)
                        .eq("id", postId)
                        .gt("thumbNum", 0)
                        .setRaw(Post::getThumbNum, "thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(HttpCodeEnum.SYSTEM_ERROR);
            }
        } else {
            // 未点赞
            result = this.save(postThumb);
            if (result) {
                // 点赞数 + 1
                result = UpdateChain.of(Post.class)
                        .eq("id", postId)
                        .setRaw(Post::getThumbNum, "thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(HttpCodeEnum.SYSTEM_ERROR);
            }
        }
    }
}
