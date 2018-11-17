package top.wanmudong.oamly;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.wanmudong.oamly.modules.user.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OamlyApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testTSQL11() {
        /*
         * 实体带查询使用方法  输出看结果
         */
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.setEntity(new User());
        ew.where("user_name={0}", "'zhangsan'").and("id=1")
                .orNew("user_status={0}", "0").or("status=1")
                .notLike("user_nickname", "notvalue")
                .andNew("new=xx").like("hhh", "ddd")
                .andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
                .groupBy("x1").groupBy("x2,x3")
                .having("x1=11").having("x3=433")
                .orderBy("dd").orderBy("d1,d2");
        System.out.println(ew.getSqlSegment());
    }

}
