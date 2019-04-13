package top.wanmudong.oamly;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.junit.Test;

import top.wanmudong.oamly.modules.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanmudong
 * @date 18:55 2018/12/11
 */
public class MybatisPlusTest extends BaseMvcTest {

    @Test
    public void test1(){
        List list = new ArrayList();
        list.add("神将");
        list.add("罗刹");
        list.add("光头");

        list.forEach(item -> System.out.println(item));
        list.forEach(System.out::println);

    }
    @Test
    public void testTSQL1() {
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

//    @Test
//    public void testLambda(){
//        Predicate<String> predicate = (String s) -> s.equals("test");
//    }

}
