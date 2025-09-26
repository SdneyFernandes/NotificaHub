package br.com.sdney.notificahub_microservice.services;

import br.com.sdney.notificahub_microservice.clients.SendGridApiClient;
import br.com.sdney.notificahub_microservice.domain.Agendamento;
import br.com.sdney.notificahub_microservice.domain.NotificacaoLog;
import br.com.sdney.notificahub_microservice.domain.repositories.AgendamentoRepository;
import br.com.sdney.notificahub_microservice.domain.repositories.NotificacaoLogRepository;
import br.com.sdney.notificahub_microservice.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fsdney
 */

@Service
public class NotificationService {

    @Autowired
    private NotificacaoLogRepository notificacaoLogRepository;

    @Autowired
    private SendGridApiClient sendGridApiClient;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);


    public void enviarNotificacao(NotificacaoRequest request) {
        String status;
        log.info("Recebida requisição de notificação para o canal: {}", request.getCanal());

        if ("EMAIL".equalsIgnoreCase(request.getCanal())) {
            try {
                var sendGridRequest = buildSendGridRequest(request);
                sendGridApiClient.sendEmail("Bearer " + sendGridApiKey, sendGridRequest);
                status = "ENVIADO";
                log.info("E-mail enviado com sucesso para o destinatário: {}", request.getDestinatario());
            } catch (Exception e) {
                status = "FALHOU";
                log.error("Falha ao enviar e-mail via SendGrid para {}: {}", request.getDestinatario(), e.getMessage(), e);
            }
        } else {
            status = "CANAL_NAO_SUPORTADO";
            log.warn("Tentativa de envio para canal não suportado [{}].", request.getCanal());
        }

        salvarLog(request, status);
    }

    public void agendarNotificacao(AgendamentoRequest request) {
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setDestinatario(request.getDestinatario());
        novoAgendamento.setMensagem(request.getMensagem());
        novoAgendamento.setCanal(request.getCanal());
        novoAgendamento.setDataAgendamento(request.getDataAgendamento());
        novoAgendamento.setStatus(Agendamento.StatusAgendamento.AGUARDANDO);

        agendamentoRepository.save(novoAgendamento);
        log.info("Notificação agendada com sucesso para {} em {}", request.getDestinatario(), request.getDataAgendamento());
    }

    @Scheduled(fixedRate = 60000)
    public void verificarEEnviarAgendamentos() {
        log.debug("--- VERIFICANDO AGENDAMENTOS PENDENTES ({}) ---", LocalDateTime.now());
        var agendamentosParaEnviar = agendamentoRepository
            .findByStatusAndDataAgendamentoLessThanEqual(Agendamento.StatusAgendamento.AGUARDANDO, LocalDateTime.now());

        if (agendamentosParaEnviar.isEmpty()) {
            log.debug("Nenhum agendamento encontrado para enviar agora.");
            return;
        }

        log.info("{} agendamento(s) encontrado(s) para enviar.", agendamentosParaEnviar.size());

        for (Agendamento agendamento : agendamentosParaEnviar) {
            log.info("Processando agendamento ID: {}", agendamento.getId());
            try {
                var notificacaoRequest = new NotificacaoRequest();
                notificacaoRequest.setDestinatario(agendamento.getDestinatario());
                notificacaoRequest.setMensagem(agendamento.getMensagem());
                notificacaoRequest.setCanal(agendamento.getCanal());

                enviarNotificacao(notificacaoRequest);

                agendamento.setStatus(Agendamento.StatusAgendamento.ENVIADO);
            } catch (Exception e) {
                log.error("Falha ao processar agendamento ID {}: {}", agendamento.getId(), e.getMessage(), e);
                agendamento.setStatus(Agendamento.StatusAgendamento.ERRO);
            } finally {
                agendamentoRepository.save(agendamento);
            }
        }
    }

    private SendGridRequest buildSendGridRequest(NotificacaoRequest request) {
        var to = new SendGridEmail(request.getDestinatario());
        var from = new SendGridEmail(fromEmail);
        var personalization = new SendGridPersonalization(List.of(to));
        var content = new SendGridContent("text/plain", request.getMensagem());
        String subject = "Notificação do Sistema NotificaHub";
        return new SendGridRequest(List.of(personalization), from, subject, List.of(content));
    }

    private void salvarLog(NotificacaoRequest request, String status) {
        NotificacaoLog log = new NotificacaoLog();
        log.setDestinatario(request.getDestinatario());
        log.setMensagem(request.getMensagem());
        log.setCanal(request.getCanal());
        log.setTimestampEnvio(LocalDateTime.now());
        log.setStatus(status);
        notificacaoLogRepository.save(log);
    }
}
