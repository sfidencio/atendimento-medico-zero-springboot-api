package br.com.atendimento.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "atendimento")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    //Este campo por padrao vai receber 255 caracters, padrao da JPA
    @Column(name = "diagnostico")
    @NotEmpty(message = "O Campo diagnóstico do paciente é obrigatório.")
    private String diagnostico;

    @Column(name = "datahoraatend")
    @NotNull(message = "Data de Hora de atendimento  é obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraAtendimento;


    @Column(name = "datahoraencer")
    //@NotNull(message = "Data de Hora de encerramento  é obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraEncerramento=null;


    @Column(name = "duracao")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Long duracao; //Duracao em minutos


    //FK de Paciente. Respeita o relacionamento de composição, não existe um atendimento sem paciente. É obrigatório
    @ManyToOne()
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    //FK de Situacao. Respeita o relacionamento de composição, não existe um atendimento sem especificar uma situação. É obrigatório
    @ManyToOne()
    @JoinColumn(name = "situacao_id", nullable = false)
    private Situacao situacao;

    @PrePersist
    public void gravarDuracaoInclusao() {
        /*if (!Objects.isNull(this.getDataHoraEncerramento())) {
            Duration duration = Duration.between(this.getDataHoraAtendimento(), this.getDataHoraEncerramento());
            //Period p = Period.between(this.getDataHoraAtendimento().toLocalDate(), this.getDataHoraEncerramento().toLocalDate());
            this.setDuracao(duration.toMinutes());
        }*/
    }

    //Em tese, o encerramento do antendimento vem depois da inclusao, entao nao pode ser na inclusao..e sim no update ou
    //Alteracao do atendimento
    @PreUpdate
    public void gravarDuracaoAtualizacao() {

        //Period p = Period.between(this.getDataHoraAtendimento().toLocalDate(),this.getDataHoraEncerramento().toLocalDate());
        //this.setDuracao(LocalDateTime.of(p.getYears(),p.getMonths(),p.getDays(),));
        /*if (!Objects.isNull(this.getDataHoraEncerramento())) {
            Duration duration = Duration.between(this.getDataHoraAtendimento(), this.getDataHoraEncerramento());
            //Period p = Period.between(this.getDataHoraAtendimento().toLocalDate(), this.getDataHoraEncerramento().toLocalDate());
            this.setDuracao(duration.toMinutes());
        }*/
    }


}
