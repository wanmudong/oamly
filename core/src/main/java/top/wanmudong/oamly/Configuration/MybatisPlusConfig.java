package top.wanmudong.oamly.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wanmudong
 * @date 14:56 2018/12/12
 */
@EnableTransactionManagement
@Configuration
@MapperScan("top.wanmudong.oamly.modules.*.mapper.*Mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}