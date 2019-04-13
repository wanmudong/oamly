package top.wanmudong.oamly.modules.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import top.wanmudong.oamly.modules.common.aop.Phone;
import top.wanmudong.oamly.modules.common.aop.Stuid;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "姓名不能为空")
    private String name;
    @Stuid
    private String stuid;
    @NotNull(message = "性别不能为空")
    private String  sex;
    @Phone
    private String phone;
    private String qq;
    @NotNull(message = "部门不能为空")
    private String depart;
    private String  time;
    private Integer  status;
    private Integer  current;
    @NotNull(message = "校区不能为空")
    private String  campus;
    private String college;
    private String  major;
    private String  say;
    @TableField(exist=false)
    private RecruitStatus recruitStatus;

}
