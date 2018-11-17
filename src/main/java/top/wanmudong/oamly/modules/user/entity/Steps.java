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
@TableName("oa_recruit_log")
public class Steps implements Serializable {
    /**
     * 一个应聘者的多轮面试信息
     */

    /**
     * id
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private int id;

    /**
     * 初试信息
     */
    @TableField("step_0")
    private String step0;
    /**
     * 一轮面试信息
     */
    @TableField("step_1")
    private String step1;
    /**
     * 二轮面试信息
     */
    @TableField("step_2")
    private String step2;
    /**
     * 三轮面试信息
     */
    @TableField("step_3")
    private String step3;
    /**
     * 四轮面试信息
     */
    @TableField("step_4")
    private String step4;
}
