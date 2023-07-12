package com.otpService.controller;

import com.otpService.bean.OTPBean;
import com.otpService.service.OTPServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPServices otpServices;

    @PostMapping("/sendOTP")
    public OTPBean sendOTP(@RequestParam("mailId") String mailId){
        return otpServices.sendOTP(mailId);
    }
}
