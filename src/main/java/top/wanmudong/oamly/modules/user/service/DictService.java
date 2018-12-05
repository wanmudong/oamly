package top.wanmudong.oamly.modules.user.service;

import com.baomidou.mybatisplus.service.IService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.user.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/20
 */
@Service
public interface DictService extends IService<Dict> {

    /**
     * 用来获取一些在数据库中定义好的字段，诸如部门，校区，工作状态等
     * @return
     */
    Map<String, List<Dict>> getAllDict();

    List<Dict> getDictSummaryList();

    List<Dict> getDictList(String key);

    void updateDict(String key, Dict dict);

    String getDictName(String key);

    Dict getDictSummary(Integer key);

    void updateDictSummary(Dict dict);

    void insertDictSummary(Dict dict);

    void delDictSummary(Dict dict);

    void insertDict(String key, Dict dict);

    void delDictInfo(String key, Dict dict);
}
