package top.wanmudong.oamly.modules.feedback.service;

import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.feedback.entity.Feedback;
import top.wanmudong.oamly.modules.feedback.entity.FeedbackDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/17
 */
public interface FeedbackService extends IService<Feedback> {
    MyPageInfo getAllFeedback();

    void setFeedback(int value, String content, HttpServletRequest request);
}
