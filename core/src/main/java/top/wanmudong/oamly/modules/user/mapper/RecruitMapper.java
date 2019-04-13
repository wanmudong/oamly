package top.wanmudong.oamly.modules.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;

import java.util.List;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Mapper
public interface RecruitMapper extends BaseMapper<Recruit> {
    List<Recruit> getRecruitByContent(@Param("condition")Condition condition, @Param("sysuser")SysUser sysuser);


    /**
     *修改面试者状态
     */
    void updateStatus(@Param("id")int id,@Param("status") int status);
    /**
     * 修改当前面试状态
     */
    void updateCurrent(@Param("id")int id,@Param("current") int current);
    /**
     * 更新一条招新信息的部门
     */
    void updateDepart(@Param("id")int id,@Param("depart") String depart,@Param("current") int current);

    Recruit selectByStuid(@Param("stuid") String stuid);

    List<Recruit> selectBystatus(@Param("i") int i);

    void insertRecruitLog(@Param("id") Integer id,@Param("stepZero") String stepZero);
}
