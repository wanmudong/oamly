package top.wanmudong.oamly;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.wanmudong.oamly.modules.user.entity.Recruit;
import top.wanmudong.oamly.modules.user.entity.User;
import top.wanmudong.oamly.modules.user.service.RecruitService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static top.wanmudong.redis.SpringContextHolder.getContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OamlyApplicationTests  extends BaseMvcTest {

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
    @Test
    public void saveRecord_thread_test() throws InterruptedException {
        RecruitService recordService = getContext().getBean(RecruitService.class);

        int threadNum = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CyclicBarrier barrier = new CyclicBarrier(threadNum);
        CountDownLatch latch=new CountDownLatch(threadNum);
        AtomicInteger count = new AtomicInteger(10);

        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                Recruit query = new Recruit();
                query.setName("douzhanshen");
               query.setSex("男");
               query.setDepart("技术部");
               query.setDepart("屯溪路校区");
               query.setStuid("2016214224");
               query.setPhone("15855152923");
                query.setCollege(String.valueOf(count.getAndIncrement()));

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    //ignore
                }
                try {
                    recordService.insertRecruit(query);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();
    }
    private Date strToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
