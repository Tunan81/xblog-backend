package team.ik.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import team.ik.common.HttpCodeEnum;
import team.ik.common.Result;
import team.ik.exception.BusinessException;
import team.ik.model.dto.postthumb.PostThumbAddRequest;
import team.ik.model.entity.User;
import team.ik.service.IPostThumbService;
import org.springframework.web.bind.annotation.RestController;
import team.ik.service.UserService;

import jakarta.annotation.Resource;

/**
 * 帖子点赞表 控制层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/post/thumb")
public class PostThumbController {
    @Resource
    private IPostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public Result<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
                                   HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return Result.success(result);
    }
}
