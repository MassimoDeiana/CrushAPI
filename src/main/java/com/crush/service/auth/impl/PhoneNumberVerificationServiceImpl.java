package com.crush.service.auth.impl;

import com.crush.domain.entity.User;
import com.crush.exception.UserAlreadyVerifiedException;
import com.crush.exception.UserNotFoundException;
import com.crush.repository.UserRepository;
import com.crush.service.auth.PhoneNumberVerificationService;
import com.crush.service.auth.TwilioService;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneNumberVerificationServiceImpl implements PhoneNumberVerificationService {

    private final UserRepository userRepository;
    private final TwilioService twilioService;

    @Override
    public String generateOTP(String phoneNumber) {
        checkIfUserAlreadyVerified(phoneNumber);
        Verification verification = twilioService.createVerification(phoneNumber);
        return verification.getStatus();
    }

    @Override
    public String verifyOTP(String phoneNumber, String otp) {
        try {
            User user = userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(UserNotFoundException::new);

            if(user.isPhoneNumberVerified()) {
                throw new UserAlreadyVerifiedException();
            }

            VerificationCheck verificationCheck = twilioService.checkVerification(phoneNumber, otp);

            if(verificationCheck.getStatus().equals("approved")) {
                user.setPhoneNumberVerified(true);
                userRepository.save(user);
            }

            return verificationCheck.getStatus();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIfUserAlreadyVerified(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if(user.isPresent() && user.get().isPhoneNumberVerified()) {
            throw new UserAlreadyVerifiedException();
        }
    }

}
