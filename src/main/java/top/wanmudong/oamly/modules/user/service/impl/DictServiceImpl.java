package top.wanmudong.oamly.modules.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.service.DictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/20
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    @Override
    public Map<String, List<Dict>> getAllDict() {
        List<String> list_table_name = baseMapper.getDictTableNames();
        Map<String, List<Dict>> map = new HashMap<String, List<Dict>>();
        for (String table_name:list_table_name)
        {
                String dict_name  = baseMapper.getDictMemo(table_name);
                List<Dict> list = baseMapper.getDictByTableName(table_name);
                map.put(dict_name,list);
        }
        return map;
    }

    @Override
    public List<Dict> getDictSummaryList() {
        return null;
    }
}
