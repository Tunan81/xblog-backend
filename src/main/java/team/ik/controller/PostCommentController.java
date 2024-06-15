package team.ik.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import team.ik.common.Result;
import team.ik.model.dto.PostCommentDTO;
import team.ik.model.entity.PostComment;
import team.ik.service.IPostCommentService;

import java.util.List;


/**
 * 帖子评论表 控制层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@RestController
@RequestMapping("/post/comment")
public class PostCommentController {
    @Resource
    private IPostCommentService postCommentService;

    // todo 优化
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result<List<PostComment>> page(@PathVariable int pageNum, @PathVariable int pageSize, Long postId) {
        System.out.println(postId);
        return Result.success(postCommentService.pageByQid(pageNum, pageSize, postId));
    }

    // todo 优化
    @PostMapping("/save")
    public Result<?> save(@RequestBody PostCommentDTO commentDTO, HttpServletRequest request) {
        if (commentDTO.getContent() == null || commentDTO.getContent().isEmpty()) {
            return Result.fail("评论内容不能为空");
        }
        return Result.success(postCommentService.mySave(commentDTO, request));
    }
}
