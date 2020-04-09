package top.weimumu.loginapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.weimumu.loginapi.entity.User;
import top.weimumu.loginapi.mapper.UserMapper;
import top.weimumu.loginapi.service.IUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
