package top.wanmudong.oamly.modules.feedback.controller;


import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.feedback.entity.Feedback;
import top.wanmudong.oamly.modules.feedback.entity.FeedbackDto;
import top.wanmudong.oamly.modules.feedback.service.FeedbackService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/17
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;

    /**
     * 获取所有的反馈信息
     * @return
     */
    @GetMapping("")
    @RequiresPermissions("feedback:list")
    public Result getAllFeedback(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        MyPageInfo<FeedbackDto> pageInfo = feedbackService.getAllFeedback();
        return Result.OK().put("data",pageInfo);
    }

    /**
     * 存储一个反馈信息
     */
    @PostMapping("")
    @RequiresPermissions("feedback:add")
    public Result setFeedback(int value, String content,HttpServletRequest request){
        feedbackService.setFeedback(value,content,request);
        return Result.OK();
    }
}
