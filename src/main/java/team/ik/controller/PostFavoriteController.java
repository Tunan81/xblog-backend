package team.ik.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import team.ik.common.HttpCodeEnum;
import team.ik.common.Result;
import team.ik.exception.BusinessException;
import team.ik.model.dto.postfavour.PostFavourAddRequest;
import team.ik.model.entity.User;
import team.ik.service.IPostFavoriteService;
import org.springframework.web.bind.annotation.RestController;
import team.ik.service.UserService;

import jakarta.annotation.Resource;

/**
 * 帖子收藏表 控制层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/post/favorite")
public class PostFavoriteController {

    @Resource
    private IPostFavoriteService iPostFavoriteService;

    @Resource
    private UserService userService;

    /**
     * 收藏 / 取消收藏
     *
     * @return resultNum 收藏变化数
     */
    @PostMapping("/")
    public Result<Integer> doPostFavour(@RequestBody PostFavourAddRequest postFavourAddRequest,
                                        HttpServletRequest request) {
        if (postFavourAddRequest == null || postFavourAddRequest.getPostId() <= 0) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        // 登录才能操作
        final User loginUser = userService.getLoginUser(request);
        long postId = postFavourAddRequest.getPostId();
        int result = iPostFavoriteService.doPostFavour(postId, loginUser);
        return Result.success(result);
    }
}
