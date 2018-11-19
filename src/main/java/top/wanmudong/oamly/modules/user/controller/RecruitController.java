package top.wanmudong.oamly.modules.user.controller;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.RecruitDto;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.service.RecruitService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/22
 */
@RestController
@EnableAutoConfiguration
public class RecruitController {
    @Resource
    private RecruitService recruitService;

    @Resource
    private DictMapper dictMapper;


    /**
     * 获取当前所有的招新信息
     */
    @GetMapping("/api/recruit")
    @RequiresPermissions("user:recruit:list")
    @ResponseBody
    public Result getRecruit(Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){

        Map<String,Object> map = recruitService.getRecruitByContent(condition,pageNo,pageSize);
        return Result.OK().put("data",map);
    }

    /**
     * 单独获取一条招新信息
     */
    @GetMapping ("api/recruit/{id}")
    @RequiresPermissions("user:recruit:info")
    public Result getRecruitById(@PathVariable("id") int id){
        RecruitDto recruitDto = recruitService.getRecruitById(id);
        return Result.OK().put("data",recruitDto);
    }
    /**
     * 更新招新信息
     */
    @PostMapping ("api/recruit/{id}")
    @RequiresPermissions("user:recruit:update")
    public Result updateRecruit(@PathVariable("id") int id, int status, String desc, String depart){
       RecruitDto recruitDto =  recruitService.updateRecruit(id,status,desc,depart);
       return Result.OK().put("data",recruitDto);
    }

    /**
     *将根据相关查询条件获取
     */
    @GetMapping("/api/recruit/excel")
    @ResponseBody
    @RequiresPermissions("user:recruit:list")
    public void gerExcel(HttpServletResponse response, Condition condition,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize) throws IOException{
        recruitService.getXSSFWorkbook(response,condition,pageNo,pageSize);
    }

    /**
     * 删除招新信息
     */
    @GetMapping("/api/recruit/del/{key}")
    @ResponseBody
    public Result delRecruit(@PathVariable("key" )int key){
        recruitService.delRecruit(key);
        return Result.OK();
    }

    /**
     * 新增招新信息
     */
    @GetMapping("/api/recruit/add")
    @ResponseBody
    public Result delRecruit(Recruit recruit){
        recruitService.insert(recruit);
        return Result.OK();
    }
}
