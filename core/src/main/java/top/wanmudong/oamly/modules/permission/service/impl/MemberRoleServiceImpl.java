package top.wanmudong.oamly.modules.permission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.common.utils.ContentContext.PermissionContext;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.permission.entity.MemberRole;


import top.wanmudong.oamly.modules.permission.entity.Role;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;
import top.wanmudong.oamly.modules.permission.mapper.MemberRoleMapper;
import top.wanmudong.oamly.modules.permission.service.MemberRoleService;
import top.wanmudong.oamly.modules.permission.service.PermissionService;
import top.wanmudong.oamly.modules.permission.service.RoleService;
import top.wanmudong.oamly.modules.user.service.UserService;

import javax.annotation.Resource;


/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Service
public class MemberRoleServiceImpl extends ServiceImpl<MemberRoleMapper,MemberRole> implements MemberRoleService {

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Override
    public MyPageInfo<MemberRole> getMemberRoles(PageQuery pageQuery) {
        PageQuery.startPage(pageQuery);
        Integer state = PermissionContext.AVIALABLE;
        PageInfo<MemberRole> pageInfo = new PageInfo<>(baseMapper.getRolePermissions(state));
        return new MyPageInfo<>(pageInfo);
    }

    @Override
    public void addMemberRole(MemberRole memberRole) {

        Integer roleId = Integer.valueOf(memberRole.getRoleId());
        Integer stuid = Integer.valueOf(memberRole.getStuid());

        //判断角色与成员是否存在
        roleService.isExistRole(roleId);
        userService.isExistMember(stuid);

        MemberRole memberRoleExist = selectOne(new EntityWrapper<MemberRole>()
                .eq("stuid",stuid)
             //   .eq("role_id",memberRole.getRoleId())
        );

        //判断角色与成员的对应关系是否已经存在
        if (memberRoleExist != null){
            throw  new ContentAlreadyExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_ALREADY_EXIST_ERROR);
        }

        insert(memberRole);
    }

    @Override
    public void delMemberRole(MemberRole memberRole) {

        Integer id = memberRole.getId();

        MemberRole memberRoleExist = selectById(id);

        //判断成员角色对应关系是否存在
        if (memberRoleExist == null){
            throw new ContentNotExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_NOT_FOUND_ERROR);
        }

        deleteById(id);
    }

    @Override
    public void updateMemberRole(MemberRole memberRole) {
        Integer id = memberRole.getId();

        MemberRole memberRoleExist = selectById(id);

        //判断成员角色对应关系是否存在
        if (memberRoleExist == null){
            throw new ContentNotExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_NOT_FOUND_ERROR);
        }

        MemberRole memberRoleAllExist = selectOne(new EntityWrapper<MemberRole>()
                        .eq("stuid",memberRole.getStuid())
                        .eq("role_id",memberRole.getRoleId())
        );
        //判断修改后的角色与成员的对应关系是否已经存在
        if (memberRoleAllExist != null){
            throw  new ContentAlreadyExistException(OrderExceptionEnum.TABLE_PIECE_OF_DATA_ALREADY_EXIST_ERROR);
        }


        updateById(memberRole);
    }
}
