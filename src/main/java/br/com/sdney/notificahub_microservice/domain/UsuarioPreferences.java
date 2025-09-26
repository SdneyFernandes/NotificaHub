package br.com.sdney.notificahub_microservice.domain;


import jakarta.persistence.*;
import lombok.Data;


/**
 * @author fsdney
 */


@Entity
@Table(name = "usuario_preferences")
@Data
public class UsuarioPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idExternoUsuario; // O ID do usuário no sistema principal
    private boolean recebeEmail = true;
    private boolean recebeSms = false;
    private boolean recebePush = true;
}