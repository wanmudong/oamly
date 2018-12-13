package top.wanmudong.oamly.modules.permission.service;

import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public interface RolePermissionService extends IService<RolePermission> {

    MyPageInfo<RolePermission> getRolePermissions(PageQuery pageQuery);

    void addRolePermission(RolePermission rolePermission);

    /**
     * 删除id对应的角色权限对应关系
     * @param id
     */
    void delRolePermission(Integer id);

    /**
     * 更新角色权限对应关系
     * @param rolePermission
     */
    void updateRolePermission(RolePermission rolePermission);
}
