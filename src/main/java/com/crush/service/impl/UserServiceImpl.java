package com.crush.service.impl;

import com.crush.dtos.GetUserByPhoneNumberResponse;
import com.crush.dtos.UserDto;
import com.crush.exception.UserNotFoundException;
import com.crush.mapper.UserMapper;
import com.crush.repository.UserRepository;
import com.crush.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers() {
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public GetUserByPhoneNumberResponse getUserByPhoneNumber(String phoneNumber) {
        return userMapper.map(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(UserNotFoundException::new));
    }


}
