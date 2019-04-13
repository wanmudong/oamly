package top.wanmudong.oamly.modules.user.controller;



import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tomcat.jni.Time;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.SysUserHere;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;
import top.wanmudong.oamly.modules.user.service.DictService;
import top.wanmudong.oamly.modules.user.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
@RestController
@EnableAutoConfiguration
@RequestMapping
@Slf4j
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private DictService dictService;


    /**
     * 测试使用
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/getUsers")
    public Result getUsers(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        MyPageInfo<User> pageInfo = userService.getUsers();
        return Result.OK().put("data",pageInfo);
    }

    /**
     *根据登录的个人信息返回返回个人的详细信息以及相关设定好的字段
     * @return
     */
    @GetMapping("/api/getUserByStuid")
    @ResponseBody
    @ApiOperation("根据登录的个人信息返回返回个人的详细信息以及相关设定好的字段")
    @ApiImplicitParam(name = "stuid", value = "学号", required = true)
    public Result getNowUser(String stuid){

        List<Object> list = new ArrayList<>();
        User user = userService.getUserByStuid(stuid);
        Map<String, List<Dict>> dict = dictService.getAllDict();
        list.add(user);
        list.add(dict);
        return Result.OK().put("data",list);
    }
    /**
     * 根据条件获取用户列表
     */
    @GetMapping("/api/member")
    @ResponseBody
    @RequiresPermissions("user:member:list")
    @ApiOperation("根据条件获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "query")
    })

    public Result getUserByContent(Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){

        Map<String,Object> map = userService.getUserByContent(condition,pageNo,pageSize);
        return Result.OK().put("data",map);
    }
    /**
     * 以权限来更新id=key的用户的信息
     */
    @PostMapping("/api/member/{key}")
    @ResponseBody
    @RequiresPermissions("user:member:update")
    @ApiOperation("以权限来更新id=key的用户的信息")
    @ApiImplicitParam(name = "key",value = "用户id", required = true)
    public Result updateMember(User user, @PathVariable("key") String key){

        Boolean success = userService.updateMember(user);

        return  Result.OK();
    }
    /**
     * 获取搜索结果的excel表
     */
    @GetMapping("/api/member/excel")
    @ResponseBody
    @RequiresPermissions("user:member:list")
    @ApiOperation("获取搜索结果的excel表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "query")
    })
    public void getExcel(HttpServletResponse response, Condition condition,@RequestParam int pageNo, @RequestParam int pageSize) throws IOException{
        userService.getXSSFWorkbook(response,condition, pageNo, pageSize);
    }
    /**
     * 用户通过个人设置自己修改密码的接口
     */
    @PostMapping("/api/me/pwd")
    @ResponseBody
    @RequiresUser
    @ApiOperation("用户通过个人设置自己修改密码的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "oldPwd", value = "原密码", required = false, paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = false, paramType = "query")
    })
    public Result updateMemberPwd(String uid, String oldPwd,String newPwd){
        userService.updateMemberPwd(uid,oldPwd,newPwd);
        return Result.OK();
    }

    /**
     * 用户通过个人设置修改个人信息
     */
    @PostMapping("/api/me/{key}")
    @ResponseBody
    @RequiresUser
    @ApiOperation("用户通过个人设置自己修改密码的接口")
    @ApiImplicitParam(name = "key", value = "用户id（暂时无用）",paramType = "path")
    public Result updateMemberByMe(@PathVariable("key") String key,User user){
        userService.updateMemberByMe(key,user);
        return Result.OK();
    }
    /**
     * 添加成员
     */
    @PostMapping("/api/member/add")
    @ResponseBody
    @RequiresPermissions("user:member:add")
    @ApiOperation("添加成员")
    public Result addMember(User user){
        userService.insert(user);
        return Result.OK();
    }
    /**
     * 删除成员
     */
    @GetMapping("/api/member/del/{key}")
    @ResponseBody
    @RequiresPermissions("user:member:del")
    @ApiOperation("删除成员(慎用)")
    @ApiImplicitParam(name = "key", value = "用户id",paramType = "path")
    public Result delMember(@PathVariable("key") String key){
        userService.deleteById(key);
        return Result.OK();
    }
}
