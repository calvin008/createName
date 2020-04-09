package top.weimumu.loginapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weimumu.loginapi.common.KeyUtil;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.entity.NameDetail;
import top.weimumu.loginapi.entity.NameInfo;
import top.weimumu.loginapi.entity.OrderMaster;
import top.weimumu.loginapi.mapper.OrderMasterMapper;
import top.weimumu.loginapi.service.INameInfoService;
import top.weimumu.loginapi.service.IOrderMasterService;

import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements IOrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private  INameInfoService iNameInfoService;
    @Override
    public OrderDTO saveInfo(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //  查询姓名库中10个名字
        List<NameDetail> nameInfoList= iNameInfoService.findList(orderDTO);
        //  刷选10个数据
        //  将数据入库namedetail
        //  写入订单

        return null;
    }

    @Override
    public OrderDTO removeInfo(OrderDTO orderDTO){
        return  null;
    }

    @Override
    public List<OrderDTO> getList(String openId) {
        return  null;
    }

}
