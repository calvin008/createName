package top.weimumu.loginapi.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
public class NameInfo  {

    private static final long serialVersionUID = 1L;

    private String nameId;

    /**
     * 姓氏
     */
    private String surname;

    /**
     * 名字
     */
    private String ming;

    /**
     * 性别 1男 2女
     */
    private Integer gender;

    /**
     * 名字评分
     */
    private BigDecimal score;

    /**
     * 八字id
     */
    private Integer baiziId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
