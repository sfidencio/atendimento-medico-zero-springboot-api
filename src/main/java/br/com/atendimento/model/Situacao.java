package br.com.atendimento.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "situacao")
public class Situacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "descricao",nullable = false, length = 50)
    @NotEmpty(message = "O Campo descricao é obrigatório.")
    private String descricao;

    @OneToMany(mappedBy = "situacao",targetEntity = Atendimento.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Atendimento> atendimentos;


}
