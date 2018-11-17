package top.wanmudong.oamly.modules.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("oa_recruit")
public class Recruit implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField(exist=false)
    private int key;
    private String name;
    private String stuid;
    private String  sex;
    private String phone;
    private String qq;
    private String depart;
    private String  time;
    private int  status;
    private int  current;
    private String  campus;
    private String college;
    private String  major;
    private String  say;
    @TableField(exist=false)
    private RecruitStatus recruitStatus;

}
