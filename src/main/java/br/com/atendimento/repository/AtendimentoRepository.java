package br.com.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.atendimento.model.Atendimento;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
 }
