package br.com.sdney.notificahub_microservice.domain.repositories;

import br.com.sdney.notificahub_microservice.domain.UsuarioPreferences;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author fsdney
 */


public interface UsuarioPreferencesRepository extends JpaRepository<UsuarioPreferences, Long> {
    
}