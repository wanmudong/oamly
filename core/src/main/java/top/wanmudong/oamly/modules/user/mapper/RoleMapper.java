package top.wanmudong.oamly.modules.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.wanmudong.oamly.modules.common.entity.SysPermission;

import java.util.List;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Mapper
public interface RoleMapper {

    /**
     * 通过role的id获取对应的权限列表
     */
    List<SysPermission> getPermissions(int role_id);

}
