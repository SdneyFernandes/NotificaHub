package br.com.sdney.notificahub_microservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import br.com.sdney.notificahub_microservice.dtos.SendGridRequest;

/**
 * @author fsdney
 */

@FeignClient(name = "sendgrid", url = "https://api.sendgrid.com/v3")
public interface SendGridApiClient {

    @PostMapping("/mail/send")
    void sendEmail(@RequestHeader("Authorization") String authorization,
                   @RequestBody SendGridRequest request);
}