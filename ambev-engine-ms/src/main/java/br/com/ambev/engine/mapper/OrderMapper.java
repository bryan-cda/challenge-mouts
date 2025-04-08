package br.com.ambev.engine.mapper;

import br.com.ambev.engine.dto.CreateOrderRequestDTO;
import br.com.ambev.engine.dto.CreateOrderResponseDTO;
import br.com.ambev.engine.dto.FindOrderByCodeResponseDTO;
import br.com.ambev.engine.entity.Fulfillment;

public class OrderMapper {

   public static Fulfillment mapToProductOrder(CreateOrderRequestDTO createOrderRequestDTO){
        return Fulfillment
                .builder()
                .name(createOrderRequestDTO.getName())
                .cnpj(createOrderRequestDTO.getCnpj())
                .email(createOrderRequestDTO.getEmail())
                .product(createOrderRequestDTO.getProduct())
                .quantity(createOrderRequestDTO.getQuantity())
                .build();

    }

    public static CreateOrderResponseDTO mapToCreateOrderResponseDTO(Fulfillment fulfillment){
     return CreateOrderResponseDTO
               .builder()
               .code(fulfillment.getCode())
               .name(fulfillment.getName())
               .cnpj(fulfillment.getCnpj())
               .email(fulfillment.getEmail())
               .product(fulfillment.getProduct())
               .quantity(fulfillment.getQuantity())
               .build();
    }

    public static FindOrderByCodeResponseDTO mapToFindOrderByCodeRequestDTO(Fulfillment fulfillment){
       return FindOrderByCodeResponseDTO
               .builder()
               .name(fulfillment.getName())
               .cnpj(fulfillment.getCnpj())
               .email(fulfillment.getEmail())
               .product(fulfillment.getProduct())
               .quantity(fulfillment.getQuantity())
               .build();
    }
}
