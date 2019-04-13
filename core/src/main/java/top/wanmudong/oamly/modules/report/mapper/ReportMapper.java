package top.wanmudong.oamly.modules.report.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.report.entity.Report;
import top.wanmudong.oamly.modules.report.entity.ReportExcel;

import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Mapper
public interface ReportMapper extends BaseMapper<Report> {
    /**
     * 查询历史汇报记录
     * @param uid
     * @return
     */
    List<Report> getHistory(@Param("uid") String uid);
    /**
     * 通过状态的名字查询
     * @param name
     * @return
     */
    String getStatusByName(@Param("name") String name);
    /**
     * 查询出一个用户在当前汇报时间汇报的数量
     * @return
     */
    int getStatusByTime(@Param("report_start_date") long report_start_date,@Param("report_end_date") long report_end_date, @Param("uid")int uid);
    /**
     *  通过campus,depart,start_date，content获取汇报信息
     */
    Page<Report> getReportByContent(@Param("time")long time, @Param("condition") Condition condition, @Param("sysuser") SysUser sysuser);
    /**
     * 更新汇报时间表的字段数据
     * @param name
     * @param value
     * @return
     */
    boolean setStatusByName(@Param("name") String name,@Param("value") String value);
    /**
     * 插入一条汇报信息
     */
    void setReport(@Param("uid") int uid, @Param("work_start_date")String work_start_date, @Param("work_end_date")String work_end_date, @Param("secondTimeNow")int secondTimeNow,@Param("content") String content,@Param("suggestion") String suggestion);

    void reviewReport(@Param("id")int id, @Param("comment")String comment, @Param("rate")String rate,@Param("salary_sug") String salary_sug,@Param("salary") String salary);

    List<Long> selectReportTimes();

    Page<ReportExcel> getReportByContentExcel(@Param("time")long time, @Param("condition") Condition condition, @Param("sysuser") SysUser sysuser);
}
