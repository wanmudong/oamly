package top.wanmudong.oamly.modules.announce.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@ApiModel("通知实体类")
public class Announce extends Model<Announce> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("标题")
    @NotNull(message = "标题不可为空")
    private String title;
    @ApiModelProperty("内容")
    @NotNull(message = "内容不可为空")
    private String content;

    @ApiModelProperty("上传时间")
    @TableField("publish_time")
    private Integer publishTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("用户标识符")
    private Integer uid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
