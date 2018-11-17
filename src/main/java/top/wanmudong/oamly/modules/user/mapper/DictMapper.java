package top.wanmudong.oamly.modules.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import top.wanmudong.oamly.modules.user.entity.Dict;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 获取所有字典的表名
     * @return
     */
    List<String> getDictTableNames();
    /**
     * 获取各个表的代表的信息类型
     * @return
     */
    String getDictMemo(@Param("dict_table_name") String dict_table_name);
    /**
     *以表名获取各字典表数据
     * @param tableName
     * @return
     */

    List<Dict> getDictByTableName(@Param("tableName") String tableName);

    /**
     * 用部门名称获取部门代号
     * @param depart
     * @return
     */
    String getDepartIdByName(@Param("depart")String depart);

    /**
     * 用校区名称获取校区代号
     * @param campus
     * @return
     */
    String getCampusIdByName(@Param("campus")String campus);
    /**
     * 用部门代号获取部门名称
     * @return
     */
    String getDepartNameById(@Param("depart")String depart);

    /**
     * 通过反馈的类型获取该反馈类型的代号
     * @param type
     * @return
     */
    String selectFeedbackTypeNameById(@Param("type") int type);
}
