package br.com.atendimento.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.atendimento.model.enumerations.GestanteEnum;
import br.com.atendimento.model.enumerations.SexoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    @NotEmpty(message = "O Campo nome do paciente é obrigatório.")
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11)
    //@Length(max = 50)
    @CPF(message = "CPF inválido.")
    @NotEmpty(message = "O Campo CPF do paciente é obrigatório.")

    private String cpf;

    //@Basic(optional = false)
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datanascimento", nullable = true)
    @NotNull(message = "Data de nascimento é obrigatória.")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "America/Sao_Paulo")
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    //@NotNull(message = "{campo.alteradoem.obrigatorio}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;


    //Enum limita a gravacao da flag respeitando a estrutura do enum..não precisa setar tamanho do campo
    @Column(name = "sexo", nullable = false)
    //@Length(max = 50)
    //@NotEmpty(message = "Sexo do paciente é obrigatório.")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;


    //Enum limita a gravacao da flag respeitando a estrutura do enum..não precisa setar tamanho do campo
    @Column(name = "gestante")
    @Enumerated(EnumType.STRING)
    private GestanteEnum gestante;

    //orphanRemoval = true - excluir registros orfaos sem um pai
    @OneToMany(mappedBy = "paciente", targetEntity = Atendimento.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Atendimento> atendimentos;


}
