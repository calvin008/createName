package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: create by calvin wong
 * @date:2020/3/23
 **/
@Data
@TableName("name_info")
public class NameInfo {

    /** 主键id */
    private String name_id;

    /** 姓氏 */
    private String surname;

    /** 名字 */
    private String ming;

    /** 性别*/
    private Integer gender;

    /** 评分 */
    private BigDecimal score;

    /** 八字id*/
    private Integer bazi_id;

   /** 创建时间*/
    private Timestamp createTime;

    /** 修改时间*/
    private  Timestamp updateTime;
}
