package com.crush.service.auth.impl;

import com.crush.service.auth.PhoneNumberVerificationService;
import com.crush.service.auth.TwilioService;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneNumberVerificationServiceImpl implements PhoneNumberVerificationService {

    private final TwilioService twilioService;

    @Override
    public String generateOTP(String phoneNumber) {
        Verification verification = twilioService.createVerification(phoneNumber);
        return verification.getStatus();
    }

    @Override
    public String verifyOTP(String phoneNumber, String otp) {
        try {
            VerificationCheck verificationCheck = twilioService.checkVerification(phoneNumber, otp);
            return verificationCheck.getStatus();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
