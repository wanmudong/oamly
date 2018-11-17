package top.wanmudong.oamly.modules.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.user.entity.Steps;

/**
 * Created by chenjiehao on 2018/11/15
 */
@Mapper
public interface StepsMapper extends BaseMapper<Steps> {
    void updateLog(@Param("id") int id,@Param("status") int status,@Param("desc") String desc);
    /**
     * 将id对应的一组数据设为空
     */
    void clearRecruitLog(@Param("id")int id,@Param("desc") String desc);
}
