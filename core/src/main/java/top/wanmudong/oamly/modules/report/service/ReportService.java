package top.wanmudong.oamly.modules.report.service;

import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.report.entity.RDate;
import top.wanmudong.oamly.modules.report.entity.Report;
import top.wanmudong.oamly.modules.report.entity.ReportDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
public interface ReportService extends IService<Report> {
    List<Report> getHistory(String uid);

    RDate getStatus(String uid);

    MyPageInfo<ReportDto> getReportByContent(Condition condition, int pageNo, int pageSize, String s);

    void setStatus(RDate rDate);

    RDate setReport(String content, String suggestion);

    void reviewReport(int id, String comment, String rate, String salary_sug, String salary);

    void getXSSFWorkbook(HttpServletResponse response, String end_date, String start_date, Condition condition, int pageNo, int pageSize) throws IOException;

    List getReportTimes();
}
