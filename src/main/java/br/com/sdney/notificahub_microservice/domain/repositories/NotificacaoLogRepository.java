package br.com.sdney.notificahub_microservice.domain.repositories;

import br.com.sdney.notificahub_microservice.domain.NotificacaoLog;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author fsdney
 */

public interface NotificacaoLogRepository extends JpaRepository<NotificacaoLog, Long> {
}