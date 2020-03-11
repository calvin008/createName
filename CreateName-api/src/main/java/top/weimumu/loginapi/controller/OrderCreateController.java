package top.weimumu.loginapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.weimumu.loginapi.common.GlobalResult;
import top.weimumu.loginapi.dao.OrderCreateMapper;
import top.weimumu.loginapi.entity.OrderCreate;

import java.util.UUID;

/**
 * @author: create by calvin wong
 * @date:2020/3/9
 **/
@RestController
public class OrderCreateController {

    @Autowired
    private OrderCreateMapper orderCreateMapper;

    @PostMapping("/formSubmit")
    public GlobalResult formSubmit (
            @RequestParam(value = "rawData", required = false) String rawData
    ){
        OrderCreate orderCreate = new OrderCreate();
        JSONObject rawDataJson = JSON.parseObject(rawData);
        String orderId = UUID.randomUUID().toString();


        return result;
    }


}
