package top.wanmudong.oamly.modules.permission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.common.utils.ContentContext.PermissionContext;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.entity.Role;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;
import top.wanmudong.oamly.modules.permission.mapper.PermissionMapper;
import top.wanmudong.oamly.modules.permission.mapper.RolePermissionMapper;
import top.wanmudong.oamly.modules.permission.service.PermissionService;
import top.wanmudong.oamly.modules.permission.service.RolePermissionService;
import top.wanmudong.oamly.modules.permission.service.RoleService;

import javax.annotation.Resource;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper,RolePermission> implements RolePermissionService {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;


    @Override
    public MyPageInfo<RolePermission> getRolePermissions(PageQuery pageQuery) {
        PageQuery.startPage(pageQuery);
        Integer state = PermissionContext.AVIALABLE;
        PageInfo<RolePermission> pageInfo = new PageInfo<>(baseMapper.getRolePermissions(state));
        return new MyPageInfo<>(pageInfo);
    }

    @Override
    public void addRolePermission(RolePermission rolePermission) {

        Integer roleId = Integer.valueOf(rolePermission.getRoleId());
        Integer permissionId = Integer.valueOf(rolePermission.getPermissionId());

        //验证权角色是否存在
//        Role roleExist = roleService.selectOne(new EntityWrapper<Role>().eq("id",roleId));
//
//        if (roleExist == null){
//            throw  new ContentNotExistException(OrderExceptionEnum.ROLE_NOT_FOUND_ERROR);
//        }
        roleService.isExistRole(roleId);

        //验证权限是否存在
//        Permission permissionExist = permissionService.selectOne(new EntityWrapper<Permission>().eq("id",permissionId));
//
//        if (permissionExist == null){
//            throw new ContentNotExistException(OrderExceptionEnum.PERMISSION_NOT_FOUND_ERROR);
//        }
        permissionService.isExistPermission(permissionId);

        RolePermission rolePermissionExist = selectOne(new EntityWrapper<RolePermission>()
                .eq("permission_id",rolePermission.getPermissionId())
                .eq("role_id",rolePermission.getRoleId())
        );

        if (rolePermissionExist != null){
            throw  new ContentAlreadyExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_ALREADY_EXIST_ERROR);
        }

        insert(rolePermission);
    }

    @Override
    public void delRolePermission(Integer id) {
        RolePermission rolePermission = selectById(id);



        if (rolePermission == null){
            throw new ContentNotExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_NOT_FOUND_ERROR);
        }

        deleteById(id);

    }

    @Override
    public void updateRolePermission(RolePermission rolePermission) {
        Integer id = rolePermission.getId();

        RolePermission rolePermissionExist = selectById(id);

        if (rolePermissionExist == null){
            throw  new ContentNotExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_NOT_FOUND_ERROR);
        }

        RolePermission rolePermissionAllExist = selectOne(new EntityWrapper<RolePermission>()
                .eq("permission_id",rolePermission.getPermissionId())
                .eq("role_id",rolePermission.getRoleId())
        );

        if (rolePermissionAllExist != null){
            throw  new ContentAlreadyExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_ALREADY_EXIST_ERROR);
        }

        updateById(rolePermission);
    }
}
