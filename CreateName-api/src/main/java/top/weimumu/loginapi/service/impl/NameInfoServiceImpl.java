package top.weimumu.loginapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.entity.NameInfo;
import top.weimumu.loginapi.mapper.NameInfoMapper;
import top.weimumu.loginapi.service.INameInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@Service
public class NameInfoServiceImpl extends ServiceImpl<NameInfoMapper, NameInfo> implements INameInfoService {

    @Autowired
    private NameInfoMapper nameInfoMapper;

    @Override
    public List<NameInfo> findList(OrderDTO orderDTO){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("surname",orderDTO.getSurname());
        columnMap.put("gender", orderDTO.getGender());
        columnMap.put("bazi_id",orderDTO.getBazi_id());
        return nameInfoMapper.selectByMap(columnMap);
    }

}
