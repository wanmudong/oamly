package top.wanmudong.oamly.modules.report.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by chenjiehao on 2018/11/15
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("oa_report")
public class Report implements Serializable {
    /**
     * 用于存储汇报信息的实体
     */
    /**
     * 自增，PrimaryKey
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    /**
     * 汇报者ID
     */
    private int  uid;
    /**
     * 工作起始时间
     */
    @TableField("start_date")
    @JsonProperty("start_date")
    private String  startDate;
    /**
     * 工作结束时间
     */
    @TableField("end_date")
    @JsonProperty("end_date")
    private String  endDate;
    /**
     * 部长薪资意见
     */
    @TableField("salary_sug")
    @JsonProperty("salary_sug")
    private String salarySug;
    /**
     * 最终薪资
     */
    private String  salary;
    /**
     * 汇报时间戳
     */
    @TableField("time_report")
    @JsonProperty("time_report")
    private String timeReport;
    /**
     * 部长评分
     */
    private float rate;
    /**
     * 汇报内容
     */
    private String content;
    /**
     * 意见建议
     */
    private String suggestion;
    /**
     * 部长回评
     */
    private String comment;
    @TableField(exist=false)
    private String name;
    @TableField(exist=false)
    private String status;
    /**
     * 汇报者所在部门
     */
    private String depart;
    /**
     * 汇报者所在校区
     */
    private String campus;
}
