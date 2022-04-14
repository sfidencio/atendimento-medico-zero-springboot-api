package br.com.atendimento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.atendimento.model.Situacao;
import br.com.atendimento.repository.SituacaoRepository;

@Service
public class SituacaoService {

    @Autowired
    private SituacaoRepository situacaoRepository;

    public Optional<Situacao> obterPorId(Long id) {
        return situacaoRepository.findById(id);
    }

    public List<Situacao> obterTodas() {
        return situacaoRepository.findAll();
    }

}
