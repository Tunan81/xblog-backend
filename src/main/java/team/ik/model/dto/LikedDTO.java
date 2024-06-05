package team.ik.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author readpage
 */
@Data
public class LikedDTO {

    private Long commentId;

    /**
     * 用户id
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long uid;
}
