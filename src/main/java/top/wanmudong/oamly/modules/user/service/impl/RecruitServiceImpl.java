package top.wanmudong.oamly.modules.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.user.entity.*;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.mapper.RecruitMapper;
import top.wanmudong.oamly.modules.user.mapper.StepsMapper;
import top.wanmudong.oamly.modules.user.service.RecruitService;
import top.wanmudong.oamly.modules.user.service.UserService;
import top.wanmudong.redis.annotation.Lock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenjiehao on 2018/9/22
 */
@Service

public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {

    @Resource
    private DictMapper dictMapper;
    @Resource
    private StepsMapper stepsMapper;
    @Resource
    private UserService userService;

    private static final Integer RECRUITING = 1;
    private static final Integer FIRST_TRIAL = 0;
    private static final String DEFAUL_MESSAGE = " 申请加入，待处理";


    @Override
    public Map<String, Object> getRecruitByContent(Condition condition, int pageNo, int pageSize) {
        Map map = new HashMap();
        List<Recruit> list = new ArrayList<Recruit>();
        List<Recruit> list1 = new ArrayList<Recruit>();

        map.put("conditions",condition);
        String depart = dictMapper.getDepartIdByName(condition.getDepart());
        String campus = dictMapper.getCampusIdByName(condition.getCampus())=="0"?"":dictMapper.getCampusIdByName(condition.getCampus());
        condition.setDepart(depart);
        condition.setCampus(campus);



        SysUser sysuser = SysUserHere.getSysUser();

        Integer role = Integer.valueOf(sysuser.getRole());
//        当权限为0 时 不进行查找
//        当权限为1时，只能查找登陆者所在的部门以及对应校区的成员
//                -但若用户所在部门为1，即办公室时，则可以查找所有成员并且可以看到银行卡号
//        当权限为2或3时，可以查找所有成员的信息，但不包括银行卡号。
//        当权限为1024时，为超管，权限与办公室部长相同,但更胜。
        if (role == 0){
            map.put("data",list);
        }else {
            PageHelper.startPage(pageNo,pageSize);
            PageInfo<Recruit> pageInfo = new PageInfo<>( baseMapper.getRecruitByContent(condition,sysuser));
            list=pageInfo.getList();
            for (Recruit recruit:list){
                list1.add( UserToUserMapper.poToDtoRecruit(recruit));
            }
            map.put("data",new MyPageInfo<>(pageInfo,list1));

        }
        return map;
    }

    @Override
    public RecruitDto getRecruitById(int id) {
        List list = new ArrayList();
        Recruit recruit = baseMapper.selectById(id);
        if (recruit == null){
            throw new ContentNotExistException(OrderExceptionEnum.THIS_CONTENT_NOT_FOUND_ERROR);
        }
        Steps steps = stepsMapper.selectById(id);
        if (steps!=null){
            Step step0 = new Step(0,"待处理",steps.getStep0()!=null?steps.getStep0():"");
            Step step1 = new Step(1,"一面",steps.getStep1()!=null?steps.getStep1():"");
            Step step2 = new Step(2,"二面",steps.getStep2()!=null?steps.getStep2():"");
            Step step3 = new Step(3,"三面",steps.getStep3()!=null?steps.getStep3():"");
            Step step4 = new Step(4,"四面",steps.getStep4()!=null?steps.getStep4():"");

            list.add(step0);
            list.add(step1);
            list.add(step2);
            list.add(step3);
            list.add(step4);
        }



        RecruitStatus rs = new RecruitStatus();
        rs.setSteps(list);
        recruit.setRecruitStatus(rs);
        RecruitDto recruitDto = UserToUserMapper.recruitToDto(recruit);
        return recruitDto;
    }

    @Override
    @Transactional
    @Lock(lockKey = "updateRecruit",expireTime = 5000,timeout = 2000)
    public RecruitDto updateRecruit(int id, int status, String desc, String depart) {
        /**
         * 0 拒绝 status 为0
         * 123 修改current 为123
         * 4 修改current为4 status 为2 通过考核 插入一条新数据进入oa_member
         * 5 转部门 读 depart 通过重置current为0
         */

        desc = Format.getFormat(desc);

        switch(status){
            case 0:
                //拒绝
                baseMapper.updateStatus(id,status);
                break;
            case 1:
                //转为二面

            case 2:
                //转为三面

            case 3:
                //转为四面
                baseMapper.updateCurrent(id,status);
                stepsMapper.updateLog(id,status,desc);
                break;
            case 4:
                //正式入职

                baseMapper.updateStatus(id,2);
                baseMapper.updateCurrent(id,status);
                stepsMapper.updateLog(id,status,desc);
                Recruit recruit = baseMapper.selectById(id);
                User user  = userService.insertUser(recruit);
                break;
            case 5:
                //转部门

                baseMapper.updateDepart(id,dictMapper.getDepartIdByName(depart),0);
                stepsMapper.clearRecruitLog(id,desc);
                break;
             default:
                 //无效操作
                 return null;

        }


        return getRecruitById(id);
    }

    @Override
    public void getXSSFWorkbook(HttpServletResponse response, Condition condition, int pageNo, int pageSize) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("测试表");

//            List<User> queryAll = userDao.getUserListByDepart("3");
//            设置导出的表的名字
        String fileName = "memberInfo.xls";
        //设置头

        String[] headers = {"部门", "姓名", "学号", "性别", "学院", "专业", "qq号", "手机号码", "申请时间", "自我介绍"};
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

        MyPageInfo<Recruit> members = (MyPageInfo<Recruit>) getRecruitByContent(condition, pageNo, pageSize).get("data");
        List<Recruit> list = members.getList();

        for (Recruit recruit : list) {
            row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(recruit.getDepart());
            row1.createCell(1).setCellValue(recruit.getName());
            row1.createCell(2).setCellValue(String.valueOf(recruit.getStuid()));
            row1.createCell(3).setCellValue(recruit.getSex());
            row1.createCell(4).setCellValue(recruit.getCollege());
            row1.createCell(5).setCellValue(recruit.getMajor());
            row1.createCell(6).setCellValue(recruit.getQq());
            row1.createCell(7).setCellValue(recruit.getPhone());
            row1.createCell(8).setCellValue(recruit.getTime());
            row1.createCell(9).setCellValue(recruit.getSay());

            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void delRecruit(int key) {
        baseMapper.deleteById(key);
    }

    @Override
    @Transactional
//    @Lock(lockKey = "insertRecruit", expireTime = 5000,timeout = 2000)
    public void insertRecruit(Recruit recruit) {


        recruit.setTime(String.valueOf(timeUtil.getSecondTimeNow()));
        recruit.setDepart(dictMapper.getDepartIdByName(recruit.getDepart()));
        recruit.setCampus(dictMapper.getCampusIdByName(recruit.getCampus()));
        recruit.setStatus(5);
        recruit.setCurrent(FIRST_TRIAL);

        Recruit recruitExist =  baseMapper.selectByStuid(recruit.getStuid());

       if (recruitExist != null){
           recruit.setId(recruitExist.getId());
           baseMapper.updateById(recruit);
//           throw new ContentAlreadyExistException(OrderExceptionEnum.RECRUIT_ALREADY_EXIST_ERROR);
           return;
       }


       baseMapper.insert(recruit);

       Recruit recruitInsert =  baseMapper.selectByStuid(recruit.getStuid());
       Integer id = recruitInsert.getId();
       String stepZero = timeUtil.dateTime(Long.parseLong(recruitInsert.getTime())) + DEFAUL_MESSAGE;

       baseMapper.insertRecruitLog(id,stepZero);

    }

}
