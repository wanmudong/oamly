package top.wanmudong.oamly.modules.user.controller;



import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.RecruitDto;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.service.RecruitService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/22
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/recruit")
@Api(tags = "招新管理")
public class RecruitController {
    @Resource
    private RecruitService recruitService;

    @Resource
    private DictMapper dictMapper;


    /**
     * 获取当前所有的招新信息
     */
    @GetMapping("")
    @RequiresPermissions("user:recruit:list")
    @ResponseBody
    @ApiOperation("获取当前所有的招新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = false, paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "query",defaultValue = "10")
    })
    public Result getRecruit(Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){

        Map<String,Object> map = recruitService.getRecruitByContent(condition,pageNo,pageSize);
        return Result.OK().put("data",map);
    }

    /**
     * 单独获取一条招新信息
     */
    @GetMapping ("/{id}")
    @RequiresPermissions("user:recruit:info")
    @ApiOperation("获取当前所有的招新信息")
    @ApiImplicitParam(name = "id", value = "招新id", paramType = "path", required = true)
    public Result getRecruitById(@PathVariable("id") int id){
        RecruitDto recruitDto = recruitService.getRecruitById(id);
        return Result.OK().put("data",recruitDto);
    }
    /**
     * 修改招新情况
     */
    @PostMapping ("/{id}")
    @RequiresPermissions("user:recruit:update")
    @ApiOperation("修改招新情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "招新id", paramType = "path", required = true),
            @ApiImplicitParam(name = "status", value = "招新状态", paramType = "query", required = true),
            @ApiImplicitParam(name = "desc", value = "招新情况", paramType = "query"),
            @ApiImplicitParam(name = "depart", value = "部门（在需要转部门时使用）", paramType = "query")
    })
    public Result updateRecruit(@PathVariable("id") int id, int status, String desc, String depart){
       RecruitDto recruitDto =  recruitService.updateRecruit(id,status,desc,depart);
       return Result.OK().put("data",recruitDto);
    }

    /**
     *将根据相关查询条件获取excel
     */
    @GetMapping("/excel")
    @ResponseBody
    @RequiresPermissions("user:recruit:list")
    @ApiOperation("将根据相关查询条件获取excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = false, paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "query",defaultValue = "10")
    })
    public void gerExcel(HttpServletResponse response, Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize) throws IOException{
        recruitService.getXSSFWorkbook(response,condition,pageNo,pageSize);
    }

    /**
     * 删除招新信息
     */
    @GetMapping("/del/{key}")
    @ResponseBody
    @ApiOperation("删除招新信息(慎用)")
    @ApiImplicitParam(name = "key", value = "用户id",paramType = "path")
    public Result delRecruit(@PathVariable("key" )int key){
        recruitService.delRecruit(key);
        return Result.OK();
    }

    /**
     * 新增招新信息/修改招新信息
     */
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增招新信息/修改招新信息")
    public Result insertRecruit(@Valid Recruit recruit){
        recruitService.insertRecruit(recruit);
        return Result.OK();
    }
}
