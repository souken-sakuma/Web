package com.example.demo.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.User;

public interface OrderRepository  extends JpaRepository<Order, Long>{
	List<Order> findByUserOrderByOrderDateDesc(User user);
	
	List<Order> findAllByOrderByOrderDateDesc();


}
