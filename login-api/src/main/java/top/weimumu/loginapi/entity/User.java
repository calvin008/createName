package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String openid;
    /**
     * 用户头像
     */
    private String avatorUrl;
    /**
     * 用户网名
     */
    private String nickname;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 最后登录时间
     */
    @TableField("last_visit_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastVisitTime;

}
