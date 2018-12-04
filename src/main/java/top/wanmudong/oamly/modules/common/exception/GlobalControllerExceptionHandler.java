package top.wanmudong.oamly.modules.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wanmudong.oamly.modules.common.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

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
        log.error("未知错误",e);
        return Result.error("未知错误");
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

}
