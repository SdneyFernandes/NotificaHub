package br.com.sdney.notificahub_microservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


/**
 * @author fsdney
 */

@Entity
@Table(name = "notificacao_log")
@Data
public class NotificacaoLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestampEnvio;
    private String destinatario;
    private String mensagem;
    private String canal; // EMAIL, SMS, PUSH
    private String status; // ENVIADO, FALHOU
}

