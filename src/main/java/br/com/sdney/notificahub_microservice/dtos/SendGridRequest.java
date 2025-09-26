package br.com.sdney.notificahub_microservice.dtos;

import java.util.List;

/**
 * @author fsdney
 */

public record SendGridRequest(List<SendGridPersonalization> personalizations, SendGridEmail from, String subject, List<SendGridContent> content) {
    
}