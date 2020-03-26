package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.sql.Timestamp;

/**
 * @author: create by calvin wong
 * @date:2020/3/20
 **/
@Data
@TableName("order_master")
public class OrderMaster {
    /**
     * 主键
     */
    @TableId
    private String orderId;

    /**
     * 姓氏
     */
    private String surname;

    /**
     *
     * 出生时间
     */
    private Timestamp birth_time;

    /**
     * 名字详情id
     */
    private String detail_id;

    /**
     * 用户open_id
     */
    private String open_id;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private  Timestamp updateTime;

}
