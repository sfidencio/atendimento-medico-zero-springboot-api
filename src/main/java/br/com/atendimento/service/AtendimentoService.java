package br.com.atendimento.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.atendimento.model.Atendimento;
import br.com.atendimento.model.Paciente;
import br.com.atendimento.model.Situacao;
import br.com.atendimento.repository.AtendimentoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AtendimentoService {

	@Autowired
	private AtendimentoRepository atendimentoRepository;
	@Autowired
	private PacienteService pacienteService;
	@Autowired
	private SituacaoService situacaoService;

	public Atendimento encerrar(Paciente paciente, Long atendimentoId) {
		Atendimento atendimentoEncontrado = this.atendimentoRepository.findById(atendimentoId).get();
		/*
		 * if (!atendimentoEncontrado.isPresent()) throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND,
		 * "Atendimento informado não encontrado para o Id: " + atendimentoId);
		 */
		/*
		 * if(Objects.isNull(atendimentoEncontrado.get().getDiagnostico())) throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND,
		 * "Diagnóstico é necessário no encerramento do atendimento.");
		 */

		// Gravar dados Paciente Alterado
		this.pacienteService.atualizar(paciente);

		atendimentoEncontrado.setDataHoraEncerramento(LocalDateTime.now());

		Duration duration = Duration.between(atendimentoEncontrado.getDataHoraAtendimento(),
				atendimentoEncontrado.getDataHoraEncerramento());
		// Period p = Period.between(this.getDataHoraAtendimento().toLocalDate(),
		// this.getDataHoraEncerramento().toLocalDate());
		atendimentoEncontrado.setDuracao(duration.toMinutes());
		return this.atendimentoRepository.save(atendimentoEncontrado);

	}

	public Atendimento incluir(Atendimento atendimento) {
		Optional<Paciente> pacienteEncontrado = this.pacienteService.obterPorId(atendimento.getPaciente().getId());
		log.info(pacienteEncontrado.toString());
		if (!pacienteEncontrado.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Paciente informado não encontrado para o Id: " + atendimento.getPaciente().getId());

		Optional<Situacao> situacaoEncontrada = this.situacaoService.obterPorId(atendimento.getSituacao().getId());
		if (!situacaoEncontrada.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Situação do paciente não encontrara para o Id: " + atendimento.getSituacao().getId());

		// CPFFormatter f = new CPFFormatter();
		// Removendo formatacao CPF
		// atendimento.getPaciente().setCpf(f.unformat(atendimento.getPaciente().getCpf()));

		return this.atendimentoRepository.save(atendimento);
	}

	public List<Atendimento> obterTodosAtendimentos() {
		return this.atendimentoRepository.findAll();
	}

	public Long count() {
		return this.atendimentoRepository.count();
	}

	public Optional<Atendimento> obterPorId(Long id) {
		return this.atendimentoRepository.findById(id);
	}

}
