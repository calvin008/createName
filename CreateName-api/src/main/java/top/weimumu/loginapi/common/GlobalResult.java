package top.weimumu.loginapi.common;


/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/

public class GlobalResult {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private String ok;	// 不使用

    public static GlobalResult build(Integer status, String msg, Object data) {
        return new GlobalResult(status, msg, data);
    }

    public static GlobalResult ok(Object data) {
        return new GlobalResult(data);
    }

    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    public static GlobalResult errorMsg(String msg) {
        return new GlobalResult(500, msg, null);
    }

    public static GlobalResult errorMap(Object data) {
        return new GlobalResult(501, "error", data);
    }

    public static GlobalResult errorTokenMsg(String msg) {
        return new GlobalResult(502, msg, null);
    }

    public static GlobalResult errorException(String msg) {
        return new GlobalResult(555, msg, null);
    }

    public GlobalResult() {

    }

    public GlobalResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}
