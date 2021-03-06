package top.wanmudong.oamly.modules.common.exception;

import org.apache.ibatis.executor.ErrorContext;
import top.wanmudong.oamly.modules.common.Enum.OrderExceptionEnum;

/**
 * Created by chenjiehao on 2018/11/20
 *
 * 查询的内容不存在
 */
public class ContentNotExistException extends  RuntimeException {

    private static final long serialVersionUID = 1L;


    /** 错误码 */
    private String errorCode;
    /** 错误上下文 */
    private ErrorContext errorContext;


    public ContentNotExistException(String errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }
    public ContentNotExistException(OrderExceptionEnum orderExceptionEnum){
        super(orderExceptionEnum.getErrorMsg());
        this.errorCode = orderExceptionEnum.getErrorCode();
    }

    public ContentNotExistException(String errorCode, String errorMsg,Throwable throwable){
        super(errorMsg,throwable);
        this.errorCode = errorCode;
    }
    public ContentNotExistException(OrderExceptionEnum orderExceptionEnum,Throwable throwable){
        super(orderExceptionEnum.getErrorMsg(),throwable);
        this.errorCode = orderExceptionEnum.getErrorCode();
    }
    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode</tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Getter method for property <tt>errorContext</tt>.
     *
     * @return property value of errorContext
     */
    public ErrorContext getErrorContext() {
        return errorContext;
    }

    /**
     * Setter method for property <tt>errorContext</tt>.
     *
     * @param errorContext value to be assigned to property errorContext
     */
    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }




}
