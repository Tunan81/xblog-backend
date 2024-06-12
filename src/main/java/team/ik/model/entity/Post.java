package team.ik.model.entity;

import com.mybatisflex.annotation.Column;
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
 * 帖子表 实体类。
 *
 * @author TuNan
 * @since 2024-02-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "post")
public class Post implements Serializable {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    private Long categoryId;

    @Column(ignore = true)
    private String categoryName;

    /**
     * 标签列表（json 数组）
     */
    private String tags;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 点赞数
     */
    @Column(value = "thumbNum")
    private Integer thumbNum;

    /**
     * 收藏数
     */
    @Column(value = "favourNum")
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @Column(value = "isDelete")
    private boolean isDelete;
}
