package top.wanmudong.oamly.modules.common.utils.shiro.tool;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author wanmudong
 * @date 11:59 2019/4/13
 */
@Component
public class ShiroSessionRedisDAO extends CachingSessionDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisCache redisCache  ;



    @Override
    protected void doUpdate(Session session) {
        this.saveSession(session);
    }
    /**
     * 将session 保存进redis 中
     * @param session 要保存的session
     */
    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return ;
        }
        redisCache.put(session.getId(),session);

    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return ;
        }
        //根据session id删除session
        redisCache.evict(session.getId());

    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("传入的 session id is null");
            return null;
        }
        return redisCache.get(sessionId,Session.class);
    }
}
