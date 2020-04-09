package top.weimumu.loginapi.converter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import top.weimumu.loginapi.dto.OrderDTO;
import top.weimumu.loginapi.entity.NameDetail;
import top.weimumu.loginapi.enums.ResultEnum;
import top.weimumu.loginapi.exception.NameException;
import top.weimumu.loginapi.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author calvin
 * @since 2020-03-30
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOpen_id(orderForm.getOpen_id());
        orderDTO.setSurname(orderForm.getSurname());
        orderDTO.setGender(orderForm.getGender());
        orderDTO.setBirth_time(orderForm.getBirth_time());

        List<NameDetail> orderDetailList = new ArrayList<>();

        orderDTO.setOrderDetailList(orderDetailList);


        return orderDTO;
    }
}
