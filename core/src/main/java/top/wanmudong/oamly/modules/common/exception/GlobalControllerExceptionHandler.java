package top.wanmudong.oamly.modules.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wanmudong.oamly.modules.common.utils.Result;
import top.wanmudong.redis.exception.RedisLockException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiehao on 2018/10/26
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "/error";

//    /**
//     * shiro抛出的UnauthorizedException 统一返回http状态码401
//     */
//    @ExceptionHandler(value = UnauthorizedException.class)
//    public ResponseEntity defaultErrorHandler() throws Exception{
//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//    }

//    @ExceptionHandler(value = AuthorizationException.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName(DEFAULT_ERROR_VIEW);
//        return mav;
//    }


    /**
     * shiro抛出的AuthorizationException统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        return Result.error(false,"用户验证错误");
        return Result.error().isNotlogin("用户验证失败");
    }

    /**
     * shiro抛出的UnauthorizedException统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result defaultnAuthorizedExceptionErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return Result.error("用户权限不足");
    }

    /**
     * SysUserHere抛出的SysUserException统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = SysUserException.class)
    public Result defaultSysUserErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("用户未登录");
        return Result.error("用户未登录");
    }
    /**
     * 系统抛出的未知异常统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result defaultExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("系统内部错误",e);
        return Result.error("系统内部错误");
    }
    /**
     * 查询内容不存在时的未知异常统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = ContentNotExistException.class)
    public Result defaultContentNotExistExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("[{}]",e.getMessage(),e);
        return Result.error(e.getMessage());
    }
    /**
     * 查询内容存在时的未知异常统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = ContentAlreadyExistException.class)
    public Result defaultContentAlreadyExistExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("[{}]，请勿重复插入",e.getMessage(),e);
        return Result.error(e.getMessage());
    }
    /**
     * Insert或Update数据时违反了完整性，例如违反了惟一性限制时的未知异常统一返回
     */
    @ResponseBody
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Result defaultDataIntegrityViolationExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("数据库插入或更新异常",e);
        return Result.error("数据库插入或更新异常");
    }
    /**
     * 执行sql语句时出现了语法错误
     */
    @ResponseBody
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result defaultBadSqlGrammarExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("SQL语法错误",e);
        return Result.error("SQL语法错误");
    }
    /**
     * 检验参数时参数出现错误
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Result defaultBindExceptionHandler(HttpServletRequest req, BindException e) {

        Map<String, String> messages = new HashMap<>();
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
//            for (ObjectError error : errors) {
//                FieldError fieldError = (FieldError) error;
                ObjectError error = errors.get(0);
                log.error(error.getDefaultMessage());
                return Result.error(error.getDefaultMessage());
//            }
        }
        return Result.error("系统错误");
    }
    /**
     * 拦截redisLock异常
     *
     * @param e redisLock异常
     */
    @ResponseBody
    @ExceptionHandler(RedisLockException.class)
    public Result handleRedisLockException(RedisLockException e) {
        log.error("拦截到redisLock异常:{}", e.getMessage(), e);
        return Result.error("服务器繁忙, 请稍后再试");
    }


}
