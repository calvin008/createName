package top.weimumu.loginapi.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
public class User{

    private static final long serialVersionUID = 1L;

    private String openId;

    private String sessionKey;

    private LocalDateTime lastVisitTime;

    private String avatarUrl;

    private String skey;

    private String nickName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
