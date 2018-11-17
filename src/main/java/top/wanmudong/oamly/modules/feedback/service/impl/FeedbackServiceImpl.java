package top.wanmudong.oamly.modules.feedback.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.common.utils.*;
import top.wanmudong.oamly.modules.feedback.entity.Feedback;
import top.wanmudong.oamly.modules.feedback.entity.FeedbackDto;
import top.wanmudong.oamly.modules.feedback.mapper.FeedbackMapper;
import top.wanmudong.oamly.modules.feedback.service.FeedbackService;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.mapper.UserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjiehao on 2018/11/17
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper,Feedback> implements FeedbackService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private DictMapper dictMapper;

    @Override
    public MyPageInfo getAllFeedback() {
        PageInfo<Feedback> pageInfo = new PageInfo<>( baseMapper.selectAllFeedback());
        List<Feedback> list1=pageInfo.getList();
        List<FeedbackDto> list = new ArrayList<>();
        List<Feedback>list_f = baseMapper.selectAllFeedback();
        int key = 0;
        for (Feedback feedback:list_f)
        {
            FeedbackDto feedbackDto = new FeedbackDto();
            feedbackDto.setKey(key);

            String depart =userMapper.selectDepartByUid(feedback.getUid());
            feedbackDto.setDepart(depart);

            feedbackDto.setComment(feedback.getComment());
            feedbackDto.setContent(feedback.getContent());

            String name = userMapper.selectNameByUid(feedback.getUid());
            feedbackDto.setName(name);

            String time = timeUtil.dateTime(feedback.getFtime(),"yyyy-MM-dd HH:mm:ss");
            feedbackDto.setTime(time);

            String type = dictMapper.selectFeedbackTypeNameById(feedback.getType());
            feedbackDto.setType(type);

            key++;
            list.add(feedbackDto);
        }
        return new MyPageInfo<>(pageInfo,list);
    }

    @Override
    public void setFeedback(int value, String content, HttpServletRequest request) {

        Feedback feedback = new Feedback();
        feedback.setType(value);
        feedback.setUid(SysUserHere.getSysUser().getId());
        feedback.setFtime(Long.valueOf(timeUtil.getSecondTimeNow()));
        feedback.setContent(content);
        feedback.setIp(IpUtil.getIpAddr(request));
        feedback.setStatus(1);
        baseMapper.insertFeedback(feedback);
    }
}
