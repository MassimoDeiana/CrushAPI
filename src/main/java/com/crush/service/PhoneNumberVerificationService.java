package com.crush.service;


public interface PhoneNumberVerificationService {

    String generateOTP(String phoneNumber);

    String verifyOTP(String phoneNumber, String otp);

}
