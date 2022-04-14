package br.com.atendimento.service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.atendimento.model.Paciente;
import br.com.atendimento.model.enumerations.SexoEnum;
import br.com.atendimento.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente incluir(Paciente paciente) throws ParseException {
        if (paciente.getSexo().equals(SexoEnum.F) && Objects.isNull(paciente.getGestante()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve ser preenchido o campo gestante, quando paciente for do sexo FEMININO.");
        return this.pacienteRepository.save(paciente);
    }

    public List<Paciente> obterTodosPacientes() {
        return this.pacienteRepository.findAll();
    }

    public Long count() {
        return this.pacienteRepository.count();
    }

    public Optional<Paciente> obterPorId(Long id) {
        /*CPFFormatter cpf = new CPFFormatter();
        Optional<Paciente>p =  this.pacienteRepository.findById(id);
        if (p.isPresent())
            p.get().setCpf(cpf.format(p.get().getCpf()));
        return p;*/
        return this.pacienteRepository.findById(id);
    }

    public Paciente atualizar(Paciente paciente) {
        if (paciente.getSexo().equals(SexoEnum.F) && Objects.isNull(paciente.getGestante()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve ser preenchido o campo gestante, quando paciente for do sexo FEMININO.");
        return this.pacienteRepository.save(paciente);
    }
}
