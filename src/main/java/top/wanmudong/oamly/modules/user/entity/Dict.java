package top.wanmudong.oamly.modules.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dict {
    /**
     * 用于存储系统汇报信息的实体
     */
//    private int key;
//
//    private String value;

    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer key;

    @TableField("dict_type_name")
    private String type;

    @TableField("dict_item_name")
    private String value;

    @TableField("dict_enable")
    private Integer enable;

    @TableField("dict_memo")
    private String memo;

    @TableField("dict_table_name")
    private String tableName;
}
