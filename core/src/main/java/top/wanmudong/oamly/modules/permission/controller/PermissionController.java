package top.wanmudong.oamly.modules.permission.controller;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.PageQuery;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.permission.entity.MemberRole;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.entity.Role;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;
import top.wanmudong.oamly.modules.permission.service.MemberRoleService;
import top.wanmudong.oamly.modules.permission.service.PermissionService;
import top.wanmudong.oamly.modules.permission.service.RolePermissionService;
import top.wanmudong.oamly.modules.permission.service.RoleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wanmudong
 * @date 14:16 2018/12/12
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private MemberRoleService memberRoleService;

    /**
     * 获取权限
     * @return
     */
    @GetMapping("/permissions")
    public Result getPermissions(PageQuery pageQuery){
        MyPageInfo<Permission> list = permissionService.selectPermissionList(pageQuery);
        return Result.OK().put("data",list);
    }



    /**
     * 获取角色
     * @param pageQuery
     * @return
     */
    @GetMapping("/roles")
    public Result getRoles(PageQuery pageQuery){
        MyPageInfo<Role> list = roleService.selectRoleList(pageQuery);
        return Result.OK().put("data",list);
    }

    //TODO:将get改为post
    /**
     * 增加角色信息
     * @param role
     * @return
     */
    @GetMapping("/role/add")
    public Result addRole(@Valid Role role){
        roleService.addRole(role);
        return Result.OK();
    }
    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @GetMapping("/role/del")
    public Result delRole(Integer id){
        roleService.delRole(id);
        return Result.OK();
    }
    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @GetMapping("/role/update")
    public Result updateRole(@Valid Role role){
        roleService.updateRole(role);
        return Result.OK();
    }
    /**
     * 获取角色权限对应关系
     * @param pageQuery
     * @return
     */
    @GetMapping("/rolePermissions")
    public Result getRolePermissions(PageQuery pageQuery){
        MyPageInfo<RolePermission> list = rolePermissionService.getRolePermissions(pageQuery);
        return Result.OK().put("data",list);
    }

    /**
     * 增加角色对应权限
     * @param rolePermission
     * @return
     */
    //TODO:将get改为post
    @GetMapping("/rolePermission/add")
    public Result addRolePermission(@Valid RolePermission rolePermission){
        rolePermissionService.addRolePermission(rolePermission);
        return Result.OK();
    }
    /**
     * 删除角色对应权限
     * @param rolePermission
     * @return
     */
    @GetMapping("/rolePermission/del")
    public Result delRolePermission(@Valid RolePermission rolePermission){
        rolePermissionService.delRolePermission(rolePermission.getId());
        return Result.OK();
    }
    /**
     * 更新角色对应权限
     * @param rolePermission
     * @return
     */
    @GetMapping("/rolePermission/update")
    public Result updateRolePermission(@Valid RolePermission rolePermission){
        rolePermissionService.updateRolePermission(rolePermission);
        return Result.OK();
    }
    /**
     * 获取成员角色对应关系
     * @param pageQuery
     * @return
     */
    @GetMapping("/memberRoles")
    public Result getMemberRoles(PageQuery pageQuery){
        MyPageInfo<MemberRole> list = memberRoleService.getMemberRoles(pageQuery);
        return Result.OK().put("data",list);
    }
    /**
     * 增加成员对应角色
     *
     * 目前仅限用于增加成员无角色时，若成员已有角色，限制使用
     *
     * @param memberRole
     * @return
     */
    //TODO:将get改为post
    @GetMapping("/memberRole/add")
    public Result addMemberRole(@Valid MemberRole memberRole){
        memberRoleService.addMemberRole(memberRole);
        return Result.OK();
    }

    /**
     * 删除成员对应角色
     *
     * @param memberRole
     * @return
     */
    @GetMapping("/memberRole/del")
    public Result delMemberRole(@Valid MemberRole memberRole){
        memberRoleService.delMemberRole(memberRole);
        return Result.OK();
    }
    /**
     * 更新成员对应角色
     *
     * @param memberRole
     * @return
     */
    @GetMapping("/memberRole/update")
    public Result updateMemberRole(@Valid MemberRole memberRole){
        memberRoleService.updateMemberRole(memberRole);
        return Result.OK();
    }

}
