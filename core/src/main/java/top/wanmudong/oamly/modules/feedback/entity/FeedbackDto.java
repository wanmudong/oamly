package top.wanmudong.oamly.modules.feedback.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by chenjiehao on 2018/11/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用于存储从数据库查找到的反馈信息的实体
     */
    private int key;
    /**
     * 类型
     */
    private String type;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户名
     */
    private String name;
    /**
     * 状态
     */
    private int status;
    /**
     * 评论
     */
    private String comment;
    /**
     * 部门
     */
    private String depart;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 反馈时间
     */
    private String time;

}
