package com.crush.service;

import com.crush.service.impl.PhoneNumberVerificationServiceImpl;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PhoneNumberVerificationServiceTest {

    @Mock
    private TwilioService twilioService;

    @InjectMocks
    private PhoneNumberVerificationServiceImpl phoneNumberVerificationService;

    private static final String PHONE_NUMBER = "1234567890";
    private static final String OTP = "123456";
    private static final String PENDING_STATUS = "pending";
    private static final String APPROVED_STATUS = "approved";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPendingStatusWhenGenerateOTP() {
        givenTwilioServiceCreatesVerificationWithStatus();

        String actualStatus = phoneNumberVerificationService.generateOTP(PHONE_NUMBER);

        verifyTwilioServiceCreatedVerificationOnce();
        assertEquals(PENDING_STATUS, actualStatus);
    }

    @Test
    void shouldReturnApprovedStatusWhenVerifyOTP() {
        givenTwilioServiceChecksVerificationWithStatus(APPROVED_STATUS);

        String actualStatus = phoneNumberVerificationService.verifyOTP(PHONE_NUMBER, OTP);

        verifyTwilioServiceCheckedVerificationOnce();
        assertEquals(APPROVED_STATUS, actualStatus);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenVerifyOTP() {
        givenTwilioServiceChecksVerificationThrowsException();

        assertThrows(RuntimeException.class, () -> phoneNumberVerificationService.verifyOTP(PHONE_NUMBER, OTP));
        verifyTwilioServiceCheckedVerificationOnce();
    }

    private void givenTwilioServiceCreatesVerificationWithStatus() {
        Verification verification = mock(Verification.class);
        when(verification.getStatus()).thenReturn(PhoneNumberVerificationServiceTest.PENDING_STATUS);
        when(twilioService.createVerification(PHONE_NUMBER)).thenReturn(verification);
    }

    private void givenTwilioServiceChecksVerificationWithStatus(String status) {
        VerificationCheck verificationCheck = mock(VerificationCheck.class);
        when(verificationCheck.getStatus()).thenReturn(status);
        when(twilioService.checkVerification(PHONE_NUMBER, OTP)).thenReturn(verificationCheck);
    }

    private void givenTwilioServiceChecksVerificationThrowsException() {
        when(twilioService.checkVerification(PHONE_NUMBER, OTP)).thenThrow(new RuntimeException());
    }

    private void verifyTwilioServiceCreatedVerificationOnce() {
        verify(twilioService, times(1)).createVerification(PHONE_NUMBER);
    }

    private void verifyTwilioServiceCheckedVerificationOnce() {
        verify(twilioService, times(1)).checkVerification(PHONE_NUMBER, OTP);
    }

}
