package top.wanmudong.oamly.modules.feedback.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by chenjiehao on 2018/11/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("oa_feedback")
public class Feedback  {
    /**
     * 用于存储从数据库查找到的反馈信息的实体
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    /**
     * 类型
     */
    private int type;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户id
     */
    private int uid;
    /**
     * 状态
     */
    private int status;
    /**
     * 评论
     */
    private String comment;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 反馈时间
     */
    private Long ftime;

}
