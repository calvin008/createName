package top.weimumu.loginapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.entity.OrderMaster;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
public interface IOrderMasterService extends IService<OrderMaster> {
    /** 创建订单*/
    OrderDTO saveInfo(OrderDTO orderDTO);
    /** 删除订单*/
    OrderDTO removeInfo(OrderDTO orderDTO);
    /** 查询订单*/
    List<OrderDTO> getList(String openid);


}
