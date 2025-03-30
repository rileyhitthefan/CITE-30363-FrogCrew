package edu.tcu.cs.frogcrewbackend.system;

 /*
 Defines the schema of the response: encapsulate data prepared by the server side,
 object will be serialized to JSON before sent back to server side
 */

public class Result {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
