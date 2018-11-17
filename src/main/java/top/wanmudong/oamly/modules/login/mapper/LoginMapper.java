package top.wanmudong.oamly.modules.login.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import top.wanmudong.oamly.modules.announce.entity.Announce;
import top.wanmudong.oamly.modules.login.entity.Login;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
@Mapper
public interface LoginMapper extends BaseMapper<Login> {

//    @Select("select * from announce where status=1 order by id desc")
    Page<Announce> selectAnnounceList();

}
