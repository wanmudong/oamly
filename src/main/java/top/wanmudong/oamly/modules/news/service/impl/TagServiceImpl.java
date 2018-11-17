package top.wanmudong.oamly.modules.news.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.wanmudong.oamly.modules.common.utils.JsonMapper;
import top.wanmudong.oamly.modules.news.entity.ResultTags;
import top.wanmudong.oamly.modules.news.entity.Tag;
import top.wanmudong.oamly.modules.news.mapper.TagMapper;
import top.wanmudong.oamly.modules.news.service.TagService;

import java.util.List;

/**
 * Created by chenjiehao on 2018/10/27
 */
@Service("TagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    public Boolean updateTag(){

        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());

        requestHeaders.add("token", "eyJpZCI6IjIiLCJ1c2VyIjoiYWRtaW4ifQ.yYYidvSXR9l893_R-49peY0Lg9vJEz27aue-zX_pYCc");
        RestTemplate restTemplate = new RestTemplate();

        //获取标签列表
        String url = "http://www.chenliangliang.xin/mly/open/api/tags";

        HttpEntity<String> requestEntity = new HttpEntity<String>(null,requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String sttr = response.getBody();

        ResultTags rs =  JsonMapper.fromJson(sttr,ResultTags.class);
        List<Tag> list = rs.getTags();
        for (Tag tag:list) {
            Tag tag1 =  baseMapper.selectById(tag.getTagId());
            if (tag1==null){
                baseMapper.insertTags(tag);
            }
        }
        return true;
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = baseMapper.queryTags();
        return tags;
    }
}
