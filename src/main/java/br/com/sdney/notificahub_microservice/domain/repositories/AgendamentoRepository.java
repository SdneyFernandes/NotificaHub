package br.com.sdney.notificahub_microservice.domain.repositories;

import br.com.sdney.notificahub_microservice.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fsdney
 */

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatusAndDataAgendamentoLessThanEqual(
        Agendamento.StatusAgendamento status,
        LocalDateTime dataAtual
    );
}