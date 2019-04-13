package top.wanmudong.oamly.modules.permission.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_role")
public class Role {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("remark")
    @NotNull(message = "角色解释不能为空")
    private String remark;

    @TableField("name")
    @Pattern(regexp = "[a-z]+",message = "角色名称应当为小写英文")
    @NotNull(message = "角色名称不能为空")
    private String name;

}
