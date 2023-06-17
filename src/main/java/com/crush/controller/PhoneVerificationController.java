package com.crush.controller;

import com.crush.service.auth.PhoneNumberVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Phone verification", description = "Phone verification by OTP")
@RequiredArgsConstructor
public class PhoneVerificationController {

    private final PhoneNumberVerificationService phoneNumberVerificationService;

    @Operation(summary = "Generate OTP")
    @PostMapping(value = "/phone-number/send-otp", produces = "application/json")
    public ResponseEntity<String> generateOTP(@RequestBody String phoneNumber) {

        String verificationStatus = phoneNumberVerificationService.generateOTP(phoneNumber);

        return ResponseEntity.ok(verificationStatus);
    }

    @Operation(summary = "Verify OTP")
    @PostMapping(value = "/phone-number/verify-otp/{phoneNumber}", produces = "application/json")
    public ResponseEntity<String> verifyOTP(@PathVariable String phoneNumber, @RequestBody String otp) {

        String verificationStatus = phoneNumberVerificationService.verifyOTP(phoneNumber, otp);

        return ResponseEntity.ok(verificationStatus);
    }

}
