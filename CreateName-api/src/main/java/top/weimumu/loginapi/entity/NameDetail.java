package top.weimumu.loginapi.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 名字推荐表
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
public class NameDetail{

    private static final long serialVersionUID = 1L;

    private String detailId;

    /**
     * 名字
     */
    private String ming;

    /**
     * 名字id
     */
    private String nameId;

    /**
     * 八字id
     */
    private Integer baziId;

    /**
     * 姓氏
     */
    private String surname;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 八字信息
     */
    private String baziContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
