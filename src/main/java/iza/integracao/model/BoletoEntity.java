package iza.integracao.model;

import iza.integracao.model.embedded.BoletoCompensacao;
import iza.integracao.model.embedded.BoletoIntegracao;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tab_boleto")
@Data
public class BoletoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cod_cliente")
    private String codigoCliente;
    //id do titulo na base
    @Column(name = "cod_titulo")
    private String codigoTitulo;
    @Column(name = "num_documento")
    private String numeroDocumento;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "dt_emissao")
    private LocalDate dataEmissao;
    @Column(name = "dt_vencimento")
    private LocalDate dataVencimento;
    @Column(name = "vl_original")
    private Double valorOriginal;
    @Column(name = "vl_custo_impressao")
    private Double custoImpressao;
    @Column(name = "vl_final")
    private Double valorFinal;
    @Embedded
    private BoletoIntegracao integracao;
    @Embedded
    private BoletoCompensacao compensacao;
    private Boolean substituido;

}
