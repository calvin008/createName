package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author: create by calvin wong
 * @date:2020/3/9
 **/

@Data
@TableName("order_create")
public class OrderCreate {
    /**
     * id
     */
    private String orderId;
    /**
     * 姓氏
     */
    private String surname;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private Date birthDay;
    /**
     * 出生时间
     */
    private Timestamp birthTime;
    /**
     * 创建时间
     */
    private  Timestamp createTime;
}

