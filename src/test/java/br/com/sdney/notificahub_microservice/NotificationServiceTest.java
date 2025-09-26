package br.com.sdney.notificahub_microservice;

import br.com.sdney.notificahub_microservice.services.NotificationService;
import br.com.sdney.notificahub_microservice.domain.NotificacaoLog;
import br.com.sdney.notificahub_microservice.domain.repositories.NotificacaoLogRepository;
import br.com.sdney.notificahub_microservice.dtos.NotificacaoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @author fsdney
 */


@ExtendWith(MockitoExtension.class) 
class NotificationServiceTest {

    @Mock 
    private NotificacaoLogRepository notificacaoLogRepository;

    @InjectMocks 
    private NotificationService notificationService;

    @Test
    void deveSalvarUmLogDeNotificacaoComSucesso() {
        NotificacaoRequest request = new NotificacaoRequest();
        request.setDestinatario("teste@email.com");
        request.setMensagem("Olá, Mundo!");
        request.setCanal("EMAIL");

        notificationService.enviarNotificacao(request);

        ArgumentCaptor<NotificacaoLog> logCaptor = ArgumentCaptor.forClass(NotificacaoLog.class);
        verify(notificacaoLogRepository).save(logCaptor.capture());
        
        NotificacaoLog logSalvo = logCaptor.getValue();
        assertEquals("teste@email.com", logSalvo.getDestinatario());
        assertEquals("Olá, Mundo!", logSalvo.getMensagem());
        assertEquals("EMAIL", logSalvo.getCanal());
        assertEquals("SIMULADO_COM_SUCESSO", logSalvo.getStatus());
    }
}
