package top.wanmudong.oamly.modules.user.entity;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
public class RecruitStatus {

    /**、
     * 当前面试状态
     */
    private  int current;
    /**
     * 当前考核状态
     * 1，未通过  normal。2，通过
     */
    private int status;
    /**
     * 面试各轮情况
     */
    private List<Step> steps;
}
