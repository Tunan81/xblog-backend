package team.ik.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import team.ik.model.vo.PostCommentUserVO;
import team.ik.utils.PageInfo;

/**
 * 帖子评论表 实体类。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "post_comment")
public class PostComment implements Serializable {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long parentId;

    /**
     * 帖子 id
     */
    private Long postId;

    /**
     * 评论用户 id
     */
    @Column("user_id")
    private Long uid;

    /**
     * 评论内容
     */
    private String content;

    private String address;

    private Integer likes;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Column(ignore = true)
    private PostCommentUserVO user;

    @Column(ignore = true)
    private PostComment.Reply reply;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Reply extends PageInfo<PostComment> {
    }
}
