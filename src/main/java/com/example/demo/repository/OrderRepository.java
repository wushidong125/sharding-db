package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shidong.wu on 2018/1/27.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {

}
