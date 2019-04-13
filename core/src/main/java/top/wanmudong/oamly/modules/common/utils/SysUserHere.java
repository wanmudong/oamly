package top.wanmudong.oamly.modules.common.utils;

import org.apache.shiro.SecurityUtils;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.exception.SysUserException;

/**
 * Created by chenjiehao on 2018/11/15
 */
public class SysUserHere {

    public static SysUser getSysUser(){
        SysUser sysuser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (sysuser == null){
            throw new SysUserException(OrderExceptionEnum.USER_NOT_LOGIN_ERROR);
        }
        return sysuser;
    }
}
