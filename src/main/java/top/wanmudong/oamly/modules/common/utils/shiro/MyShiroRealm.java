package top.wanmudong.oamly.modules.common.utils.shiro;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import top.wanmudong.oamly.modules.common.entity.SysPermission;
import top.wanmudong.oamly.modules.common.entity.SysRole;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.CustomCredentialsMatcher;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.IShiro;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.RealmKit;
import top.wanmudong.oamly.modules.user.mapper.RoleMapper;
import top.wanmudong.oamly.modules.user.mapper.UserMapper;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenjiehao on 2018/9/21
 */

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userDao;
    @Resource
    private RoleMapper roleDao;


    /**
     * 设定Password校验.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        //该句作用是重写shiro的密码验证，让shiro用我自己的验证
        setCredentialsMatcher(new CustomCredentialsMatcher());

    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        //获取用户的输入的账号.
       UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String    username = token.getUsername();
        log.info("用户{}登录",username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        IShiro shiro = RealmKit.me();
        SysUser sysUser = shiro.findSysUserByUsername(username);

        if(sysUser == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser, //用户名
                sysUser.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        SysUser sysUser  = (SysUser)principals.getPrimaryPrincipal();


        IShiro shiro = RealmKit.me();
        List<SysRole> role = shiro.getSysUserRoleList(sysUser.getUsername());
        for (SysRole sysRole:role){
//            log.info("用户{}角色为{}",sysUser.getUsername(),sysRole.getRemark());
            authorizationInfo.addRole(sysRole.getName());
            List<SysPermission> listP=shiro.getPermissions(sysRole);
            for(SysPermission p:listP){
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }
}
