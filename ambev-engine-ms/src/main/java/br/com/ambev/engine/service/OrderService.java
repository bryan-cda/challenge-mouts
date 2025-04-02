package br.com.ambev.engine.service;

import br.com.ambev.engine.domain.Orders;
import br.com.ambev.engine.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Orders> listOrders() {
        return orderRepository.findAll();
    }

    public void createOrder(Orders order) {
        Orders save = orderRepository.save(order);
    }
}
