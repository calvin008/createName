package top.weimumu.loginapi.VO;

import lombok.Data;

/**
 * @author: create by calvin wong
 * @date:2020/3/31
 **/
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}