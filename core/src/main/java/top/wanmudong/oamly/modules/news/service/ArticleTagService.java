package top.wanmudong.oamly.modules.news.service;


import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.news.entity.ArticleTag;

/**
 * Created by chenjiehao on 2018/10/28
 */
public interface ArticleTagService extends IService<ArticleTag> {

    Integer insertArticleTag(ArticleTag articleTag);
}
