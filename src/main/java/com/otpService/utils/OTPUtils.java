package com.otpService.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPUtils {

    public Integer generateRandomOTP(){
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public Long generateRandomNumber(){
        Random r = new Random( System.currentTimeMillis() );
        return (long) ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
}
