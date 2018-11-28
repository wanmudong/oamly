package top.wanmudong.oamly.modules.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.common.entity.SysRole;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    Page<User> getUsers();
    /**
     * 通过用户名（学号）获取用户的相应验证信息
     *
     */
    SysUser findSysUserByUsername(String username);
    /**
     * 获取用户名对应的角色列表
     * @param username
     * @return
     */
    List<SysRole> getSysUserRoleList(String username);

    /**
     * 通过Stuid获取用户详细信息
     * @param stuid
     * @return
     */
    User getUserByStuid(String stuid);

    /**
     * 获取登录的用户属性
     * @return
     */
//    @Select("select id,avatar_url,stuid,name,depart,role from oa_member where stuid =#{user.stuid} and pwd = #{user.pwd}")
    User getUserLogin(@Param("username")String username);

    /**
     * 查询成员列表
     * @param condition
     * @param sysuser
     * @return
     */
    Page<User> getUserByContent(@Param("condition")Condition condition,@Param("sysuser")SysUser sysuser);
    /**
     * 更新用户的密码
     */
    Integer updatePwd(@Param("uid")String uid, @Param("pwd")String pwd);
    /**
     * 更新用户的信息
     */
    void updateMemberByMe(@Param("user")User user);

    /**
     * 插入一条数据
     */
    void insertUser(@Param("recruit")Recruit recruit,@Param("pwd") String pwd,@Param("salt") String salt,@Param("time") int time);
    /**
     * 查询成员的部门
     * @param uid
     * @return
     */
    String selectDepartByUid(@Param("uid") int uid);
    /**
     * 通过uid查询成员的名称
     * @param uid
     * @return
     */
    String selectNameByUid(@Param("uid") int uid);

    /**
     * 通过stuid查询成员
     * @param stuid
     * @return
     */
    User selectByStuid(@Param("stuid") String stuid);
    /**
     * 通过stuid修改成员权限
     * @param stuid
     * @return
     */
    Integer updatePermissionByStuid(@Param("stuid") String stuid,@Param("roleId") Integer roleId);
}
