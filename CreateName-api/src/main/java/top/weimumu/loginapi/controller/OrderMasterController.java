package top.weimumu.loginapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.weimumu.loginapi.VO.ResultVO;
import top.weimumu.loginapi.common.ResultVOUtils;
import top.weimumu.loginapi.converter.OrderForm2OrderDTOConverter;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.enums.ResultEnum;
import top.weimumu.loginapi.exception.NameException;
import top.weimumu.loginapi.form.OrderForm;
import top.weimumu.loginapi.service.IOrderMasterService;
import top.weimumu.loginapi.service.IUserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author calvin
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderMasterController{

    @Autowired
    private IOrderMasterService iOrderMasterService;

    @Autowired
    private IUserService iUserService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        //校验参数
        if (bindingResult.hasErrors()) {
            log.error("【订单生成】参数不正确，orderForm={}",orderForm);
            throw new NameException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        OrderDTO createResult = iOrderMasterService.saveInfo(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrder_id());

        return ResultVOUtils.success(map);
    }
}
