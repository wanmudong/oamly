package top.wanmudong.oamly.modules.permission.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.entity.RolePermission;

import java.util.List;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    Page<RolePermission> getRolePermissions(@Param("state") Integer state);
}
