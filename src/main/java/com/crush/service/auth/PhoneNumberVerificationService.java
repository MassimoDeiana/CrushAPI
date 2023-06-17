package com.crush.service.auth;


public interface PhoneNumberVerificationService {

    String generateOTP(String phoneNumber);

    String verifyOTP(String phoneNumber, String otp);

}
