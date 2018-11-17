package top.wanmudong.oamly.modules.report.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.common.utils.timeUtil;
import top.wanmudong.oamly.modules.report.entity.RDate;
import top.wanmudong.oamly.modules.report.entity.Report;
import top.wanmudong.oamly.modules.report.entity.ReportDto;
import top.wanmudong.oamly.modules.report.service.ReportService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Slf4j
@RequestMapping("/api/report")
@RestController
@EnableAutoConfiguration
public class RoeportController {

    @Resource
    private ReportService reportService;
    /**
     * 获取个人汇报历史
     */
    @GetMapping("/history")
    //@RequiresPermissions("report:history")
    public Result history(String uid){
       List<Report> list =  reportService.getHistory(uid);
       return Result.OK().put("data",list);
    }
    /**
     * 获取当前汇报状态
     * @param uid
     * @return
     */
    @GetMapping("/status")
//    @RequiresPermissions("report:status")
    public Result getStatus(String uid){
        RDate rDate = reportService.getStatus(uid);
        return Result.OK().put("data",rDate);
    }

    /**
     * 获取成员的汇报信息
     */
    @GetMapping("")
//    @RequiresPermissions("report:list")
    public Result getReportsbByContact(Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){
       MyPageInfo<ReportDto> pageInfo =  reportService.getReportByContent(condition,pageNo,pageSize,"");
       Result result = Result.OK().put("conditions",condition);
       result.put("data",pageInfo);
       return result;
     }

    /**
     * 修改汇报状态
     * @param rDate
     * @return
     */
    @PostMapping("/status")
//    @RequiresPermissions("report:status:update")
    public Result setStatus(RDate rDate){
        reportService.setStatus(rDate);
        return Result.OK();
    }

    /**
     * 存储接收到的汇报信息
     */
    @PostMapping("")
//    @RequiresPermissions("report:save")
    public Result getReportsbByContact(String content, String suggestion){
        RDate rDate  = reportService.setReport(content,suggestion);
        return Result.OK().put("data",rDate);
    }

    /**
     * 对某一汇报进行审核
     */
    @PostMapping("/review")
//    @RequiresPermissions("report:review")
    public Result reviewReport(int id,String comment,String rate,String salary_sug,String salary){
        reportService.reviewReport(id,comment,rate,salary_sug,salary);
        return Result.OK();
    }
    /**
     * 在权限允许的条件下获取条件选择后的excel文件
     */
    @GetMapping("/excel")
    @ResponseBody
//    @RequiresPermissions("report:list")
    public void getXSSFWorkbook(HttpServletResponse response, Condition condition, String end_date, String start_date,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize) throws IOException{
        reportService.getXSSFWorkbook(response,end_date,start_date,condition,pageNo,pageSize);
    }

    /**
     * 获取当前汇报状态
     */
    @GetMapping("/reportTimes")
    public Result reportTimes(){
        List list = reportService.getReportTimes();
        return Result.OK().put("data",list);
    }
}
