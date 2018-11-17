package top.wanmudong.oamly.modules.feedback.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.feedback.entity.Feedback;

import java.util.List;

/**
 * Created by chenjiehao on 2018/11/17
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
    /**
     * 获取所有status=1的反馈，即未解决的反馈
     * @return
     */
    Page<Feedback> selectAllFeedback();
    /**
     * 插入一条反馈信息
     * @param feedback
     * @return
     */
    long insertFeedback(@Param("feedback") Feedback feedback);
}
