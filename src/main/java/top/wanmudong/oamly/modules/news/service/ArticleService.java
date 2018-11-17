package top.wanmudong.oamly.modules.news.service;


import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.news.entity.Article;

import java.util.Map;

/**
 * 文章管理
 *
 * @author chenliangliang
 * @date 2018-02-21 15:42:00
 */
public interface ArticleService extends IService<Article> {



    MyPageInfo queryPage(String tags);

    Article getDetail(Long id);


    MyPageInfo<Map<String,Object>> getIdAndTitle(Integer pageNum, Integer pageSize, Integer catalogId);

    void auditArticle(Long id, String commment);

    Integer updateAudit(Long id, Long pass, String comment);

    Boolean insertArticle(String json);

    String uploadArticle();

    MyPageInfo<Article> getHistory(String username);
}

