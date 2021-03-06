package top.wanmudong.oamly.modules.common.utils;


import io.swagger.annotations.ApiModel;

import java.util.HashMap;

/**
 * @author chenliangliang
 * @date 2018/2/18
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -8044673729630271207L;

    public Result(int success, Message_oa msg, Object data, Condition conditions) {
        this.put("success", success);
        this.put("msg", msg);
        this.put("data",data);
        this.put("conditions",conditions);
    }

    public Result(boolean success, Message_oa msg) {
        this.put("success", success);
        this.put("msg", msg);
    }

    public   Result(boolean b){
        this.put("success",b);
    }

    public static Result error(){
        return new Result(false);
    }

    /**
     * 操作失败，用户已登录
     * @param text
     * @return
     */
    public static Result error(String text){
        return new Result(false).put("msg",Message_oa.isLogin(text));
    }

    /**
     * 操作失败，用户未登录
     * @param b
     * @param text
     * @return
     */
    public static Result error(Boolean b,String text){
        return new Result(false).put("msg",Message_oa.isNotLogin(text));
    }

    /**
     * 用户未登录
     * @param text
     * @return
     */
    public  Result isNotlogin(String text){
        this.put("msg",Message_oa.isNotLogin(text));
        return this;
    }

//    public static Result isLogin(String text){
//        return new Result(false).put("msg",Message_oa.isLogin(text));
//    }
//    public static Result isNotLogin(String text){
//        return new Result(false).put("msg",Message_oa.isNotLogin(text));
//    }

    /**
     * 操作成功
     * @return
     */
    public static Result OK(){
        return   new Result(true);
    }

//    public Result(HttpStatus status){
//        this(status.value(),status.getReasonPhrase());
//    }
//
//   public static Result OK(String msg){
//        return new Result(200,msg);
//   }
//
//   public static Result OK(){
//      return   OK("success");
//   }
//
//   public static Result OK(Map<String,Object> data){
//        Result res=OK();
//        res.putAll(data);
//        return res;
//   }
//
//   public static Result error(HttpStatus status){
//      return new Result(status);
//   }
//
//   public static Result error(HttpStatus status, String msg){
//        return new Result(status.value(),msg);
//   }
//
//    public static Result error(String msg){
//        return new Result(500,msg);
//    }
//
//    public static Result error(){
//        return error("未知异常，请联系管理员");
//    }



    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
