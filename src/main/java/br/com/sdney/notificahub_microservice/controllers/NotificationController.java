package br.com.sdney.notificahub_microservice.controllers;

import br.com.sdney.notificahub_microservice.services.NotificationService;
import br.com.sdney.notificahub_microservice.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/api/notificacoes")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarNotificacao(@RequestBody NotificacaoRequest request) {
        notificationService.enviarNotificacao(request);
        return ResponseEntity.ok("Notificação recebida e processada.");
    }

    @PostMapping("/agendar")
    public ResponseEntity<String> agendarNotificacao(@RequestBody AgendamentoRequest request) {
        notificationService.agendarNotificacao(request);
        return ResponseEntity.ok("Notificação agendada com sucesso.");
    }
}
