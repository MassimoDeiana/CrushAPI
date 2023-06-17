package com.crush.service.auth;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public interface TwilioService {

    Verification createVerification(String phoneNumber);

    VerificationCheck checkVerification(String phoneNumber, String otp);

}
