package top.wanmudong.oamly.modules.user.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("oa_member")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String stuid;

    private String name;

    private String sex;

    private String depart;

    /**
     * 银行卡
     */
    private String debitcard;

    /**
     * 角色
     */
    private String role;

    private String avatarUrl;

    private String pwd;

    private String salt;

    /**
     * 在职状态
     */
    private String status;

    private String joinTime;

    private String exitTime;

    private String phone;

    private String email;

    private String qq;

    /**
     * 校区
     */
    private String campus;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;


}
