package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: create by calvin wong
 * @date:2020/3/9
 **/
@Data
@TableName("name")
public class Name {
    /**
     * id
     */
    private Integer id;
    /**
     * 姓氏
     */
    private String xing;
    /**
     * 名字
     */
    private String ming;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 分数
     */
    private Float score;
    /**
     * 使用状态
     */
    private Integer active;
}
