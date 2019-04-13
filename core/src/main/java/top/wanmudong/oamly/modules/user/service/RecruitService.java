package top.wanmudong.oamly.modules.user.service;


import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.RecruitDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/9/22
 */

public interface RecruitService extends IService<Recruit> {

    Map<String,Object> getRecruitByContent(Condition condition, int pageNo, int pageSize);

    RecruitDto getRecruitById(int id);

    RecruitDto updateRecruit(int id, int status, String desc, String depart);

    void getXSSFWorkbook(HttpServletResponse response, Condition condition, int pageNo, int pageSize) throws IOException;

    void delRecruit(int key);

    void insertRecruit(Recruit recruit);
}
