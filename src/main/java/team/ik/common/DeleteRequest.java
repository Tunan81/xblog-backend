package team.ik.common;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除请求
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long userId;

    @Serial
    private static final long serialVersionUID = 1L;
}