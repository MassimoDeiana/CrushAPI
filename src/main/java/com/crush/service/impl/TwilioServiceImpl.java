package com.crush.service.impl;

import com.crush.service.TwilioService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class TwilioServiceImpl implements TwilioService {

    @PostConstruct
    public void init() {
        initializeTwilioService();
    }

    @PreDestroy
    public void destroy() {
        destroyTwilioService();
    }

    @Override
    public Verification createVerification(String phoneNumber) {
        return Verification.creator(
                        System.getenv("TWILIO_VERIFY_SERVICE_SID"),
                        phoneNumber,
                        "sms")
                .create();
    }

    @Override
    public VerificationCheck checkVerification(String phoneNumber, String otp) {
        return VerificationCheck.creator(
                        System.getenv("TWILIO_VERIFY_SERVICE_SID"))
                .setTo(phoneNumber)
                .setCode(otp)
                .create();
    }

    private void initializeTwilioService() {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
    }

    private void destroyTwilioService() {
        Twilio.destroy();
    }
}
