package br.com.sdney.notificahub_microservice.dtos;

import java.util.List;

/**
 * @author fsdney
 */

public record SendGridPersonalization(List<SendGridEmail> to) {
    
}