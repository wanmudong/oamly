package top.wanmudong.oamly.modules.news.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.news.entity.ArticleTag;
import top.wanmudong.oamly.modules.news.mapper.ArticleTagMapper;
import top.wanmudong.oamly.modules.news.service.ArticleTagService;

/**
 * Created by chenjiehao on 2018/10/28
 */
@Service("ArticleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public Integer insertArticleTag(ArticleTag articleTag) {

        Integer success = baseMapper.insert(articleTag);

        return success;
    }
}
