package br.com.atendimento.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.atendimento.model.Paciente;
import br.com.atendimento.model.enumerations.GestanteEnum;
import br.com.atendimento.model.enumerations.SexoEnum;
import br.com.atendimento.service.PacienteService;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;


    /**
     * Incluir Paciente.
     *
     * @param paciente
     * @return Paciente
     */
    @PostMapping("/pacientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Paciente incluir(@Valid @RequestBody Paciente paciente) throws ParseException {
        log.info(String.valueOf(paciente));
        return this.pacienteService.incluir(paciente);
    }

    /**
     * Obter Pacientes.
     *
     * @return Paciente
     */
    @GetMapping("/pacientes")
    @ResponseStatus(HttpStatus.OK)
    public List<Paciente> obterTodosPacientes() {
        return this.pacienteService.obterTodosPacientes();
    }


    /**
     * Buscar Paciente pelo Id
     *
     * @param id
     * @return Paciente
     */
    @GetMapping("/pacientes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Paciente obterPacientePorId(@PathVariable(value = "id") Long id) throws ParseException {
        return this.pacienteService.obterPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encotrado para o id=> " + id));
    }

    /**
     * Atualizar usuario.
     *
     * @param pacienteId
     * @param pacienteAtualizar para atualizar
     * @return Devolve o Paciente atualizado
     */
    @PutMapping("/pacientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//padrao para retornos void, mas to devolendo NO_CONTENT
    public Paciente atualizar(
            @PathVariable(value = "id") Long pacienteId, @Valid @RequestBody Paciente pacienteAtualizar) {
        log.info("Alterando paciente existente: " + pacienteAtualizar.toString());
        Paciente paciente = this.pacienteService.obterPorId(pacienteId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado para atualização: id=>" + pacienteId));
        paciente.setNome(pacienteAtualizar.getNome());
        paciente.setDataNascimento(pacienteAtualizar.getDataNascimento());
        paciente.setCpf(pacienteAtualizar.getCpf());
        paciente.setSexo(pacienteAtualizar.getSexo());
        paciente.setGestante(pacienteAtualizar.getGestante());
        return this.pacienteService.atualizar(paciente);
    }


    //Criar alguns pacientes
    @Bean
    public CommandLineRunner executar() {
        return args -> {
            if (this.pacienteService.count() == 0) {
                Paciente p1 = Paciente.builder()
                        .nome("Fulana")
                        .cpf("35523117005") //CPF valido
                        .dataNascimento(LocalDate.now())
                        .sexo(SexoEnum.F)
                        .gestante(GestanteEnum.S) //Fulana de Tal está gestante nesse caso
                        .build();
                this.pacienteService.incluir(p1);


                Paciente p2 = Paciente.builder()
                        .nome("Beltrano")
                        .cpf("25010406004") //CPF valido
                        .dataNascimento(LocalDate.now())
                        .sexo(SexoEnum.M) //Nao precisa informar se esta gestante ou nao...pois é MASCULINO
                        .build();
                this.pacienteService.incluir(p2);
            }
        };
    }

}
