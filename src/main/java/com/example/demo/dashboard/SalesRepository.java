package com.example.demo.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.order.Order;

@Repository
public interface SalesRepository extends JpaRepository<Order, Long> {
	@Query(value = """
	        SELECT to_char(order_date, 'YYYY-MM') AS month,
	               SUM(total_price) AS total
	        FROM orders
	        GROUP BY month
	        ORDER BY month
	        """,
	        nativeQuery = true)
	    List<Object[]> getMonthlySalesRaw();
}
