package top.wanmudong.oamly.modules.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenjiehao on 2018/9/24
 */
@Data
@ApiModel("查询条件")
public class Condition {
    /**
     * 查询条件
     */

    /**
     * 界别
     */
    @ApiModelProperty("界别")
    private String period;

    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private String depart;

    /**
     * 校区
     */
    @ApiModelProperty("校区")
    private String campus;

    /**
     * 内容
     */
    @ApiModelProperty("查询用户所需内容")
    private String content;

    /**
     * 内容
     */
    @ApiModelProperty("查询内容")
    private String contact;

    private int uid;
    /**
     *汇报开始时间
     */
    @ApiModelProperty("汇报开始时间")
    private String work_start_date;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}
