package team.ik.controller;

import com.google.gson.Gson;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import team.ik.common.DeleteRequest;
import team.ik.common.HttpCodeEnum;
import team.ik.common.Result;
import team.ik.exception.BusinessException;
import team.ik.exception.ThrowUtils;
import team.ik.model.dto.post.PostAddRequest;
import team.ik.model.dto.post.PostAdminQueryRequest;
import team.ik.model.dto.post.PostQueryRequest;
import team.ik.model.dto.post.PostUpdateRequest;
import team.ik.model.entity.Post;
import team.ik.model.entity.PostCategory;
import team.ik.model.entity.User;
import team.ik.model.vo.search.PostVO;
import team.ik.service.IPostCategoryService;
import team.ik.service.IPostService;
import team.ik.service.UserService;

import java.util.List;

import static team.ik.model.entity.table.PostCategoryTableDef.POST_CATEGORY;
import static team.ik.model.entity.table.PostTableDef.POST;
import static team.ik.model.entity.table.UserTableDef.USER;

/**
 * 帖子表 控制层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private IPostService postService;

    @Resource
    private IPostCategoryService postCategoryService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查
    /**
     * 创建
     */
    @PostMapping("/add")
    public Result<Long> addPost(@RequestBody PostAddRequest postAddRequest, HttpServletRequest request) {
        if (postAddRequest == null) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        Post post = new Post();
        BeanUtils.copyProperties(postAddRequest, post);
        List<String> tags = postAddRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        postService.validPost(post, true);
        User loginUser = userService.getLoginUser(request);
        post.setUserId(loginUser.getId());
        post.setFavourNum(0);
        post.setThumbNum(0);
        boolean result = postService.save(post);
        ThrowUtils.throwIf(!result, HttpCodeEnum.OPERATION_ERROR);
        long newPostId = post.getId();
        return Result.success(newPostId);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Post oldPost = postService.getById(id);
        ThrowUtils.throwIf(oldPost == null, HttpCodeEnum.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(HttpCodeEnum.NO_AUTH_ERROR);
        }
        boolean b = postService.removeById(id);
        return Result.success(b);
    }

    /**
     * 更新（仅管理员）
     */
    @PostMapping("/update")
    public Result<Boolean> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        if (postUpdateRequest == null || postUpdateRequest.getId() <= 0) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        Post post = new Post();
        BeanUtils.copyProperties(postUpdateRequest, post);
        List<String> tags = postUpdateRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        // 参数校验
        postService.validPost(post, false);
        long id = postUpdateRequest.getId();
        // 判断是否存在
        Post oldPost = postService.getById(id);
        ThrowUtils.throwIf(oldPost == null, HttpCodeEnum.NOT_FOUND_ERROR);
        boolean result = postService.updateById(post);
        return Result.success(result);
    }

    /**
     * 根据 id 获取
     */
    @GetMapping("/get/vo/{id}")
    public Result<PostVO> getPostVOById(@PathVariable long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(HttpCodeEnum.PARAMS_ERROR);
        }
        Post post = postService.getById(id);
        if (post == null) {
            throw new BusinessException(HttpCodeEnum.NOT_FOUND_ERROR);
        }
        return Result.success(postService.getPostVO(post, request));
    }

    /**
     *
     * @param postQueryRequest 查询条件
     * @return 分页结果
     */
    @PostMapping("/list/page")
    public Result<Page<Post>> listPostByPage(@RequestBody PostAdminQueryRequest postQueryRequest) {
        long current = postQueryRequest.getPageNumber();
        long size = postQueryRequest.getPageSize();
        return  Result.success(postService.listPostByPage(new Page<>(current, size),
                postService.getAdminQueryWrapper(postQueryRequest)));
    }

    /**
     * 分页获取列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public Result<Page<PostVO>> listPostVOByPage(@RequestBody PostQueryRequest postQueryRequest,
                                                 HttpServletRequest request) {
        long current = postQueryRequest.getPageNumber();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, HttpCodeEnum.PARAMS_ERROR);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
        return Result.success(postVOPage);
    }

    /**
     *
     */
    @PostMapping("/list/{categoryName}")
    public Result<List<Post>> listPostVO(@PathVariable String categoryName){
//        PostCategory postCategory = postCategoryService.getOne(new QueryWrapper().eq("name", categoryName));
//        if (postCategory == null) {
//            return Result.fail("分类不存在");
//        }
        boolean isAll = "all".equals(categoryName);
        if (isAll){
            categoryName = "";
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select().from(POST)
                .leftJoin(POST_CATEGORY).on(POST.CATEGORY_ID.eq(POST_CATEGORY.ID))
                .leftJoin(USER).on(POST.USER_ID.eq(USER.ID))
                .like(POST_CATEGORY.NAME.getName(), categoryName);
        List<Post> list = postService.list(queryWrapper);
        return Result.success(list);
    }

    // endregion
}
