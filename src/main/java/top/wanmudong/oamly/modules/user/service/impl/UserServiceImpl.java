package top.wanmudong.oamly.modules.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.entity.SysRole;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.mapper.UserMapper;
import top.wanmudong.oamly.modules.user.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.wanmudong.oamly.modules.common.utils.oa_md5.md5_salt;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public User insertUser(User user) {
        return null;
    }
    //初始密码为online666
    private static final String PASSWORD="online666";
    //初始成员角色为一般成员
    private static final int GENERALMEMBER=1;


//    TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
//    TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
//    TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User insertUser(Recruit recruit) {

        String password = oa_md5.md5(PASSWORD);
        String salt = oa_md5.radomString();
        String pwd = oa_md5.md5_salt(password,salt);
        int time = timeUtil.getSecondTimeNow();

        User userExist = baseMapper.getUserByStuid(recruit.getStuid());
        List<SysRole> userRoleExist = baseMapper.getSysUserRoleList(recruit.getStuid());

        if (userExist != null || userRoleExist.size()!=0){
            throw new ContentAlreadyExistException(OrderExceptionEnum.USER_ALREADY_EXIST_ERROR);
        }
        baseMapper.insertUser(recruit,pwd,salt,time);
        baseMapper.insertUserRole(GENERALMEMBER,recruit);
        User user = baseMapper.getUserByStuid(recruit.getStuid());
        return user;
    }

    @Override
    public MyPageInfo<User> getUsers() {

        PageInfo<User> pageInfo = new PageInfo<>(baseMapper.getUsers());
        return new MyPageInfo<User>(pageInfo);
    }

    @Override
    public User getUserByStuid(String stuid) {
        if (stuid == null ||stuid.equals("")){
            stuid = SysUserHere.getSysUser().getUsername();
        }
        return baseMapper.getUserByStuid(stuid);
    }

    @Override
    public User getUserLogin(String username) {
        return baseMapper.getUserLogin(username);
    }

    @Override
    public Map<String,Object> getUserByContent(Condition condition, int pageNo, int pageSize) {
        Map map = new HashMap();
        List<User> list = new ArrayList<User>();
        List<User> list1 = new ArrayList<User>();

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
            map.put("members",list);
        }else {
            PageHelper.startPage(pageNo,pageSize);
            PageInfo<User> pageInfo = new PageInfo<>(baseMapper.getUserByContent(condition,sysuser));
//            list = baseMapper.getUserByContent(condition,sysuser);
            list=pageInfo.getList();
            for (User user:list){
               list1.add( UserToUserMapper.poToDto(user));
            }
            map.put("members",new MyPageInfo<>(pageInfo,list1));
        }
        return map;
    }

    @Transactional
    @Override
    public Boolean updateMember(User user) {

        user = UserToUserMapper.DtoToPo(user);

        //利用user来获取sysUser对象
        SysUser sysUser = baseMapper.findSysUserByUsername(String.valueOf(user.getStuid()));
        String password = sysUser.getPassword();
        String salt = sysUser.getSalt();
        //判断密码这一项是否需要更新
        if (user.getPwd() == null || user.getPwd().equals("")) {
            //如果不需要更新那么直接将sysUser的密码存入user即可
            user.setPwd(password);
        } else {
            //如果需要修改密码，那么我们需要获取到前台传给user的密码然后对其进行加盐加密存入数据库
            password = user.getPwd();
            password = md5_salt(password, salt);
            user.setPwd(password);
        }
        //更改权限
        if ("0".equals(user.getRole())){
            baseMapper.updatePermissionByStuid(user.getStuid(),1);
        }else if ("1".equals(user.getRole())){
            baseMapper.updatePermissionByStuid(user.getStuid(),2);
        }else {
            baseMapper.updatePermissionByStuid(user.getStuid(),3);
        }
        Integer success  = baseMapper.updateById(user);
        return success>0;
    }

    @Override
    public void getXSSFWorkbook(HttpServletResponse response, Condition condition, int pageNo, int pageSize) throws IOException {
        //工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("测试表");

        //设置导出的表的名字
        String fileName = "memberInfo.xls";
        //设置头
        String[] headers = {"部门", "姓名", "学号", "性别", "学院", "专业", "qq号", "手机号码", "银行卡号"};
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
        MyPageInfo<User> members = (MyPageInfo<User>) getUserByContent(condition, pageNo, pageSize).get("members");
        List<User> list = members.getList();
        int rowNum = 1;
        XSSFRow row1;
        for (User user : list) {
            row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(user.getDepart());
            row1.createCell(1).setCellValue(user.getName());
            row1.createCell(2).setCellValue(String.valueOf(user.getStuid()));
            row1.createCell(3).setCellValue(user.getSex());
            row1.createCell(4).setCellValue(user.getCollege());
            row1.createCell(5).setCellValue(user.getMajor());
            row1.createCell(6).setCellValue(user.getQq());
            row1.createCell(7).setCellValue(user.getPhone());
            row1.createCell(8).setCellValue(user.getDebitcard());

            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void updateMemberPwd(String uid, String oldPwd, String newPwd) {
        SysUser sysUser = SysUserHere.getSysUser();

        if (uid == null || uid.equals("")) {
            uid = String.valueOf(sysUser.getId());
        }
        //判断前台传来的原密码是否正确
        if (sysUser.getPassword().equals(md5_salt(oldPwd,sysUser.getSalt())))
        {
            Integer success = baseMapper.updatePwd(uid,md5_salt(newPwd,sysUser.getSalt()));
        }
    }

    @Override
    public void updateMemberByMe(String key, User user) {
            baseMapper.updateMemberByMe(user);
    }
}
