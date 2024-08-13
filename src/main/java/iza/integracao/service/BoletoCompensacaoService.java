package iza.integracao.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iza.integracao.model.BoletoEntity;
import iza.integracao.model.embedded.BoletoCompensacao;
import iza.integracao.repository.BoletoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class BoletoCompensacaoService {
    @Autowired
    private BoletoRepository repository;
    public void compensarAsaasBoleto(Object payload){
        log.info("Iniciando a compensação do boleto ... ");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(payload, JsonNode.class);
        if (node.get("event").asText().equals("PAYMENT_RECEIVED")) {
            JsonNode payment = node.get("payment");
            String status = payment.get("status").asText();
            String numeroIdentificacao = payment.get("externalReference").asText();
            if("RECEIVED".equals(status)){
                log.info("Boleto de identificacao {} localizado com status {} - Iniciando a compensação", numeroIdentificacao, status);
                BoletoEntity boleto = repository.findByIntegracaoNumeroIdentificacao(numeroIdentificacao);
                if(boleto!=null){
                    /*
                    if(boleto.getIntegracao().getStatusRetorno().equals(status)){
                        throw new IllegalArgumentException("O boleto já foi compensado anteriormente");
                    }
                    */
                    boleto.getIntegracao().setStatusMensagem("Boleto compensado com sucesso");
                    boleto.getIntegracao().setStatusRetorno(status);
                    BoletoCompensacao compensacao = new BoletoCompensacao();
                    compensacao.setDataPagamento(LocalDate.parse(payment.get("confirmedDate").asText()));
                    boleto.setCompensacao(compensacao);
                    repository.save(boleto);
                } else {
                    log.info("Boleto de identificacao {} - NÃO LOCALIZADO NA BASE DE DADOS", numeroIdentificacao);
                }
            }

        }
    }
}
