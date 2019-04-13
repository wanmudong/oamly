package top.wanmudong.oamly.modules.common.utils.shiro.tool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.SpringContextHolder;
import top.wanmudong.oamly.modules.common.entity.SysPermission;
import top.wanmudong.oamly.modules.common.entity.SysRole;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.user.mapper.RoleMapper;
import top.wanmudong.oamly.modules.user.mapper.UserMapper;

import java.util.List;

/**
 * Created by chenjiehao on 2018/9/22
 */
@Service
@DependsOn("springContextHolder")
public class RealmKit implements IShiro {


    private UserMapper userDao;

    private RoleMapper roleDao;

    @Autowired
    protected RealmKit(UserMapper userDao,RoleMapper roleDao){
        this.userDao=userDao;
        this.roleDao=roleDao;
    }

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    public SysUser getSysuser(String username){
     return userDao.findSysUserByUsername(username);
    }

    public List<SysRole> getSysUserRoleList(String username){

       return userDao.getSysUserRoleList(username);
    }


    public List<SysPermission> getPermissions(SysRole role) {
       return roleDao.getPermissions(role.getId());
    }

    @Override
    public SysUser findSysUserByUsername(String i) {
//        SysUser sysUser = userDao.findSysUserByUsername(i);
        return userDao.findSysUserByUsername(i);
    }


}
