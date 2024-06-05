package team.ik.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionAnswerVO {

    /**
     * 回答用户 id
     */
    private Long userId;

    /**
     * 题解标题
     */
    private String title;

    /**
     * 标签列表（json 数组）
     */
    private String tags;

    /**
     * 题解内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
