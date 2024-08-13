package iza.integracao.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class BoletoCompensacao {
    @Column(name = "comp_dt_pagto")
    private LocalDate dataPagamento;
    @Column(name = "comp_baixa_realizada")
    private boolean baixaRealizada;
}
