package top.wanmudong.oamly.modules.report.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chenjiehao on 2018/9/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    /**
     * 薪酬的实体
     */

    /**
     * 最终薪资
     */
    private int fina;

    /**
     * 建议薪资
     */
    private int review;


}
