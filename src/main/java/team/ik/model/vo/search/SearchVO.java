package team.ik.model.vo.search;

import lombok.Data;
import team.ik.model.vo.UserVO;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 *
 */
@Data
public class SearchVO implements Serializable {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<?> dataList;

    private static final long serialVersionUID = 1L;

}
