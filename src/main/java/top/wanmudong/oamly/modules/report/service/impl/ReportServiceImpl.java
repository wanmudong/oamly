package top.wanmudong.oamly.modules.report.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.report.entity.RDate;
import top.wanmudong.oamly.modules.report.entity.Report;
import top.wanmudong.oamly.modules.report.entity.ReportDto;
import top.wanmudong.oamly.modules.report.entity.ReportExcel;
import top.wanmudong.oamly.modules.report.mapper.ReportDtoConverter;
import top.wanmudong.oamly.modules.report.mapper.ReportMapper;
import top.wanmudong.oamly.modules.report.service.ReportService;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static top.wanmudong.oamly.modules.report.Constant.DISPART_ERROR;
import static top.wanmudong.oamly.modules.report.Constant.STATUS_NORMAL;


/**
 * Created by chenjiehao on 2018/11/15
 */
@Service
public class ReportServiceImpl  extends ServiceImpl<ReportMapper, Report> implements ReportService {
    @Resource
    private DictMapper dictMapper;

    @Override
    public List<Report> getHistory(String uid) {
        if (uid == null || uid.equals("")) {
            uid = SysUserHere.getSysUser().getUsername();
        }
        List<Report> list = baseMapper.getHistory(uid);
        for (Report report:list)
        {
            report.setEndDate(timeUtil.dateTime(Long.parseLong(report.getEndDate()),"yyyy-MM-dd"));
            report.setStartDate(timeUtil.dateTime(Long.parseLong(report.getStartDate()),"yyyy-MM-dd"));
            report.setTimeReport(timeUtil.dateTime(Long.parseLong(report.getTimeReport()),"yyyy-MM-dd"));
        }
        return list;
    }

    @Override
    public RDate getStatus(String uid) {
        RDate rDate = new RDate();
        String report_start_date = baseMapper.getStatusByName("report_start_date");
        String report_end_date = baseMapper.getStatusByName("report_end_date");

        rDate.setReport_end_date(baseMapper.getStatusByName("report_end_date"));
        rDate.setReport_start_date(baseMapper.getStatusByName("report_start_date"));
        if (uid==null){
            uid = String.valueOf(SysUserHere.getSysUser().getId());
        }
        String state = baseMapper.getStatusByName("report_stat");
        if (state.equals("close"))
        {
            rDate.setReport_stat(state);
        }else {
            int times = baseMapper.getStatusByTime(Long.parseLong(timeUtil.dateTimeStamp(report_start_date,"yyyy-MM-dd")),Long.parseLong(timeUtil.dateTimeStamp(report_end_date,"yyyy-MM-dd")), Integer.parseInt(uid));
            if (times>0){
                rDate.setReport_stat("done");
            }else {
                rDate.setReport_stat("open");
            }
        }

        rDate.setReview_end_date(baseMapper.getStatusByName("review_end_date"));
        rDate.setReview_start_date(baseMapper.getStatusByName("review_start_date"));
        rDate.setWork_end_date(baseMapper.getStatusByName("work_end_date"));
        rDate.setWork_start_date(baseMapper.getStatusByName("work_start_date"));

        return rDate;
    }

    @Override
    public MyPageInfo getReportByContent(Condition condition, int pageNo, int pageSize, String s) {
        String work_start_date = condition.getWork_start_date();
        if (work_start_date == null ||  work_start_date.equals("")){
            work_start_date = baseMapper.getStatusByName("work_start_date");
        }
        String depart = dictMapper.getDepartIdByName(condition.getDepart());
        String campus = dictMapper.getCampusIdByName(condition.getCampus())=="0"?"":dictMapper.getCampusIdByName(condition.getCampus());

        condition.setDepart(depart);
        condition.setCampus(campus);
        work_start_date = timeUtil.dateTime(Long.parseLong(timeUtil.dateTimeStamp(work_start_date,"yyyy-MM-dd")),"yyyy-MM-dd HH:mm:ss");
        SysUser sysUser = SysUserHere.getSysUser();


        PageHelper.startPage(pageNo,pageSize);
        PageInfo pageInfo;

        if ("excel".equals(s)){
            pageInfo = new PageInfo<>(baseMapper.getReportByContentExcel(Long.parseLong(timeUtil.dateTimeStamp(work_start_date,"yyyy-MM-dd HH:mm:ss")),condition,sysUser));
            List<ReportExcel> list1=pageInfo.getList();
            for (ReportExcel reportExcel:list1){
                reportExcel.setDepart(dictMapper.getDepartNameById(reportExcel.getDepart()));
            }
            return new MyPageInfo (pageInfo,list1);
        }else {
            pageInfo = new PageInfo<>(baseMapper.getReportByContent(Long.parseLong(timeUtil.dateTimeStamp(work_start_date,"yyyy-MM-dd HH:mm:ss")),condition,sysUser));
            List<Report> list1=pageInfo.getList();
            List list2 =new  ArrayList<>();
            for (Report report:list1){
                ReportDto reportDto = ReportDtoConverter.INSTANCE.reportDto(report);
//                对于办公室小部长，能查看所有的成员，但只能审核本校区本部门成员
//                对于办公室大部长，能查看所有的成员，但只能审所有校区本部门成员
                if ("1".equals(sysUser.getDepart())){
                        if ("1".equals(report.getDepart())){
                            if(report.getCampus().equals(sysUser.getCampus())){
                                reportDto.setStatus(STATUS_NORMAL);
                            }else {
                                if ("2".equals(sysUser.getRole())){
                                    reportDto.setStatus(STATUS_NORMAL);
                                }else {
                                    reportDto.setStatus(DISPART_ERROR);
                                }
                            }
                        }else {
                            reportDto.setStatus(DISPART_ERROR);
                        }
                }
                list2.add(reportDto);
            }
            return new MyPageInfo (pageInfo,list2);
        }



    }

