package top.wanmudong.oamly.modules.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.wanmudong.oamly.modules.common.aop.dictTableName;

/**
 * Created by chenjiehao on 2018/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("字典实体类")
public class Dict {
    /**
     * 用于存储系统汇报信息的实体
     */
//    private int key;
//
//    private String value;
    @ApiModelProperty("数据字典id(主键)")
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer key;

    @ApiModelProperty(value = "字典字段表对应项目名称",required = false)
    @TableField("dict_type_name")
    private String type;

    @ApiModelProperty(value = "字典表项目名称",required = true)
    @TableField("dict_item_name")
    private String value;

    @ApiModelProperty("1:使用 0:停用")
    @TableField("dict_enable")
    private Integer enable;

    @ApiModelProperty("备注")
    @TableField("dict_memo")
    private String memo;

    @ApiModelProperty(value = "数据字典项目名称对应的表",required = true,example = "dict_table-*")
    @dictTableName
    @TableField("dict_table_name")
    private String tableName;
}
