package team.ik.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import team.ik.model.dto.post.PostAdminQueryRequest;
import team.ik.model.dto.post.PostQueryRequest;
import team.ik.model.entity.Post;
import team.ik.model.vo.search.PostVO;



/**
 * 帖子表 服务层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface IPostService extends IService<Post> {

    /**
     * 校验
     */
    void validPost(Post post, boolean add);

    /**
     * 获取帖子封装
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    QueryWrapper getQueryWrapper(PostQueryRequest postQueryRequest);

    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);

    /**
     * 分页查询帖子
     */
    Page<PostVO> listPostVOByPage(PostQueryRequest postQueryRequest, HttpServletRequest request);

    //Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    Page<Post> listPostByPage(Page<Post> objectPage, QueryWrapper queryWrapper);

    QueryWrapper getAdminQueryWrapper(PostAdminQueryRequest postQueryRequest);
}
