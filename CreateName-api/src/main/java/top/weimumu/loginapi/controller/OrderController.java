package top.weimumu.loginapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.weimumu.loginapi.common.GlobalResult;
import top.weimumu.loginapi.dao.OrderMasterMapper;
import top.weimumu.loginapi.form.OrderForm;

import javax.validation.Valid;

/**
 * @author: create by calvin wong
 * @date:2020/3/25
 **/

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @PostMapping("/create")
    public GlobalResult create(@Valid OrderForm orderForm,
                                BindingResult bindingResult
    ){
        if (bindingResult.hasErrors())
    }
}
