package top.wanmudong.oamly.modules.news.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.wanmudong.oamly.modules.news.entity.Tag;

import java.util.List;

/**
 * Created by chenjiehao on 2018/10/27
 */



@Mapper
public interface TagMapper extends BaseMapper<Tag> {


    Integer insertTags(Tag tag);

    List<Tag> queryTags();

}
