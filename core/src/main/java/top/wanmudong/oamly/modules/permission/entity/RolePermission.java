package top.wanmudong.oamly.modules.permission.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_role_permission")
public class RolePermission {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("permission_id")
    @NotNull(message = "权限不为空")
    private String permissionId;

    @TableField("role_id")
    @NotNull(message = "角色不为空")
    private String roleId;

}
