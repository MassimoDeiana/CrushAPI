package com.crush.service.impl;

import com.crush.service.PhoneNumberVerificationService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneNumberVerificationServiceImpl implements PhoneNumberVerificationService {

    @Override
    public String generateOTP(String phoneNumber) {
        initializeTwilioService();
        Verification verification = Verification.creator(
                System.getenv("TWILIO_VERIFY_SERVICE_SID"),
                phoneNumber,
                "sms")
                .create();

        log.info("OTP: " + verification.getStatus());
        return verification.getStatus();
    }

    @Override
    public boolean verifyOTP(String phoneNumber, String otp) {
        initializeTwilioService();
        try {

            VerificationCheck verificationCheck = VerificationCheck.creator(
                    System.getenv("TWILIO_VERIFY_SERVICE_SID"))
                    .setTo(phoneNumber)
                    .setCode(otp)
                    .create();

            System.out.println(verificationCheck.getStatus());
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeTwilioService() {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
    }
}
