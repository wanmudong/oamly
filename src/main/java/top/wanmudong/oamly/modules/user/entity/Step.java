package top.wanmudong.oamly.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Step implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 招新面试每一轮的信息的实体
     */
    private int key;
    private String title;
    private String desc;

    public Step(int key, String title, String desc) {
        this.key = key;
        this.title = title;
        this.desc = desc;
    }
}
