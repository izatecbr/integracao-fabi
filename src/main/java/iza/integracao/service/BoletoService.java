package iza.integracao.service;

import iza.integracao.integration.BoletoIntegrador;
import iza.integracao.model.BoletoCadastroEntity;
import iza.integracao.model.BoletoConfiguracaoEntity;
import iza.integracao.model.BoletoEntity;
import iza.integracao.model.asaas.BoletoIntegracaoCadastro;
import iza.integracao.model.dto.BoletoRequest;
import iza.integracao.model.dto.BoletoRequestDetalhe;
import iza.integracao.model.dto.BoletoRequestPagador;
import iza.integracao.model.embedded.BoletoIntegracao;
import iza.integracao.model.asaas.BoletoIntegracaoRequest;
import iza.integracao.model.asaas.BoletoIntegracaoResponse;
import iza.integracao.repository.BoletoCadastroRepository;
import iza.integracao.repository.BoletoConfiguracaoRepository;
import iza.integracao.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BoletoService {
    @Autowired
    private BoletoRepository repository;
    @Autowired
    private BoletoConfiguracaoRepository configuracaoRepository;
    @Autowired
    private BoletoIntegrador integrador;
    @Autowired
    private BoletoCadastroRepository cadastroRepository;

    @Transactional
    public void integrarBoleto (BoletoRequest requisicao){

        BoletoRequestDetalhe detalhe = requisicao.getDetalhe();
        BoletoConfiguracaoEntity configuracao = configuracaoRepository.findById(requisicao.getEmpresa()).orElse(null);
        BoletoEntity entity = new BoletoEntity();

        BoletoCadastroEntity cadastro = integrarCadastro(configuracao, requisicao.getPagador());
        entity.setCodigoCliente(cadastro.getId().toString());
        entity.setCustoImpressao(cadastro.getCustoEmissao());

        entity.setNumeroDocumento(detalhe.getNumeroDocumento());
        entity.setCodigoTitulo(detalhe.getCodigoTitulo());
        entity.setCodigoTitulo(detalhe.getCodigoTitulo());
        entity.setDataEmissao(LocalDate.now());
        entity.setValorOriginal(detalhe.getValor());
        entity.setValorFinal(detalhe.getValor() + configuracao.getCustoEmissao());
        entity.setDataVencimento(detalhe.getDataVencimento());
        entity.setDescricao(detalhe.getDescricao());


        try {
            BoletoIntegracao integracao = new BoletoIntegracao();
            integracao.setNumeroIdentificacao(UUID.randomUUID().toString());
            entity.setIntegracao(integracao);

            BoletoIntegracaoRequest internalRequest = criarPayload(cadastro.getIdExterno(), entity);
            BoletoIntegracaoResponse response = integrador.gerarBoleto(configuracao, internalRequest);
            integracao.setLinkPagamento(response.getBankSlipUrl());
            integracao.setNossoNumero(response.getNossoNumero());
            integracao.setStatusRetorno(response.getStatus());
            integracao.setNumeroProtocolo(response.getId());
            integracao.setValorLiquido(response.getNetValue());

        }catch (Exception exception){
            exception.printStackTrace();
        }

        repository.save(entity);
    }
    private BoletoCadastroEntity integrarCadastro(BoletoConfiguracaoEntity configuracao, BoletoRequestPagador pagador){
        BoletoCadastroEntity entity = cadastroRepository.findById(pagador.getCodigoCliente()).orElse(null);
        if(entity==null){
            entity = new BoletoCadastroEntity();
            entity.setId(pagador.getCodigoCliente());
            entity.setNome(pagador.getNome());
            entity.setEmail(pagador.getEmail());
            entity.setCpfCnpj(pagador.getCpfCnpj());
            entity.setCustoEmissao(configuracao.getCustoEmissao());

            BoletoIntegracaoCadastro cadastro = new BoletoIntegracaoCadastro();
            cadastro.setEmail(entity.getEmail());
            cadastro.setName(entity.getNome());
            cadastro.setCpfCnpj(entity.getCpfCnpj());
            BoletoIntegracaoCadastro response = integrador.cadastrar(configuracao,cadastro);
            entity.setIdExterno(response.getId());
            cadastroRepository.save(entity);
        }
        return entity;
    }

    private BoletoIntegracaoRequest criarPayload(String customer, BoletoEntity entity){
        BoletoIntegracaoRequest boleto = new BoletoIntegracaoRequest();
        boleto.setExternalReference(entity.getIntegracao().getNumeroIdentificacao());
        boleto.setValue(entity.getValorFinal());
        boleto.setDescription(entity.getDescricao());
        boleto.setCustomer(customer);
        boleto.setDueDate(entity.getDataVencimento().toString());
        return boleto;
    }
}
