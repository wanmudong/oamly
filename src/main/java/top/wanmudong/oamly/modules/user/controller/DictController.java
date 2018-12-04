package top.wanmudong.oamly.modules.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.service.DictService;


import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/28
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/dict")
public class DictController {
    @Resource
    private DictService dictService;

    /**
     * 获取字典列表
     * @return
     */
    @GetMapping("")
//    @RequiresPermissions("dict:list")
    public Result getDictSummaryList(){
        List<Dict> list = dictService.getDictSummaryList();
        return Result.OK().put("data",list);
    }

    /**
     * 更新字典表
     * @return
     */
    @GetMapping("/update")
//    @RequiresPermissions("dict:list")
    public Result updateDictSummary(Dict dict){
        dictService.updateDictSummary(dict);
        return Result.OK();
    }
    /**
     * 新增一条字典表信息
     * @return
     */
    @GetMapping("/add")
//    @RequiresPermissions("dict:list")
    public Result insertDictSummary(Dict dict){
        dictService.insertDictSummary(dict);
        return Result.OK();
    }

    /**
     * 删除一条字典表信息
     * @return
     */
    @GetMapping("/del")
//    @RequiresPermissions("dict:list")
    public Result delDictSummary(Dict dict){
        dictService.delDictSummary(dict);
        return Result.OK();
    }

    /**
     * 获取字段表详细信息
     * @return
     */
    @GetMapping("/{key}")
//    @RequiresPermissions("dict:list")
    public Result getDictList(@PathVariable String key){
        List<Dict> list = dictService.getDictList(key);
        return Result.OK().put("data",list);
    }

    /**
     * 更新对应字段表
     * @return
     */
    @GetMapping("/update/{key}")
//    @RequiresPermissions("dict:list")
    public Result updateDict(@PathVariable String key,Dict dict){
        dictService.updateDict(key,dict);
        return Result.OK();
    }

    /**
     * 新增一条字段表信息
     * @return
     */
    @GetMapping("/add/{key}")
//    @RequiresPermissions("dict:list")
    public Result insertDict(@PathVariable String key,Dict dict){
        dictService.insertDict(key,dict);
        return Result.OK();
    }

    /**
     * 删除一条字段表信息
     * @return
     */
    @GetMapping("/del/{key}")
//    @RequiresPermissions("dict:list")
    public Result delDictInfo(@PathVariable String key,Dict dict){
        dictService.delDictInfo(key,dict);
        return Result.OK();
    }

}
