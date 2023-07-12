package com.otpService.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MailBean {

    private String toMailId;
    private String subject;
    private String body;

}
