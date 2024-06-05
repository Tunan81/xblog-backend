package team.ik.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.ik.utils.redis.RedisUtil;
import team.ik.model.dto.LikedDTO;

import java.util.List;
import java.util.Set;

/**
 * @author tunan
 * @since 2024-02-29
 */
@Component
public class CommentKeyUtil {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户评论点赞： 根据文章id获取用户id
     */
    public final static String COMMENT_LIKE = "comment-like:uid:";

    /**
     * 用户取消评论点赞: 根据文章id获取用户id
     */
    public final static String COMMENT_CANCEL_LIKE = "comment-cancel-like:uid:";

    /**
     * 添加评论点赞或取消点赞key
     *
     * @param state 是否点赞key
     * @return boolean
     */
    public boolean setLike(LikedDTO likedDTO, boolean state) {
        return redisUtil.sSet(getLikeKey(likedDTO.getCommentId(), state), likedDTO.getUid()) > 0;
    }

    /**
     * 删除评论点赞或取消点赞key
     *
     * @param state 是否点赞key
     * @return boolean
     */
    public boolean remLike(LikedDTO likedDTO, boolean state) {
        return redisUtil.sRemove(getLikeKey(likedDTO.getCommentId(), state), likedDTO.getUid()) > 0;
    }

    /**
     * 判断是否点赞或取消点赞
     *
     * @return boolean
     */
    public boolean isLike(LikedDTO likedDTO, boolean state) {
        return redisUtil.sHasKey(getLikeKey(likedDTO.getCommentId(), state), likedDTO.getUid());
    }

    /**
     * 根据评论id获取点赞或取消点赞uid列表
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     */
    public Set<Integer> getUidList(Long commentId, boolean state) {
        return redisUtil.sGet(getLikeKey(commentId, state));
    }

    /**
     * 获取点赞或取消点赞所有key
     *
     * @return java.util.List<java.lang.String>
     */
    public List<String> getLikeList(boolean state) {
        String key = state ? COMMENT_LIKE : COMMENT_CANCEL_LIKE;
        return redisUtil.scan(key + "*");
    }

    /**
     * 获取点赞或取消点赞数量
     *
     * @return int
     */
    public int getLikeSize(Long commentId, boolean state) {
        return (int) redisUtil.sSize(getLikeKey(commentId, state));
    }

    /**
     * 根据评论id和状态获取对应的key
     *
     * @return java.lang.String
     */
    private String getLikeKey(Long commentId, boolean state) {
        return state ? COMMENT_LIKE + commentId : COMMENT_CANCEL_LIKE + commentId;
    }

}
