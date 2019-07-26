package cn.tag.exception;

public class GlobalException extends RuntimeException {

    private Integer code;
    private String errMessage;

    public GlobalException(Integer code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
