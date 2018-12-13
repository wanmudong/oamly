package top.wanmudong.oamly.modules.permission.service;

import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.Role;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public interface RoleService extends IService<Role> {
    MyPageInfo<Role> selectRoleList(PageQuery pageQuery);

    void addRole(Role role);

    /**
     * 验证角色是否存在
     * @param roleId
     */
    Role isExistRole(Integer roleId);

    /**
     * 删除id对应的角色
     * @param id
     */
    void delRole(Integer id);

    /**
     * 更新角色信息
     * @param role
     */
    void updateRole(Role role);
}
