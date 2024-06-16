package team.ik.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import org.springframework.web.multipart.MultipartFile;
import team.ik.model.dto.user.UserQueryRequest;
import team.ik.model.entity.User;
import team.ik.model.vo.LoginUserVO;
import team.ik.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/Tunan81">图南</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     */
    User getLoginUserPermitNull(HttpServletRequest request);
//
//    /**
//     * 是否为管理员
//     */
//    //boolean isAdmin(HttpServletRequest request);
//
//    /**
//     * 是否为管理员
//     */
//    //boolean isAdmin(User user);

    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     */
    List<UserVO> getUserVO(List<User> userList);

    //boolean updatePassword(String oldPassword, String newPassword);

    //String uploadAvatar(MultipartFile file);

    /**
     * 获取查询条件
     */
    //QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);

//    /**
//     * 分页查询用户
//     *
//     * @param userQueryRequest 查询条件
//     */
//    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);
}
