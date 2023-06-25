package com.crush.service;

import com.crush.dtos.GetUserByPhoneNumberResponse;
import com.crush.dtos.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    UserDto getUserByUsername(String username);

    GetUserByPhoneNumberResponse getUserByPhoneNumber(String phoneNumber);

}
