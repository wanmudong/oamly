package top.wanmudong.oamly.modules.common.Enum;

/**
 * Created by chenjiehao on 2018/11/15
 */
public enum OrderExceptionEnum {

    /**
     * 0 未知
     * 2 用户问题
     * 3 查询内容问题
     */
    /** 未知异常 */
    UNKNOWN_EXCEPTION("000","未知异常","warn"),
    /** 系统错误 */
    SYSTEM_ERROR("OE002","系统错误","error"),
    /** 用户未登录 */
    SYSUSER_ERROR("201","用户未登录","error"),
    /** 查询不存在错误 */
    THIS_CONTENT_NOT_FOUND_ERROR("301","该条记录不存在","error")
    ;

    private String errorCode;
    private String errorMsg;
    private String errorType;


    OrderExceptionEnum(String errorCode, String errorMsg, String errorType) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
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
     * Getter method for property <tt>errorMsg</tt>.
     *
     * @return property value of errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter method for property <tt>errorMsg</tt>.
     *
     * @param errorMsg value to be assigned to property errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Getter method for property <tt>errorType</tt>.
     *
     * @return property value of errorType
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Setter method for property <tt>errorType</tt>.
     *
     * @param errorType value to be assigned to property errorType
     */
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}

