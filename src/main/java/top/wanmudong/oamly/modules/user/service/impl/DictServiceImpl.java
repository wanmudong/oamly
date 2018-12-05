package top.wanmudong.oamly.modules.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;
import top.wanmudong.oamly.modules.common.exception.ContentAlreadyExistException;
import top.wanmudong.oamly.modules.common.exception.ContentNotExistException;
import top.wanmudong.oamly.modules.user.entity.Dict;
import top.wanmudong.oamly.modules.user.mapper.DictMapper;
import top.wanmudong.oamly.modules.user.service.DictService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/20
 *
 * 在这里，我将oa_dict称为字典表
 * 每个对应的附属表称为字段表
 *
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
                List<Dict> list = baseMapper.getDictByTableNames(table_name);
                map.put(dict_name,list);
        }
        return map;
    }

    @Override
    public List<Dict> getDictSummaryList() {
        List<Dict> dictList = baseMapper.getDictSummaryList();
        return dictList;
    }

    @Override
    public List<Dict> getDictList(String key) {
        Dict dictSummary = getDictSummary(Integer.valueOf(key));
        List<Dict> dictList = baseMapper.getDictList(dictSummary.getTableName());
        return dictList;
    }

    @Override
    @Transactional
    public void updateDict(String key, Dict dict) {
        Dict dictSummary = getDictSummary(Integer.valueOf(key));

        //若表不存在会报 BadSqlGrammarException 然后被 GlobalControllerExceptionHandler 抓住
        Integer isSuccess = baseMapper.updateDict(dictSummary.getTableName(),dict);
    }

    @Override
    @Transactional
    public String getDictName(String key) {
        Integer id = Integer.valueOf(key);
        String tableName = baseMapper.getDictTableName(id);
        if (tableName==null){
            throw new ContentNotExistException(OrderExceptionEnum.THIS_CONTENT_NOT_FOUND_ERROR);
        }
        return tableName;
    }

    @Override
    @Transactional
    public Dict getDictSummary(Integer key) {
        Dict dictSummary = baseMapper.getDictByKey(key);
        if (dictSummary == null){
            throw new ContentNotExistException(OrderExceptionEnum.TABLE_FIELD_NOT_FOUND_ERROR);
        }
        if (dictSummary.getTableName() == null){
            throw new ContentNotExistException(OrderExceptionEnum.TABLE_NOT_FOUND_ERROR);
        }
        return dictSummary;
    }


    @Override
    @Transactional
    public void updateDictSummary(Dict dict) {
        /**
         * 更新表要保证信息存在，
         * 表名不允许更改
         */

        Dict dictSummary = getDictSummary(dict.getKey());
        dict.setTableName(dictSummary.getTableName());

        Integer isSuccessDict = baseMapper.updateDictBySummary(dict);
        Integer isSuccessDictSummary = baseMapper.updateDictSummary(dict);

    }

    @Override
    @Transactional
    public void insertDictSummary(Dict dict) {
        /**
         * 插入一条数据，在字典表中这个字段信息不能存在，也要保证这个字段表不存在，
         */
        Dict dictTableExist = baseMapper.getDictByTableName(dict.getTableName());
        Dict dictValueExist = baseMapper.getDictByTableValue(dict.getValue());
        if (dictTableExist != null){
            throw new ContentAlreadyExistException(OrderExceptionEnum.TABLE_ALREADY_EXIST_ERROR);
        }
        if (dictValueExist != null){
            throw new ContentAlreadyExistException(OrderExceptionEnum.TABLE_FIELD_ALREADY_EXIST_ERROR);
        }
        Integer isSuccessInsert = baseMapper.creatDictTable(dict);
        Integer isSuccess = baseMapper.insertDictSummary(dict);

    }

    @Override
    @Transactional
    public void delDictSummary(Dict dict) {
        /**
         * 删除一条数据，在字典表中这个字段信息存在，也要保证这个字段表存在，
         */
        Dict dictSummary = getDictSummary(dict.getKey());

        //若表不存在会报 BadSqlGrammarException 然后被 GlobalControllerExceptionHandler 抓住
        Integer isSuccessDel = baseMapper.delDictTable(dictSummary.getTableName());

        Integer isSuccess = baseMapper.delDictSummary(dict);
    }

    @Override
    @Transactional
    public void insertDict(String key, Dict dict) {
        /**
         * 插入一条数据，在字典表中这个表信息存在，也要保证这个字段表存在，
         */
        Dict dictSummary = getDictSummary(Integer.valueOf(key));
        dict.setType(dictSummary.getValue());
        dict.setTableName(dictSummary.getTableName());

        Integer isSuccess = baseMapper.insertDict(dict);
    }

    @Override
    @Transactional
    public void delDictInfo(String key, Dict dict) {
        /**
         * 删除一条数据，在字典表中这个表信息存在，也要保证这个字段表的字段存在，
         */
        Dict dictSummary = getDictSummary(Integer.valueOf(key));

        dict.setType(dictSummary.getValue());
        dict.setTableName(dictSummary.getTableName());

        Dict dictExist = baseMapper.getDictByValue(dict);
        if (dictExist == null){
            throw new ContentAlreadyExistException(OrderExceptionEnum.TABLE_FIELD_NOT_FOUND_ERROR);
        }

        Integer isSuccess = baseMapper.delDictInfo(dict);

    }
}
