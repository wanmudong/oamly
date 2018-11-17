package top.wanmudong.oamly.modules.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wanmudong.oamly.modules.common.utils.Result;

/**
 * Created by chenjiehao on 2018/10/27
 */
@RequestMapping("/api/test")
@RestController
@Slf4j
@EnableAutoConfiguration
public class test {
    @PostMapping("testPost")
    public Result test(String uid){
        System.out.println(uid);
        log.info("获取成功({})",uid);
        return Result.OK();
    }
}
