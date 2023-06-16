package com.crush.controller;

import com.crush.service.PhoneNumberVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication by phone number")
@RequiredArgsConstructor
public class AuthenticationController {

    private final PhoneNumberVerificationService phoneNumberVerificationService;

    @Operation(summary = "Generate OTP")
    @PostMapping(value = "/phone-number/send-otp", produces = "application/json")
    public ResponseEntity<String> generateOTP(@RequestBody String phoneNumber) {

        String otp = phoneNumberVerificationService.generateOTP(phoneNumber);

        return ResponseEntity.ok(otp);
    }

    @Operation(summary = "Verify OTP")
    @PostMapping(value = "/phone-number/verify-otp/{phoneNumber}", produces = "application/json")
    public ResponseEntity<Boolean> verifyOTP(@PathVariable String phoneNumber, @RequestBody String otp) {

        boolean isVerified = phoneNumberVerificationService.verifyOTP(phoneNumber, otp);

        return ResponseEntity.ok(isVerified);
    }

}

