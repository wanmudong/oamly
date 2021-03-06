package top.wanmudong.oamly.modules.news.controller;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.annotation.SysLog;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.news.entity.Article;
import top.wanmudong.oamly.modules.news.service.ArticleService;


/**
 * 文章管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
@Slf4j
@RestController
@EnableAutoConfiguration
@RequestMapping("api/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    protected ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 列表
     */
   @RequiresPermissions("news:article:list")
    @GetMapping("/list")
    public Result list(String tags,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        MyPageInfo page = articleService.queryPage(tags);

        return Result.OK().put("data",page);
    }



    @GetMapping("/info/{id}")
    @RequiresPermissions("news:article:info")
    public Result detail(@PathVariable("id") Long id){
        Article article=articleService.getDetail(id);
        return Result.OK().put("data",article);
    }




    /**
     * 更新管理后台文章
     */

   @RequiresPermissions("news:article:upload")
    @GetMapping("/upload")
//    @Scheduled(cron = "0 0 21 ? * SUN")
    public Result upload(){

        String msg = articleService.uploadArticle();
        if ("success".equals(msg)){
            log.info(msg);
            return Result.OK();
        }
        log.info(msg);
        return Result.error(msg);
    }

    /**
     * 审核文章
     */
    @PostMapping("/audit/{id}")
    @RequiresPermissions("news:article:audit")
    public Result audit(@PathVariable("id") Long id,  String comment,  Long pass) {
        Integer num;
        try {
            num = articleService.updateAudit(id, pass, comment);
        } catch (Exception e) {
            log.info("审核失误");
            return Result.error("审核失误");
        }
        return Result.OK();
    }
    /**
     * 保存（json格式）
     */
    @PostMapping("/save")
    @RequiresPermissions("news:article:save")
    public Result save( @RequestBody String json) {
        log.info("收到了({})",json);
        try {
            articleService.insertArticle(json);
        }catch (Exception e){
            log.info("提交数据有误",e);
            return Result.error("提交数据有误");
        }
        return Result.OK();
    }

    /**
     * 修改
     */
    @SysLog("修改文章")
    @PostMapping("/update")
    @RequiresPermissions("news:article:update")
    public Result update(@RequestBody Article article) {
            articleService.updateById(article);
        return Result.OK();
    }


    /**
     * 历史文章
     */
    @SysLog("历史文章")
    @GetMapping("/history")
    @RequiresPermissions("news:article:history")
    public Result history( String username,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {

        MyPageInfo myPageInfo = articleService.getHistory(username);
        return Result.OK().put("data",myPageInfo);
    }


    /**
     * 删除
     */
    @SysLog("删除文章")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("news:article:delete")
    public Result delete(@PathVariable("id")Long  id) {
            articleService.deleteById(id);
        return Result.OK();
    }

}
