package br.com.sdney.notificahub_microservice.dtos;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author fsdney
 */

@Data
public class AgendamentoRequest {
    private String destinatario;
    private String mensagem;
    private String canal;
    private LocalDateTime dataAgendamento;
}