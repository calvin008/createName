package top.weimumu.loginapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.weimumu.loginapi.entity.NameDetail;

import java.util.Date;
import java.util.List;

/**
 * @author: create by calvin wong
 * @date:2020/3/24
 **/
@Data
public class OrderDTO {
    /** order_id */
    private String order_id;

    /** 姓氏*/
    private String surname;

    /** 性别 */
    private Integer sex;

    /** 出生日期 */
    private Date birth_time;

    /** detail_id名字id*/
    private String detail_id;

    /** 用户身份识别open_id*/
    private String open_id;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    List<NameDetail> orderDetailList;
}
