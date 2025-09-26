package br.com.sdney.notificahub_microservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author fsdney
 */

@Entity
@Table(name = "agendamentos")
@Data
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;
    private String mensagem;
    private String canal;

    private LocalDateTime dataAgendamento;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    public enum StatusAgendamento {
        AGUARDANDO,
        ENVIADO,
        ERRO
    }
}