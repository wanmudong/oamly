//package top.wanmudong.oamly.modules.dict.entity;
//
//import com.baomidou.mybatisplus.annotations.TableField;
//import com.baomidou.mybatisplus.annotations.TableId;
//import com.baomidou.mybatisplus.annotations.TableName;
//import com.baomidou.mybatisplus.enums.IdType;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//
///**
// * Created by chenjiehao on 2018/11/28
// */
//@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class Dict {
//    /**
//     * 用于存储从数据库查找到的数据字典的实体
//     */
//    @TableId(value = "dict_id", type = IdType.AUTO)
//    private int id;
//
//    @TableField("dict_type_name")
//    private String dictTypeName;
//
//    @TableField("dict_item_name")
//    private String dictItemName;
//
//    @TableField("dict_enable")
//    private int dictEnable;
//
//    @TableField("dict_memo")
//    private String dictMemo;
//}
