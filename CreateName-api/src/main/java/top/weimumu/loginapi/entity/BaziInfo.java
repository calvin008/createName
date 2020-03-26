package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: create by calvin wong
 * @date:2020/3/20
 **/
@Data
@TableName("Bazi_info")
public class BaziInfo {
    /**
     * 主键id
     */
    private Integer baziId;

    /**
     * 八字类型
     */
    private String baziType;

    /**
     * 八字内容
     */
    private String baziContent;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 修改时间
     */
    private  Timestamp updateTime;
}
