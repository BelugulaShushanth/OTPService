package com.otpService.restClients;

import com.otpService.bean.MailBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("MAILING-SERVICE")
public interface MailClient {

    @PostMapping("/api/mail/sendMail")
    ResponseEntity<String> sendMail(@RequestBody MailBean mailBean);
}
