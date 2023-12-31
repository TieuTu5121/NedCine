package com.nthao.nedcine.repository;

import com.nthao.nedcine.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    @Query("select order from Order order WHERE order.userId = :userId order by order.createdAt desc ")
    List<Order> findOrdersByUserId(long userId);
    @Override
    @Query("select order from Order order order by order.createdAt desc")
    List<Order> findAll();
}
