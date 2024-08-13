package iza.integracao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tab_boleto_config")
@Data
public class BoletoConfiguracaoEntity {
    @Id
    @Column(name = "cod_empresa")
    private Integer codigoEmpresa;
    @Column(name = "vl_custo_emissao")
    private Double custoEmissao;
    @Column(name = "token_integracao")
    private String tokenIntegracao;
    @Column(name = "token_atualizacao")
    private String tokenAtualizacao;
}
