package top.wanmudong.oamly.modules.permission.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.wanmudong.oamly.modules.common.aop.Stuid;
import top.wanmudong.oamly.modules.common.aop.dictTableName;

import javax.validation.constraints.NotNull;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_member_role")
public class MemberRole {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("stuid")
    @Stuid
    @NotNull(message = "学号不能为空")
    private String stuid;

    @TableField("role_id")
    @NotNull(message = "角色不能为空")
    private String roleId;

}
