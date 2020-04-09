package top.weimumu.loginapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.entity.NameInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
public interface INameInfoService extends IService<NameInfo> {

            /** 根据姓氏和生辰八字查询名字 */
            List<NameInfo> findList(OrderDTO orderDTO);


}
