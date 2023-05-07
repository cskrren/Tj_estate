package com.rkr.service.security;

import org.springframework.stereotype.Service;

/**
 * @Package com.rkr.service.security
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description PermissionService:权限服务
 */
@Service("ps")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 角色分隔符
     */
    private static final String ROLE_DELIMETER = ",";

    /**
     * 权限分隔符
     */
    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 验证用户是否具备某权限
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        return true;
//        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
//        List<String> permissions = loginUser.getPermissions();
//        if(permission == null || permission.length() == 0){
//            return false;
//        }
//        return permissions.contains(permission);
    }
}
