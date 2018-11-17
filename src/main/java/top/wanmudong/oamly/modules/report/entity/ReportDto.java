package top.wanmudong.oamly.modules.report.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    /**
     * 自增，PrimaryKey
     */
    private int id;

    /**
     * 工作起始时间
     */
    private String  start_date;
    /**
     * 工作结束时间
     */
    private String  end_date;
    /**
     * 薪资
     */
    private  Salary salary;

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
    /**
     * 用户名
     */
    private String username;
    /**
     * 部门
     */
    private String depart;
    /**
     * 学号
     */
    private String stuid;
    /**
     * 性别
     */
    private String sex;
    /**
     * 学院
     */
    private String college;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 银行卡号
     */
    private String debitcard;
    /**
     * 汇报时间
     */
    private String time_report;
}
