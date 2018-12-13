package top.wanmudong.oamly.modules.user.service;

import com.baomidou.mybatisplus.service.IService;
import top.wanmudong.oamly.modules.common.utils.Condition;
import top.wanmudong.oamly.modules.common.utils.MyPageInfo;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-14
 */
public interface UserService extends IService<User> {
        User insertUser(User user);

        User insertUser(Recruit recruit);

        MyPageInfo<User> getUsers();

        User getUserByStuid(String stuid);

        User getUserLogin(String username);

        Map<String,Object> getUserByContent(Condition condition, int pageNo, int pageSize);

        Boolean updateMember(User user);

        void getXSSFWorkbook(HttpServletResponse response, Condition condition, int pageNo, int pageSize) throws IOException;

        void updateMemberPwd(String uid, String oldPwd, String newPwd);

        void updateMemberByMe(String key, User user);

        /**
         * 学号对应的成员是否存在
         * @param stuid
         */
        void isExistMember(Integer stuid);
}
