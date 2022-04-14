package br.com.atendimento.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.atendimento.model.Atendimento;
import br.com.atendimento.model.Paciente;
import br.com.atendimento.model.Situacao;
import br.com.atendimento.repository.SituacaoRepository;
import br.com.atendimento.service.AtendimentoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;
    @Autowired
    private SituacaoRepository situacaoRepository; //Criar repository direto no controller pois trata de um Bean simples..


    /**
     * Incluir Atendimento.
     *
     * @return Atendiemnto
     */
    @PutMapping("/atendimentos/encerramento/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String encerrar(@Valid @RequestBody Paciente paciente, @PathVariable(value = "id") Long atendimentoId) {
        Atendimento atendimento = this.atendimentoService.encerrar(paciente,atendimentoId);
        return "ID do atendimento : " + atendimento.getId().toString() + " tempo duracao: " + atendimento.getDuracao().toString();
    }

    /**
     * Incluir Atendimento.
     *
     * @return Atendiemnto
     */
    @PostMapping("/atendimentos")
    @ResponseStatus(HttpStatus.CREATED)
    public String incluir(@Valid @RequestBody Atendimento atendimento) {
        log.info(String.valueOf(atendimento));
        return "ID do atendimento : " + this.atendimentoService.incluir(atendimento).getId().toString();
    }

    /**
     * Obter Atendimentos.
     *
     * @return Atendimentos
     */
    @GetMapping("/atendimentos")
    @ResponseStatus(HttpStatus.OK)
    public List<Atendimento> obterTodosAtendimentos() {
        return this.atendimentoService.obterTodosAtendimentos();
    }


    /**
     * Buscar Paciente pelo Id
     *
     * @param id
     * @return Paciente
     */
    @GetMapping("/atendimentos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Atendimento obterPacientePorId(@PathVariable(value = "id") Long id) {
        return this.atendimentoService.obterPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendimento não encotrado para o id=> " + id));
    }


    //Criar alguns pacientes
    @Bean
    public CommandLineRunner executarBeanSituacao() {
        return args -> {
            if (this.situacaoRepository.count() == 0) {
                //Popular situaçoes para termos dados para fazer o atendimento
                Situacao s1 = new Situacao();
                s1.setDescricao("Grave");
                this.situacaoRepository.save(s1);

                Situacao s2 = new Situacao();
                s2.setDescricao("Moderado");
                this.situacaoRepository.save(s2);


                Situacao s3 = new Situacao();
                s3.setDescricao("Estavel");
                this.situacaoRepository.save(s3);

            }
        };
    }

}