    @Override
    public void setStatus(RDate rDate) {
        boolean success=false;
        if (rDate.getReport_stat().equals("close")) {

            success = baseMapper.setStatusByName("report_stat",rDate.getReport_stat());
        } else {
             success = baseMapper.setStatusByName("report_start_date",rDate.getReport_start_date());
            Boolean success1 = baseMapper.setStatusByName("report_end_date",rDate.getReport_end_date());
            Boolean success2 = baseMapper.setStatusByName("report_stat",rDate.getReport_stat());
            Boolean success3 = baseMapper.setStatusByName("review_end_date",rDate.getReview_end_date());
            Boolean success4 = baseMapper.setStatusByName("review_start_date",rDate.getReview_start_date());
            Boolean success5 = baseMapper.setStatusByName("work_end_date",rDate.getWork_end_date());
            Boolean success6 = baseMapper.setStatusByName("work_start_date",rDate.getWork_start_date());
        }
    }

    @Override
    public RDate setReport(String content, String suggestion) {
        int uid = SysUserHere.getSysUser().getId();
        String work_start_date = baseMapper.getStatusByName("work_start_date");
        String work_end_date = baseMapper.getStatusByName("work_end_date");
        work_start_date = timeUtil.dateTime(Long.parseLong(timeUtil.dateTimeStamp(work_start_date,"yyyy-MM-dd")),"yyyy-MM-dd HH:mm:ss");

        work_start_date =  timeUtil.dateTimeStamp(work_start_date,"yyyy-MM-dd HH:mm:ss");

        work_end_date = timeUtil.dateTime(Long.parseLong(timeUtil.dateTimeStamp(work_end_date,"yyyy-MM-dd")),"yyyy-MM-dd HH:mm:ss");

        work_end_date =String.valueOf( Long.parseLong( timeUtil.dateTimeStamp(work_end_date,"yyyy-MM-dd HH:mm:ss"))+86400-1);

        baseMapper.setReport(uid,work_start_date,work_end_date,timeUtil.getSecondTimeNow(),content,suggestion==null?"":suggestion);
        return getStatus(String.valueOf(uid));
    }

    @Override
    public void reviewReport(int id, String comment, String rate, String salary_sug, String salary) {
        baseMapper.reviewReport(id,  comment,  rate,  salary_sug,  salary);
    }

    @Override
    public void getXSSFWorkbook(HttpServletResponse response, String end_date, String start_date, Condition condition, int pageNo, int pageSize) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("测试表");
        //设置导出的表的名字
        String fileName = "memberInfo.xls";
        //设置头

        String[] headers = {"部门", "姓名", "学号", "性别", "学院", "手机号码", "银行卡号", "工资", "汇报", "汇报时间"};
        XSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }

        int rowNum = 1;
        XSSFRow row1;
        condition.setWork_start_date(condition.getWork_start_date());


        MyPageInfo<ReportExcel> pageInfo = getReportByContent(condition, pageNo, pageSize, "excel");
        List<ReportExcel> list = pageInfo.getList();


        for (ReportExcel rReport : list) {

            row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rReport.getDepart());
            row1.createCell(1).setCellValue(rReport.getName());
            row1.createCell(2).setCellValue(String.valueOf(rReport.getStuid()));
            row1.createCell(3).setCellValue(rReport.getSex());
            row1.createCell(4).setCellValue(rReport.getCollege());
            row1.createCell(5).setCellValue(rReport.getPhone());
            row1.createCell(6).setCellValue(rReport.getDebitcard());
            row1.createCell(7).setCellValue(rReport.getSalary());
            row1.createCell(8).setCellValue(rReport.getContent());
            row1.createCell(9).setCellValue(rReport.getTime_report());


            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public List getReportTimes() {
        List<Long> list = baseMapper.selectReportTimes();
        List<String> list1 = new ArrayList<>();
        for (Long time:list){
            list1.add(timeUtil.dateTime(time,"yyyy-MM-dd"));
        }
        return list1;
    }
}
