package team.ik.aop;

import cn.dev33.satoken.stp.StpInterface;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import team.ik.model.entity.User;
import team.ik.service.UserService;


import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private UserService userService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = userService.getById(Long.parseLong((String) loginId));
        List<String> list = new ArrayList<>();
        list.add(user.getUserRole());
        return list;
    }

}

