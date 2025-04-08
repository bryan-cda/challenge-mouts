package br.com.ambev.engine.mapper;

import br.com.ambev.engine.dto.CreateOrderRequestDTO;
import br.com.ambev.engine.dto.CreateOrderResponseDTO;
import br.com.ambev.engine.dto.FindOrderByCodeResponseDTO;
import br.com.ambev.engine.entity.Order;

public class OrderMapper {

   public static Order mapToProductOrder(CreateOrderRequestDTO createOrderRequestDTO){
        return Order
                .builder()
                .name(createOrderRequestDTO.getName())
                .cnpj(createOrderRequestDTO.getCnpj())
                .email(createOrderRequestDTO.getEmail())
                .product(createOrderRequestDTO.getProduct())
                .quantity(createOrderRequestDTO.getQuantity())
                .build();

    }

    public static CreateOrderResponseDTO mapToCreateOrderResponseDTO(Order order){
     return CreateOrderResponseDTO
               .builder()
               .code(order.getCode())
               .name(order.getName())
               .cnpj(order.getCnpj())
               .email(order.getEmail())
               .product(order.getProduct())
               .quantity(order.getQuantity())
               .build();
    }

    public static FindOrderByCodeResponseDTO mapToFindOrderByCodeRequestDTO(Order order){
       return FindOrderByCodeResponseDTO
               .builder()
               .name(order.getName())
               .cnpj(order.getCnpj())
               .email(order.getEmail())
               .product(order.getProduct())
               .quantity(order.getQuantity())
               .build();
    }
}
