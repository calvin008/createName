package top.weimumu.loginapi.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
public class OrderMaster {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private LocalDateTime birthTime;

    private String surname;

    private String detailId;

    private String openId;

    private Integer gender;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
