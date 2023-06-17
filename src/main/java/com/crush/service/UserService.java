package com.crush.service;

import com.crush.dtos.UserDto;

public interface UserService {

    UserDto getUserByUsername(String username);

}
