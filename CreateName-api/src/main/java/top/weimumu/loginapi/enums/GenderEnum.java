package top.weimumu.loginapi.enums;

import lombok.Getter;

/**
 * @author: create by calvin wong
 * @date:2020/4/2
 **/

@Getter
public enum GenderEnum {
    BOY(1,"男孩"),
    GIRL(2,"女孩"),
    ;

    private Integer code;

    private String message;

    GenderEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
