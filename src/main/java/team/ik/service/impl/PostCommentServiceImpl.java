package team.ik.service.impl;

import com.github.pagehelper.PageHelper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import team.ik.model.dto.PostCommentDTO;
import team.ik.model.entity.PostComment;
import team.ik.mapper.PostCommentMapper;
import team.ik.model.entity.PostComment;
import team.ik.model.entity.PostComment;
import team.ik.model.entity.User;
import team.ik.model.vo.PostCommentUserVO;
import team.ik.service.IPostCommentService;
import org.springframework.stereotype.Service;
import team.ik.service.UserService;
import team.ik.utils.CommentKeyUtil;
import team.ik.utils.PageInfo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子评论表 服务层实现。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Slf4j
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment> implements IPostCommentService {

    @Resource
    private PostCommentMapper postCommentMapper;

    @Resource
    private CommentKeyUtil commentKeyUtil;

    @Resource
    private UserService userService;


    @Override
    public List<PostComment> pageByQid(int pageNum, int pageSize, Long postId) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println("sss"+postId);
        List<PostComment> parentList = postCommentMapper.pageByQid(postId);
        log.warn("parentList: {}", parentList);
        //添加评论对应的回复
        for (PostComment postComment : parentList) {
            Long id = postComment.getId();
            // 合并redis中的点赞数量
            postComment.setLikes(postComment.getLikes() + getLikeCount(id));
            PageInfo<PostComment> replyPage = replyPage(1, 5, id);
            PostComment.Reply reply = new PostComment.Reply();
            BeanUtils.copyProperties(replyPage, reply);
            postComment.setReply(reply);
        }
        return parentList;
    }

    @Override
    public PageInfo<PostComment> replyPage(int pageNum, int pageSize, Long parentId) {
        PageHelper.startPage(pageNum, pageSize);
        // 通过parentId查询回复列表
        List<PostComment> replyList = postCommentMapper.selectByPid(parentId);
        for (PostComment reply : replyList) {
            Long id = reply.getId();
            // 合并redis中的点赞数量
            reply.setLikes(reply.getLikes() + getLikeCount(id));
        }
        return new PageInfo<>(replyList);
    }

    @Override
    public PostComment mySave(PostCommentDTO commentDTO, HttpServletRequest request) {
        PostComment postComment = new PostComment();
        User user = userService.getLoginUser(request);
        BeanUtils.copyProperties(commentDTO, postComment);
        postComment.setUid(user.getId());
        postComment.setLikes(0);
        postComment.setCreateTime(LocalDateTime.now());
        if (postCommentMapper.insert(postComment) < 1) {
            throw new RuntimeException();
        }
        PostCommentUserVO questionCommentUserVO = new PostCommentUserVO();
        questionCommentUserVO.setUsername(user.getUserName());
        questionCommentUserVO.setAvatar(user.getUserAvatar());
        postComment.setUser(questionCommentUserVO);
        return postComment;
    }

    /**
     * 获取redis中的评论点赞数量
     *
     * @param commentId 评论id
     * @return void
     */
    private int getLikeCount(Long commentId) {
        long count = commentKeyUtil.getLikeSize(commentId, true);
        long cancelCount = commentKeyUtil.getLikeSize(commentId, false);
        return (int) (count - cancelCount);
    }
}
