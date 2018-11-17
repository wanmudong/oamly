package top.wanmudong.oamly.modules.announce.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.wanmudong.oamly.modules.announce.entity.Announce;
import com.github.pagehelper.Page;



/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */
@Mapper
public interface AnnounceMapper extends BaseMapper<Announce> {

//    @Select("select * from announce where status=1 order by id desc")
    Page<Announce> selectAnnounceList();

}
