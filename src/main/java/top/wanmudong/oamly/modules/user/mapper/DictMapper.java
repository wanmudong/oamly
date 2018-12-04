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

    List<Dict> getDictByTableNames(@Param("tableName") String tableName);

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

    /**
     * 获取所有字典表基本信息
     * @param
     * @return List<Dict>
     */
    List<Dict> getDictSummaryList();

    /**
     * 获取对应的字典表表名
     * @param id
     * @return
     */
    String getDictTableName(@Param("id") Integer id);

    /**
     * 获取字段表中信息
     * @param tableName
     * @return
     */
    List<Dict> getDictList(@Param("tableName") String tableName);

    /**
     * 更新字段表中信息
     */
    Integer updateDict(@Param("tableName") String tableName,@Param("dict") Dict dict);

    /**
     * 更新字典表中信息
     */
    Integer updateDictSummary(@Param("dict") Dict dict);

    /**
     * 向字典表中插入新信息
     */
    Integer insertDictSummary(@Param("dict") Dict dict);

    /**
     * 新建一个字段表
     */
    Integer creatDictTable(@Param("dict") Dict dict);

    /**
     * 在字典表中删除信息
     */
    Integer delDictSummary(@Param("dict") Dict dict);

    /**
     * 根据表名删除一张字段表
     * @param tableName
     * @return
     */
    Integer delDictTable(@Param("tableName") String tableName);

    /**
     *以表名获取字典表数据
     */
    Dict getDictByTableName(@Param("tableName") String tableName);

    /**
     *以字段名获取字段表数据
     */
    Dict getDictByValue(@Param("dict") Dict dict);

    /**
     *以字段id获取字典表数据
     */
    Dict getDictByKey(@Param("key") String key);

    /**
     * 在字段表中插入一条数据
     */
    Integer insertDict(@Param("dict") Dict dict);

    /**
     * 修改字段表中的类型名
     */
    Integer updateDictBySummary(@Param("dict") Dict dict);
    /**
     * 在字段表中删除一条数据
     */
    Integer delDictInfo(@Param("dict") Dict dict);
}
