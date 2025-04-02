package br.com.ambev.engine.dto;

import br.com.ambev.engine.enumerations.ClientType;
import br.com.ambev.engine.enumerations.ProductType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateOrderRequest {
    private String firstName;
    private String lastName;
    private String cpf;
    private String cnpj;
    private ClientType clientType;
    private ProductType productType;
    private Integer quantity;
}
