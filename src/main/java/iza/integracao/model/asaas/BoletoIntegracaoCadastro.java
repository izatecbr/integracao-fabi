package iza.integracao.model.asaas;

import lombok.Data;

@Data
public class BoletoIntegracaoCadastro {
    private String id;
    private String name;
    private String email;
    private String cpfCnpj;
    private boolean deleted;
}
