package top.wanmudong.oamly.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruitStatusDto implements Serializable {
    /**、
     * 当前面试状态
     */
    private  int current;
    /**
     * 当前考核状态
     * 1，未通过  normal。2，通过
     */
    private String status;
    /**
     * 面试各轮情况
     */
    private List<Step> steps;
}
