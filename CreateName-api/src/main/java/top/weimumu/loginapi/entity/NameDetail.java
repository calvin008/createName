package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: create by calvin wong
 * @date:2020/3/24
 **/

@Data
@TableName("name_detail")
public class NameDetail {
    /** id */
    private String detail_id;

    /** 名字*/
    private String ming;

    /** 名字id号 */
    private String name_id;

    /** 八字id*/
    private Integer bazi_id;

    /** 姓氏*/
    private String surname;

    /** 评分 */
    private BigDecimal score;

    /** 八字信息 */
    private String bazi_content;

    /** 创建时间*/
    private Timestamp createTime;

    /** 修改时间*/
    private  Timestamp updateTime;


}
