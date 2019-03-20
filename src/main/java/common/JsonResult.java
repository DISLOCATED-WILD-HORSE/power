package common;

import java.io.Serializable;

public class JsonResult{
    private int code;
    private String message;
    private Object data;

    private JsonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static JsonResult success(String message){
        return new JsonResult(Const.success,message);
    }

    public static JsonResult error(String message){
        return new JsonResult(Const.error,message);
    }

    public static JsonResult success(String message, Object data){
        return new JsonResult(Const.success,message,data);
    }

    public static JsonResult error(String message, Object data){
        return new JsonResult(Const.error,message,data);
    }
}
