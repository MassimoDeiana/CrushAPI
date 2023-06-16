package com.crush.service;


public interface PhoneNumberVerificationService {

    public String generateOTP(String phoneNumber);

    public boolean verifyOTP(String phoneNumber, String otp);

}
