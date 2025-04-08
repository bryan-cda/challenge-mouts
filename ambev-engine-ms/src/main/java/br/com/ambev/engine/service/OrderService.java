package br.com.ambev.engine.service;

import br.com.ambev.engine.dto.CreateOrderRequestDTO;
import br.com.ambev.engine.dto.CreateOrderResponseDTO;
import br.com.ambev.engine.dto.FindOrderByCodeResponseDTO;
import br.com.ambev.engine.entity.Fulfillment;
import br.com.ambev.engine.mapper.OrderMapper;
import br.com.ambev.engine.repository.OrderRepository;
import br.com.ambev.engine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<Fulfillment> listOrders() {
        return orderRepository.findAll();
    }

    public CreateOrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequest) {
        Fulfillment order = OrderMapper.mapToProductOrder(createOrderRequest);
        order.setCode(randomUUID());
        order.getProduct().setCode(randomUUID());
        Fulfillment save = orderRepository.save(order);
        return OrderMapper.mapToCreateOrderResponseDTO(orderRepository.save(save));

    }

    public FindOrderByCodeResponseDTO findByCode(UUID code) {
        Fulfillment byCode = orderRepository.findByCode(code);
        return OrderMapper.mapToFindOrderByCodeRequestDTO(byCode);
    }
}
