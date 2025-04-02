package br.com.ambev.engine.domain;

import br.com.ambev.engine.enumerations.ClientType;
import br.com.ambev.engine.enumerations.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String cnpj;
    private ClientType clientType;
    private ProductType productType;
    private Integer quantity;
}
