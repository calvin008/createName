package top.weimumu.loginapi.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author: create by calvin wong
 * @date:2020/3/25
 **/

@Data
public class OrderForm {
    /** 用户微信open_id*/
    @NotEmpty(message = "open_id不能为空")
    private  String open_id;
    /** 姓氏*/
    @NotEmpty(message="姓氏不能为空")
    private String surname;
    /** 性别*/
    @NotEmpty(message = "性别不能为空")
    private  Integer gender;
    /** 出生日期*/
    @NotEmpty(message = "出生时间不能为空")
    private Date birth_time;
}
