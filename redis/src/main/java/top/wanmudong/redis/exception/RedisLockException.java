package top.wanmudong.redis.exception;

/**
 * @author wanmudong
 * @date 12:08 2018/12/11
 */
public class RedisLockException extends RuntimeException {

    public RedisLockException(String msg){
        super(msg);
    }
}
