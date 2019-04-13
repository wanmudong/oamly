package top.wanmudong.oamly.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruitDto {
    /**
     * 用于返回给前端的招新信息的实体
     */
    /**
     * key , campus,depart,status,status.status
     */
    private int key;
    private String name;
    private String stuid;
    private String  sex;
    private String phone;
    private String qq;
    private String depart;
    private String  time;
    private int  current;
    private String  campus;
    private String college;
    private String  major;
    private String  say;
    private RecruitStatusDto status;
    private String start_date;
    private String end_date;

}
