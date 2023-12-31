package com.otpService.service;

import com.otpService.bean.MailBean;
import com.otpService.bean.OTPBean;
import com.otpService.bean.Status;
import com.otpService.repository.OTPRepository;
import com.otpService.restClients.MailClient;
import com.otpService.utils.OTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@Service
public class OTPServices {

    private final Logger logger = LoggerFactory.getLogger(OTPServices.class);

    @Value("${cricfizz.sendOTP.subject}")
    private String subject;

    @Value("${cricfizz.sendOTP.body}")
    private String body;

    @Autowired
    private OTPUtils otpUtils;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private OTPRepository otpRepository;

    public OTPBean sendOTP(String mailId){
        final Integer randomOTP = otpUtils.generateRandomOTP();
        String message = body;
        message = message.replace("otpValue", randomOTP.toString());
        MailBean mailBean = new MailBean();
        mailBean.setToMailId(mailId);
        mailBean.setSubject(subject);
        mailBean.setBody(message);
        ResponseEntity<String> responseEntity = null;
        try {
             responseEntity = mailClient.sendMail(mailBean);
             if(responseEntity.getStatusCode().is2xxSuccessful()) {
                 logger.info("OTP Sent Successfully to mailID: {}",mailId);
             }
             else{
                 logger.error("Failed to send OTP to mailID: {}",mailId);
             }
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<>("Unable To Send Mail To "+mailBean.getToMailId()
                    +" please retry again",HttpStatus.BAD_REQUEST);
        }

        return saveOTP(responseEntity,mailId,randomOTP);
    }

    private OTPBean saveOTP(ResponseEntity<String> responseEntity, String mailId, Integer randomOTP){

        OTPBean otpBean = new OTPBean();

        otpBean.setMailId(mailId);
        otpBean.setOtp(randomOTP);
        otpBean.setExpired(false);
        otpBean.setVerified(false);

        Status status = new Status();
        status.setHttpStatus(responseEntity.getStatusCode());
        status.setStatusMessage(responseEntity.getBody());

        otpBean.setMailStatus(status);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            Instant instant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            final Long transactionId = instant.toEpochMilli() + otpUtils.generateRandomNumber();
            otpBean.setTransactionId(transactionId);
            otpRepository.save(otpBean);
        }

        return otpBean;
    }

    public Status verifyOTP(Map<String, String> otpVerify){
        Optional<OTPBean> optionalOTPBean = otpRepository.findById(Long.parseLong(otpVerify.get("transactionId")));

        if (optionalOTPBean.isPresent()){
            OTPBean otpEntity = optionalOTPBean.get();
            if(Integer.parseInt(otpVerify.get("otp")) == otpEntity.getOtp()){
                otpEntity.setVerified(true);
                otpRepository.save(otpEntity);
                Status status = new Status();
                status.setHttpStatus(HttpStatus.OK);
                status.setStatusMessage("OTP verified successfully");
                logger.info("OTP verified successfully for mailId: {}",otpEntity.getMailId());
                return status;
            }
            else{
                Status status = new Status();
                status.setHttpStatus(HttpStatus.BAD_REQUEST);
                status.setStatusMessage("Invalid OTP");
                logger.info("Invalid OTP for mailId: {}",otpEntity.getMailId());
                return status;
            }
        }
        else{
            Status status = new Status();
            status.setHttpStatus(HttpStatus.BAD_REQUEST);
            status.setStatusMessage("Invalid transactionId");
            logger.error("Invalid transactionId: {}",otpVerify.get("transactionId"));
            return status;
        }
    }
}
