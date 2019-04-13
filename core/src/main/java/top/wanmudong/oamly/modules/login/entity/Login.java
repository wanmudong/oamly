package top.wanmudong.oamly.modules.login.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Login extends Model<Login> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "学号不可为空")
    @TableField("stuid")
    private String username;
    @NotNull(message = "密码不可为空")
    @TableField("pwd")
    private String password;
    @TableField(exist = false)
    private Boolean rememberMe;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
