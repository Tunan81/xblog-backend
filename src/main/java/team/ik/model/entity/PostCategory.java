package team.ik.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tunan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "post_category")
public class PostCategory {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private String name;
}
