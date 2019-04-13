package top.wanmudong.oamly.Configuration;



import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author wanmudong
 * @date 22:33 2019/1/7
 *
 * http://{root-path}/swagger-ui.html
 *
 */
@SuppressWarnings({"unused"})
@Configuration @EnableSwagger2
public class Swagger2Config {


    @Value("${swagger2.enable}")
    private boolean enable = true;

    @Bean("AnnounceApis")
    public Docket announceApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通知模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/api/announce.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
    @Bean("UserApis")
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("(/api/dict.*)|(/api/member.*)|(/api/me.*)|(/api/getUserByStuid)|(/api/recruit.*)"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("明理院OA系统平台接口文档")
                .description("明理院技术部提供技术支持")
                .version("1.0")
                .contact(new Contact("chenjiehao","https://github.com/wanmudong/oamly","wanmudong@qq.com"))
                .build();
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
