package com.example.demo.controller;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shidong.wu on 2018/1/27.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public IdGenerator idGenerator;


    @GetMapping("/add")
    public Object add() {
//        for (int i = 0; i < 10; i++) {
//            Order order = new Order();
//            order.setUserId((long) i);
//            order.setOrderId((long) i);
//            orderRepository.save(order);
//        }
//        for (int i = 10; i < 20; i++) {
//            Order order = new Order();
//            order.setUserId((long) i + 1);
//            order.setOrderId((long) i);
//            orderRepository.save(order);
//        }

        Order  order = new Order();
        order.setUserId(1L);
        order.setOrderId(idGenerator.generateId().longValue());
        orderRepository.save(order);

        return "success";
    }



    @RequestMapping("query")
    private Object queryAll() {
        return orderRepository.findAll();
    }
}
