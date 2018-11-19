package top.wanmudong.oamly.modules.common.utils.shiro.tool;


import top.wanmudong.oamly.modules.common.entity.SysPermission;
import top.wanmudong.oamly.modules.common.entity.SysRole;
import top.wanmudong.oamly.modules.common.entity.SysUser;

import java.util.List;

/**
 * Created by chenjiehao on 2018/9/22
 */
public interface IShiro {

    public SysUser getSysuser(String username);

    public List<SysRole> getSysUserRoleList(String username);
    public List<SysPermission> getPermissions(SysRole role);

    SysUser findSysUserByUsername(String i);
}
