package top.wanmudong.oamly.modules.report.entity;

import lombok.Data;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
public class RDate {

    /**
     * 用于存储完整的汇报状态的信息的实体
     */
    private String report_end_date;
    private String report_start_date;
    private String report_stat;
    private String review_end_date;
    private String review_start_date;
    private String work_end_date;
    private String work_start_date;
}
