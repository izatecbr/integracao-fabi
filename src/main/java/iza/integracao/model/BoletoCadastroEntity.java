package iza.integracao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tab_boleto_cadastro")
@Data
public class BoletoCadastroEntity {
    @Id
    private Integer id;
    private String idExterno;
    private String cpfCnpj;
    private String nome;
    private String email;
    @Column(name = "vl_custo_emissao")
    private Double custoEmissao;
}
