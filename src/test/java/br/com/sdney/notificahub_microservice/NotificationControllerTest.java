package br.com.sdney.notificahub_microservice;

import br.com.sdney.notificahub_microservice.domain.repositories.NotificacaoLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author fsdney
 */

@SpringBootTest 
@AutoConfigureMockMvc
@ActiveProfiles("test") 
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @Autowired
    private ObjectMapper objectMapper; 

    @Autowired
    private NotificacaoLogRepository notificacaoLogRepository;

    @Test
void deveEnviarNotificacaoERetornarStatus200() throws Exception {
    String requestJson = """
        {
          "destinatario": "integracao@email.com",
          "mensagem": "Teste de integração!",
          "canal": "SMS"
        }
    """;

    mockMvc.perform(post("/api/notificacoes/enviar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string("Notificação recebida e processada."));

    assertEquals(1, notificacaoLogRepository.count());
}
}