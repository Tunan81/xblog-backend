package team.ik.model.dto;

import lombok.Data;

/**
 * @author tunan
 */
@Data
public class PostCommentDTO {

    private Long postId;

    private Long parentId;

    private String content;
}
