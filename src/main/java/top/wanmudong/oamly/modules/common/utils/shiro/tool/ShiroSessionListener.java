package top.wanmudong.oamly.modules.common.utils.shiro.tool;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenjiehao on 2018/12/1
 */
public class ShiroSessionListener implements SessionListener {
    //其维护着一个原子类型的Integer对象，用于统计在线Session的数量。
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
