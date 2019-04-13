package top.wanmudong.oamly.modules.common.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author chenliangliang
 * @since 2018-02-18
 */
@Data
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer pid;
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField("permission")
    private String permission;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * ztree属性
     * 如果0则是不存在子菜单
     */
    @TableField(exist=false)
    private int open;

    /**
     * 子菜单
     */
    @TableField(exist=false)
    private List<SysPermission> list;

    /**
     * 父菜单名称
     */
    @TableField(exist=false)
    private String parentName;

}
