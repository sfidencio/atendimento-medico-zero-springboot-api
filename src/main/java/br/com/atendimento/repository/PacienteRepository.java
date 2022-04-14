package br.com.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.atendimento.model.Paciente;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
 }
