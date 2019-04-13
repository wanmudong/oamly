package top.wanmudong.oamly.modules.announce.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.wanmudong.oamly.modules.announce.entity.Announce;
import top.wanmudong.oamly.modules.announce.service.AnnounceService;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.redis.annotation.Lock;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/announce")
@Api(tags = "通知管理")
public class AnnounceController {
    @Resource
    private AnnounceService announceService;

    @Autowired
    protected AnnounceController(AnnounceService announceService) {
        this.announceService = announceService;
    }
    /**
     * 用于主页面通知的显示
     */
    @Lock(lockKey = "updateRecruit",expireTime = 5000,timeout = 2000)
    @GetMapping("")
    @ApiOperation("获取通知列表")
    public Result getAnnounceModel(){
        List list = announceService.getAnnounceList();
      return  Result.OK().put("data",list);
    }

    /**
     * 上传通知
     */
    @PostMapping("")
    @RequiresPermissions("announce:post")
    @ApiOperation("上传通知")
    public Result setAnnounceModel(@Valid Announce announce){
            announceService.setAnnounce(announce);
        return  Result.OK();
    }

    /**
     * 用于删除一条通知，需要知道该条通知的id
     * @return
     * @param id
     */
    @GetMapping("/del")
    @RequiresPermissions("announce:delete")
    @ApiOperation("删除通知")
    @ApiImplicitParam(value = "通知id",required = true, paramType = "query", dataType = "String")
    public Result setAnnounceModel(String id) {
        announceService.deleteById(Integer.valueOf(id));
        return Result.OK();
    }
}
