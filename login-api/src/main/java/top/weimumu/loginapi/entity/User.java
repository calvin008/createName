package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author: create by calvin wong
 * @date:2019/12/10
 **/

@Data
@TableName("user")
public class User {
    /**
     * openid
     */
    @TableId(value = "open_id",type = IdType.INPUT)
    private String openId;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 用户网名
     */
    private String nickName;
    /**
     * session_key
     */
    private String session_key;
    /**
     * skey
     */
    private String skey;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 最后登录时间
     */
    @TableField("last_visit_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastVisitTime;

}
