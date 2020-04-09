package top.weimumu.loginapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * <p>
 * 八字表
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Data
@Accessors(chain = true)
public class BaziInfo {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bazi_id", type = IdType.AUTO)
    private Integer baziId;

    /**
     * 八字类型
     */
    private String baziType;

    /**
     * 喜用神信息
     */
    private String baziContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
