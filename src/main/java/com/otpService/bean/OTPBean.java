package com.otpService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "otp_details")
@NoArgsConstructor
@Setter
@Getter
public class OTPBean {

    @Id
    private Long transactionId;

    @JsonIgnore
    private Integer otp;

    private String mailId;

    @JsonIgnore
    private boolean isVerified;

    @JsonIgnore
    private boolean isExpired;

    @Transient
    private Status mailStatus;
}
