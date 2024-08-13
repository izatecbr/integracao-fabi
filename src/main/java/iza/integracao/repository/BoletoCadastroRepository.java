package iza.integracao.repository;

import iza.integracao.model.BoletoCadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletoCadastroRepository extends JpaRepository <BoletoCadastroEntity, Integer> {
}
