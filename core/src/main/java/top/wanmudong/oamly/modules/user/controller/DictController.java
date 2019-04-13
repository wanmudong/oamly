package top.wanmudong.oamly.modules.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.entity.User;
import top.wanmudong.oamly.modules.user.service.DictService;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/11/28
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/dict")
@Api(tags = "字典管理")
public class DictController {
    @Resource
    private DictService dictService;

    /**
     * 获取字典列表
     * @return
     */
    @GetMapping("")
//    @RequiresPermissions("dict:list")
    @ApiOperation("获取字典列表")
    public Result getDictSummaryList(){
        List<Dict> list = dictService.getDictSummaryList();
        return Result.OK().put("data",list);
    }

    /**
     * 更新字典表
     * @return
     */
    @PostMapping("/update")
    @RequiresPermissions("dict:update")
    @ApiOperation("更新字典表")
    public Result updateDictSummary(Dict dict){
        dictService.updateDictSummary(dict);
        return Result.OK();
    }
    /**
     * 新增一条字典表信息
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("dict:add")
    @ApiOperation("新增一条字典表信息")
    public Result insertDictSummary(@Valid Dict dict, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                return Result.error(error.getDefaultMessage());
            }
        }
        dictService.insertDictSummary(dict);
        return Result.OK();
    }

    /**
     * 删除一条字典表信息
     * @return
     */
    @GetMapping("/del")
    @RequiresPermissions("dict:del")
    @ApiOperation("删除一条字典表信息")
    public Result delDictSummary(Dict dict){
        dictService.delDictSummary(dict);
        return Result.OK();
    }

    /**
     * 获取字段表详细信息
     * @return
     */
    @GetMapping("/{id}")
//    @RequiresPermissions("dict:info")
    @ApiOperation("获取字段表详细信息")
    @ApiImplicitParam(name = "id",value = "字段表id",required = true, dataType = "String")
    public Result getDictList(@PathVariable String id){
        List<Dict> list = dictService.getDictList(id);
        return Result.OK().put("data",list);
    }

    /**
     * 更新对应字段表
     * @return
     */
    @PostMapping("/update/{id}")
    @RequiresPermissions("dict:info:update")
    @ApiOperation("更新对应字段表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "字段表id",required = true, dataType = "String")

            }
    )
    public Result updateDict(@PathVariable String id,Dict dict){
        dictService.updateDict(id,dict);
        return Result.OK();
    }

    /**
     * 新增一条字段表信息
     * @return
     */
    @PostMapping("/add/{id}")
    @RequiresPermissions("dict:info:add")
    @ApiOperation("新增一条字段表信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "字段表id",required = true, dataType = "String")

            }
    )
    public Result insertDict(@PathVariable String id,Dict dict){
        dictService.insertDict(id,dict);
        return Result.OK();
    }

    /**
     * 删除一条字段表信息
     * @return
     */
    @GetMapping("/del/{id}")
    @RequiresPermissions("dict:info:del")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "字段表id",required = true, dataType = "String")

            }
    )
    public Result delDictInfo(@PathVariable String id,Dict dict){
        dictService.delDictInfo(id,dict);
        return Result.OK();
    }

    /**
     *
     * @return
     */
    @GetMapping("/get/departAndCampus")
    @ResponseBody
    @ApiOperation("获取部门和校区")
    public Result getDepartAndCampus(){

        List<Object> list = new ArrayList<>();
        Map<String, List<Dict>> dict = dictService.getDepartAndCampus();
        list.add(dict);
        return Result.OK().put("data",list);
    }

}
