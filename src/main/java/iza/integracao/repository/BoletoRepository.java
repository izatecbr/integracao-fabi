package iza.integracao.repository;

import iza.integracao.model.BoletoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletoRepository extends JpaRepository<BoletoEntity, Integer> {
    BoletoEntity findByIntegracaoNumeroIdentificacao(String numeroIdentificacao);
    BoletoEntity findFirstByCodigoTituloAndSubstituidoFalse(String codigoTitulo);
}
