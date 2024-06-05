package team.ik.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帖子点赞表 实体类。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "post_thumb")
public class PostThumb implements Serializable {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 题目 id
     */
    private Long postId;

    /**
     * 点赞用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
