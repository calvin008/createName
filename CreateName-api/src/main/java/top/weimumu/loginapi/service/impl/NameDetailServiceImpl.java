package top.weimumu.loginapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weimumu.loginapi.entity.NameDetail;
import top.weimumu.loginapi.mapper.NameDetailMapper;
import top.weimumu.loginapi.service.INameDetailService;

/**
 * <p>
 * 名字推荐表 服务实现类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Service
public class NameDetailServiceImpl extends ServiceImpl<NameDetailMapper, NameDetail> implements INameDetailService {

}
