package top.wanmudong.oamly.modules.permission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.common.utils.ContentContext.PermissionContext;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.MemberRole;
import top.wanmudong.oamly.modules.permission.entity.Role;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;
import top.wanmudong.oamly.modules.permission.mapper.SRoleMapper;
import top.wanmudong.oamly.modules.permission.service.MemberRoleService;
import top.wanmudong.oamly.modules.permission.service.RolePermissionService;
import top.wanmudong.oamly.modules.permission.service.RoleService;
import top.wanmudong.redis.annotation.Lock;

import javax.annotation.Resource;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<SRoleMapper,Role> implements RoleService {

    @Resource
    private MemberRoleService memberRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public MyPageInfo<Role> selectRoleList(PageQuery pageQuery) {
        PageQuery.startPage(pageQuery);
        Integer state = PermissionContext.AVIALABLE;
        PageInfo<Role> pageInfo = new PageInfo<>(baseMapper.selectRoleList(state));
        return new MyPageInfo<>(pageInfo);
    }

    @Override
    @Transactional
    @Lock(lockKey = "addRole", expireTime = 5000, timeout = 2000)
    public void addRole(Role role) {
            Role roleExist = selectOne(new EntityWrapper<Role>().eq("name",role.getName()));

            if (roleExist != null){
                throw  new ContentAlreadyExistException(OrderExceptionEnum.ROLE_ALREADY_EXIST_ERROR);
            }

            insert(role);

    }

    @Override
    public Role isExistRole(Integer roleId) {
        Role roleExist = selectOne(new EntityWrapper<Role>().eq("id",roleId));

        if (roleExist == null){
            throw  new ContentNotExistException(OrderExceptionEnum.ROLE_NOT_FOUND_ERROR);
        }
        return roleExist;
    }

    @Override
    @Transactional
    @Lock(lockKey = "delRole",expireTime = 5000,timeout = 2000)
    public void delRole(Integer id) {
        isExistRole(id);

        memberRoleService.delete(new EntityWrapper<MemberRole>().eq("role_id",id));
        rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("role_id",id));

        deleteById(id);
    }

    @Override
    public void updateRole(Role role) {
        isExistRole(role.getId());

        updateById(role);
    }
}
