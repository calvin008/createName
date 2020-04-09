package top.weimumu.loginapi.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.weimumu.loginapi.entity.NameDetail;

import java.util.List;

/**
 * <p>
 * 名字推荐表 Mapper 接口
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
public interface NameDetailMapper extends BaseMapper<NameDetail> {
    @Override
    List<NameDetail> selectList(Wrapper<NameDetail> queryWrapper);
}
