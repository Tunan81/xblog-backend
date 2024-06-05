package team.ik.model.dto;

import lombok.Data;

@Data
public class QuestionCommentDTO {

    private Long questionId;

    private Long parentId;

    private String content;
}
