package top.wanmudong.oamly.modules.report.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by chenjiehao on 2018/11/17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("oa_report")
public class ReportExcel implements Serializable {
    private String depart;
    private String name;
    private String stuid;
    private String sex;
    private String college;
    private String phone;
    private String debitcard;
    private String salary;
    private String content;
    private String time_report;
}
