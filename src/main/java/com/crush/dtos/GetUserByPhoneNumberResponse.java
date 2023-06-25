package com.crush.dtos;

import com.crush.domain.enums.AccountStatus;
import lombok.Data;

@Data
public class GetUserByPhoneNumberResponse {

    private String id;
    private String phoneNumber;
    private AccountStatus accountStatus;
    private boolean isPhoneNumberVerified;


}
