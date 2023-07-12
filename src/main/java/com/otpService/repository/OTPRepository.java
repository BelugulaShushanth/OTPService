package com.otpService.repository;

import com.otpService.bean.OTPBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTPBean,Long> {
}
