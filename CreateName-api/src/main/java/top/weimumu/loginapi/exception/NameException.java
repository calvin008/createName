package top.weimumu.loginapi.exception;

import top.weimumu.loginapi.enums.ResultEnum;

/**
 * @author: create by calvin wong
 * @date:2020/3/27
 **/
public class NameException extends RuntimeException {
    private Integer code;

    public NameException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public NameException(Integer code,String message){
        super(message);
        this.code = code;
    }
}
