package iza.integracao.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class BoletoIntegracao {
    //codigo interno para ser localizado na integradora
    @Column(name = "int_num_identificacao")
    private String numeroIdentificacao;
    //codigo de protocolo recebido pela integradora
    @Column(name = "int_num_protocolo")
    private String numeroProtocolo;
    //codigo do nosso numero gerado internamente ou recebido pela operadora
    @Column(name = "int_nosso_numero")
    private String nossoNumero;
    @Column(name = "int_link_pagto")
    private String linkPagamento;
    @Column(name = "int_vl_liquido")
    private Double valorLiquido;
    @Column(name = "int_st_retorno")
    private String statusRetorno;
    @Column(name = "int_st_mensagem")
    private String statusMensagem = "Boleto integrado com sucesso";
}
