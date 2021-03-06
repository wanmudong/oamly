package top.wanmudong.oamly.modules.login.controller;



import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import top.wanmudong.oamly.modules.announce.entity.Announce;
import top.wanmudong.oamly.modules.announce.service.AnnounceService;
import top.wanmudong.oamly.modules.common.entity.SysUser;
import top.wanmudong.oamly.modules.common.utils.Message_oa;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.oamly.modules.login.entity.Login;
import top.wanmudong.oamly.modules.login.service.LoginService;
import top.wanmudong.oamly.modules.user.entity.User;
import top.wanmudong.oamly.modules.user.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanmudong
 * @since 2018-11-12
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("")
@Slf4j
public class LoginController {

    private LoginService loginService;

    @Autowired
    protected LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    private SessionDAO sessionDAO;

    @Resource
    private UserService userService;


    @RequestMapping("/api/login")
    public Result login(@Valid Login login){
        String username = login.getUsername();
        String password = login.getPassword();
        Boolean rememberMe = login.getRememberMe();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token;
        if(rememberMe == null){
             token = new UsernamePasswordToken(username, password);
        }else {
             token = new UsernamePasswordToken(username, password,rememberMe);
        }


        try {

            subject.login(token);
            //踢出当前用户在其他地方的登录
            List<Session> loginedList = getLoginedSession(subject);
            for (Session session : loginedList) {
                session.setTimeout(0);
            }
        } catch (UnknownAccountException uae) {
            log.info("对用户[{}]进行登录验证..验证未通过,未知账户", username);
//            log.info("错误轨迹",uae);
//            result.setSuccess(false);
//            mo.setText("未知账户");
//            result.setMsg(mo);
//            return result;
//            return Result.error(false,"未知账户");
            return Result.error().isNotlogin("未知账户");
        } catch (IncorrectCredentialsException ice) {
            log.info("对用户[{}]进行登录验证..验证未通过,错误的凭证", username);
//            result.setSuccess(false);
//            mo.setText("密码错误");
//            result.setMsg(mo);
//            return result;
            return Result.error().isNotlogin("密码错误");
        } catch (LockedAccountException lae) {
            log.info("对用户[{}]进行登录验证..验证未通过,账户已锁定", username);
//            result.setSuccess(false);
//            mo.setText("账户已锁定");
//            result.setMsg(mo);
//            return result;
            return Result.error().isNotlogin("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.info("对用户[{}]进行登录验证..验证未通过,错误次数过多", username);
//            result.setSuccess(false);
//            mo.setText("错误次数过多");
//            result.setMsg(mo);
//            return result;
            return Result.error().isNotlogin("错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("对用户[{}]进行登录验证..验证未通过,堆栈轨迹如下", username);
            log.info("堆栈轨迹",ae);
//            result.setSuccess(false);
//            mo.setText("登录失败或密码错误");
//            result.setMsg(mo);
//            return result;
            return Result.error().isNotlogin("登录失败或密码错误");
        }
        User user = userService.getUserLogin(username);
//        return Result.OK().put("data",token);
        return Result.OK().put("data",user);
    }


    /**
     * 当用户注销时返回该信息
     * @return
     */
    @GetMapping("/loginOut/msg")
    public Result loginOutMsg(){

        SysUser sysuser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        System.out.println(sysuser);
        return Result.error().isNotlogin("用户已注销");

    }
    /**
     * 用户注销接口
     * @return
     */
    @GetMapping("/api/logout")
    @ResponseBody
    public Result logout(){
        try {
            SecurityUtils.getSubject().logout();
        }catch (NullPointerException e){
            return Result.error().isNotlogin("用户已注销");
        }
        return Result.OK();
    }

    //遍历同一个账户的session
    private List<Session> getLoginedSession(Subject currentUser) {
        Collection<Session> list = sessionDAO
                .getActiveSessions();
        List<Session> loginedList = new ArrayList<Session>();
        SysUser loginUser = (SysUser) currentUser.getPrincipal();
        for (Session session : list) {

            Subject s = new Subject.Builder().session(session).buildSubject();

            if (s.isAuthenticated()) {
                SysUser user = (SysUser) s.getPrincipal();

                if (user.getId().equals(loginUser.getId())) {
                    if (!session.getId().equals(
                            currentUser.getSession().getId())) {
                        loginedList.add(session);
                    }
                }
            }
        }
        return loginedList;
    }


}
