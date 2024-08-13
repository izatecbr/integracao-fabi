package iza.integracao.integration;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iza.integracao.model.asaas.*;
import iza.integracao.model.BoletoConfiguracaoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class BoletoIntegrador {
    private RestTemplate template = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();
    @Value("${asaas.url}")
    private String asaasUrl;
    //@Value("${asaas.token}")
    //private String asaasToken;
    public BoletoIntegracaoCadastro cadastrar(BoletoConfiguracaoEntity configuracao, BoletoIntegracaoCadastro cadastro){
        HttpEntity<BoletoIntegracaoCadastro> entity = new HttpEntity<BoletoIntegracaoCadastro>(cadastro,headers(configuracao.getTokenIntegracao()));
        String url =asaasUrl+"/customers";
        try {
            ResponseEntity<BoletoIntegracaoCadastro> response = template.exchange(url, HttpMethod.POST, entity, BoletoIntegracaoCadastro.class);
            System.out.println(response.getBody());
            desativarNotificacoes(configuracao, response.getBody().getId());
            return response.getBody();
        }catch (Exception ex){
            log.error("#BOLETO_CADASTRO Não possível gerar o cadastro para o cpf {}", cadastro.getCpfCnpj(), ex );
            throw new RuntimeException("Não foi possível integrar o cpf " + cadastro.getCpfCnpj() + " ao meio de pagamento");
        }
    }
    public BoletoIntegracaoResponse gerarBoleto(BoletoConfiguracaoEntity configuracao, BoletoIntegracaoRequest boleto){
        try{
            HttpEntity<BoletoIntegracaoRequest> entity = new HttpEntity<BoletoIntegracaoRequest>(boleto,headers(configuracao.getTokenIntegracao()));
            String url = asaasUrl +"/payments";
            ResponseEntity<BoletoIntegracaoResponse> response = template.exchange(url, HttpMethod.POST, entity, BoletoIntegracaoResponse.class);;
            return response.getBody();
        }catch (HttpClientErrorException httpex) {
            String message="";
            try {
                JsonNode node = mapper.readTree(httpex.getResponseBodyAsString());
                String error = mapper.writeValueAsString(node);
                System.out.println(error);
                throw new RuntimeException(message);
            } catch (JacksonException e) {
                throw new RuntimeException();
            }

        }catch (Exception httpex){
            throw new RuntimeException();
        }
    }
    private void desativarNotificacoes(BoletoConfiguracaoEntity configuracao, String clienteCodigoIntegracao){
        try{
            List<String> notificacoes = listarNotificacoes(configuracao, clienteCodigoIntegracao);
            AsaasNotificacaoLote lote = new AsaasNotificacaoLote();
            lote.setCustomer(clienteCodigoIntegracao);
            for(String id: notificacoes){
                lote.getNotifications().add(new AsaasNotificacao(id));
            }
            HttpEntity<AsaasNotificacaoLote> entity = new HttpEntity<>(lote,headers(configuracao.getTokenIntegracao()));
            String url = asaasUrl+"/notifications/batch";
            ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }
    private List<String> listarNotificacoes(BoletoConfiguracaoEntity configuracao, String clienteCodigoIntegracao){
        try{
            List<String> ids = new ArrayList<>();
            HttpEntity<String> entity = new HttpEntity<String>(headers(configuracao.getTokenIntegracao()));
            String url = asaasUrl +"/customers/"+clienteCodigoIntegracao+"/notifications";
            ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            Iterator<JsonNode> notificacoes = root.get("data").iterator();
            while (notificacoes.hasNext()){
                ids.add(notificacoes.next().get("id").asText());
            }
            return ids;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }
    private HttpHeaders headers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("access_token",token );
        return headers;
    }
}
