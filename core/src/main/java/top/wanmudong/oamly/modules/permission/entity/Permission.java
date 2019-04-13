package top.wanmudong.oamly.modules.permission.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("sys_permission")
public class Permission {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    @JsonIgnore
    private String parentId;

    @TableField("parent_ids")
    @JsonIgnore
    private String parentIds;

    @TableField("available")
    private String available;

}
