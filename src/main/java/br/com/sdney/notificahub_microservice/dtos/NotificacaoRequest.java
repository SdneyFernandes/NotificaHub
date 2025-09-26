package br.com.sdney.notificahub_microservice.dtos;


import lombok.Data;


/**
 * @author fsdney
 */


@Data
public class NotificacaoRequest {
    private String destinatario;
    private String mensagem;
    private String canal; // EMAIL, SMS, PUSH
}