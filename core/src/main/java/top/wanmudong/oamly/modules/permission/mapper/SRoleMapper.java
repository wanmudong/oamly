package top.wanmudong.oamly.modules.permission.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.permission.entity.Permission;
import top.wanmudong.oamly.modules.permission.entity.Role;

/**
 * @author wanmudong
 * @date 14:17 2018/12/12
 */
@Mapper
public interface SRoleMapper extends BaseMapper<Role> {
    Page<Role> selectRoleList(@Param("state") Integer state);
}
