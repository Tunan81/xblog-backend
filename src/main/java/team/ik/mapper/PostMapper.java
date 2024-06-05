package team.ik.mapper;

import com.mybatisflex.core.BaseMapper;
import team.ik.model.entity.Post;

import java.util.Date;
import java.util.List;

/**
 * 帖子表 映射层。
 *
 * @author TuNan
 * @since 2024-02-17
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Post> listPostWithDelete(Date minUpdateTime);
}
