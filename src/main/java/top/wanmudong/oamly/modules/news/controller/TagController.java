package top.wanmudong.oamly.modules.news.controller;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.news.entity.Tag;
import top.wanmudong.oamly.modules.news.service.TagService;

import java.util.List;


/**
 * Created by chenjiehao on 2018/10/27
 */
@RestController
@EnableAutoConfiguration
public class TagController {

    private TagService tagService;

    @Autowired
    protected TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 更新标签库
     */
//    @GetMapping("api/tag/update")
    @Scheduled(cron = "0 0 21 ? * SUN")
    @RequiresPermissions("news:tag:update")
    public Result updateTag(){
            tagService.updateTag();
        return Result.OK();
    }

    /**
     * 获取标签库
     */
    @GetMapping("api/tag/get")
//    @RequiresPermissions("news:tag:list")
    public Result getTag(){
        List<Tag> list = tagService.getTags();
        return Result.OK().put("data",list);
    }

}
