package top.wanmudong.oamly.modules.news.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.wanmudong.oamly.modules.news.entity.ArticleTag;

import java.util.List;

/**
 * Created by chenjiehao on 2018/10/28
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag>{


        Integer insertArticleTag(ArticleTag articleTag);

        List<Integer> queryArticleTags(@Param("article_id") Integer article_id);
}
