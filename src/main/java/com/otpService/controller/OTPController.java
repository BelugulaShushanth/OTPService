package com.otpService.controller;

import com.otpService.bean.OTPBean;
import com.otpService.bean.Status;
import com.otpService.service.OTPServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPServices otpServices;

    @PostMapping("/sendOTP")
    public OTPBean sendOTP(@RequestParam("mailId") String mailId){
        return otpServices.sendOTP(mailId);
    }

    @PostMapping("/verifyOTP")
    public Status verifyOTP(@RequestBody Map<String,String> otpVerify){
        return otpServices.verifyOTP(otpVerify);
    }
}
