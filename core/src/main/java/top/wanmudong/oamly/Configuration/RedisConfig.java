package top.wanmudong.oamly.Configuration;

import org.springframework.context.annotation.Configuration;



@Configuration
public class RedisConfig {


    private String host= "localhost:6379";

    private int timeout = 2*60*60*1000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}