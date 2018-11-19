package top.wanmudong.oamly.modules.announce.web;


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
public class AnnounceController {

    private AnnounceService announceService;

    @Autowired
    protected AnnounceController(AnnounceService announceService) {
        this.announceService = announceService;
    }
    /**
     * 用于主页面通知的显示
     */
    @GetMapping("")
    public Result getAnnounceModel(){
        List list = announceService.getAnnounceList();
      return  Result.OK().put("data",list);
    }

    /**
     * 上传通知
     */
    @PostMapping("")
    @RequiresPermissions("announce:post")
    public Result setAnnounceModel(@Valid Announce announce){
            announceService.setAnnounce(announce);
        return  Result.OK();
    }

    /**
     * 用于删除一条通知，需要知道该条通知的id
     * @param id
     * @return
     */
    @GetMapping("/del")
    @RequiresPermissions("announce:delete")
    public Result setAnnounceModel(String id) {
        announceService.deleteById(Integer.valueOf(id));
        return Result.OK();
    }
}
