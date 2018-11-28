package top.wanmudong.oamly.modules.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Result getAllFeedback(){
        List<Dict> list = dictService.getDictSummaryList();
        return Result.OK().put("data",list);
    }
}
